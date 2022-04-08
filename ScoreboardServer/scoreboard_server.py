import datetime
import time
import json
import boto3
import decimal
from http import HTTPStatus
from flask import Flask, request, Response
app = Flask(__name__)
DB = boto3.resource('dynamodb', endpoint_url="http://dynamodb.us-east-1.amazonaws.com", region_name='us-east-1')

def datetime_generate():
    return int(time.mktime(datetime.datetime.now().timetuple()))

class DecimalEncoder(json.JSONEncoder):
    def default(self, o):
        if isinstance(o, decimal.Decimal):
            return int(o)
        return super(DecimalEncoder, self).default(o)

class UserScore:
    def __init__(self, username, score, datetime=None):
        self.username = username
        self.score = score
        if datetime is None:
            self.datetime = datetime_generate()
        else:
            self.datetime = datetime
        log(f"NEW USER SCORE CREATED: {username} {score}")

    def to_json(self):
        result = {}
        result["username"] = self.username
        result["datetime"] = self.datetime
        result["info"] = {}
        result["info"]["highscore"] = self.score
        return json.dumps(result)

def response_build(data, response_status):
    return Response(str(data), status=response_status, mimetype='application/json')

def error_response_build(error_message, response_status):
    """ Create and return a Response object using input error message and status code.

    Builds an HTTP response using a Flask Response object, and returns it. Response
    mimetype is set to 'application/json', since the server deals with JSON data.
    Error JSON construction is an object that has a key 'error' with value of the error message.

    ---
    parameters:
      - name: error_message
        required: true
        description: Input error message (string) to assign to Response error JSON object.
      - name: response_status
        required: true
        description: HTTP Status Code to assign to the HTTP Response. It is recommended
                     to pass these using named HTTPStatus attributes. (e.g.: HTTPStatus.BAD_REQUEST)
    returns:
        Response object with provided error message and status code.
    """
    error = {"error": error_message}
    result = response_build(json.dumps(error), HTTPStatus.BAD_REQUEST)
    return result

def log(data):
    log_time = datetime.datetime.now().strftime("%d/%b/%Y %H:%M:%S")
    print(f"LOG  -  [{log_time}]  {str(data)}")

@app.route('/test/create')
def test_create():
    result = "START.\n"

    new_table = DB.create_table(
                    TableName='Scores',
                    KeySchema=[
                        {
                            'AttributeName': 'username',
                            'KeyType': 'HASH' # partition AKA primary key
                        },
                        {
                            'AttributeName': 'datetime',
                            'KeyType': 'RANGE' # sort AKA partial key
                        },
                    ],
                    AttributeDefinitions=[
                        {
                            'AttributeName': 'username',
                            'AttributeType': 'S' # string
                        },
                        {
                            'AttributeName': 'datetime',
                            'AttributeType': 'N' # number (?)
                        }
                    ],
                    ProvisionedThroughput={
                        'ReadCapacityUnits': 10,
                        'WriteCapacityUnits': 10
                    })

    result += str(new_table)
    result += "\n"

    result += "DONE.\n"
    return result


@app.route('/add', methods=['POST', 'GET'])
def post_add():
    """ Add a Score to database based on POST data.

    Receive POST score data and add to remote database.
    Data is expected to be JSON format with mandatory
    'username' and 'info' keys, 'username' corresponding
    to a string value and 'info' to a dict/object value.
    ---

    responses:
      200:
        description: A JSON string containing response data.
    """
    table = DB.Table('Scores')
    result = ""

    if request.method == 'POST':
        post_data = request.get_json()
        if post_data is None:
            result = response_build("Malformed Request", HTTPStatus.BAD_REQUEST)
            return result
        if isinstance(post_data, dict):
            if 'username' in post_data and 'info' in post_data:
                post_data['datetime'] = datetime_generate()
                result = response_build(f"POST DATA GOOD FORMAT {str(post_data)}", HTTPStatus.OK)
                log(result.get_data())
                table.put_item(Item=post_data)
        else:
            result = response_build("Malformed Request", HTTPStatus.BAD_REQUEST)
            log(result)

    return result

@app.route('/retrieve/', defaults={'count': -1})
@app.route('/retrieve/<count>')
def retrieve(count):
    """ Retrieves leaderboard.

    Retrieves leaderboard as a JSON object. Communicates with remote database to
    get remote score data, then assembles into a JSON object to reply to the
    client. Results are sorted by highscore. Optionally, a count can be provided
    to limit the number of scores returned. If no count is provided, all records
    are returned.
    ---
    parameters:
      - name: count (int)
          in: path
          required: false
          description: Amount of scores to return in result JSON data.
    responses:
      200:
        description: A JSON string containing highscore data.
    """
    table = DB.Table('Scores')
    result_data = ""
    score_list = None
    try:
        count = int(count)
    except ValueError:
        return error_response_build("Must provide count as a number if providing count parameter to /retrieve GET endpoint.", HTTPStatus.BAD_REQUEST)

    if request.method == 'GET':
        scan = table.scan()
        if 'Items' in scan:
            score_list = scan['Items']

    sorted_scores = []
    found_lower = False
    if score_list and len(score_list) > 0:
        sorted_scores.append(score_list[0])
        for cur_item in score_list:
            cur_score = cur_item["info"]["highscore"]
            for i in range(len(sorted_scores)):
                found_lower = False
                score = sorted_scores[i]["info"]["highscore"]
                if score < cur_score:
                    sorted_scores.insert(i, cur_item)
                    found_lower = True
                    break
            if not found_lower:
                sorted_scores.append(cur_item)

    if count > -1:
        sorted_scores = sorted_scores[:count]

    result_scores = json.dumps(sorted_scores, cls=DecimalEncoder)
    result_data += result_scores
    log(result_data)

    result = response_build(result_data, HTTPStatus.OK)
    return result


@app.route('/add/<username>/<score>/')
def add(username, score):
    """ Adds a username & score to database.

    Adds a username and score to a remote database as a single new entry in the table.
    ---
    parameters:
      - username (str): Username to store into remote database.
        in: path
        required: true
        description: The username
      - name: score
          in: path
          required: true
          description: Game highscore to store into remote database.
    responses:
      200:
        description: Successful
    """

    table = DB.Table('Scores')
    result = ""
    time_made = datetime_generate()
    try:
        score = int(score)
    except ValueError:
        return error_response_build("Must provide highscore as a number for the second parameter to the /add GET endpoint.", HTTPStatus.BAD_REQUEST)

    if request.method == 'GET':
        log(f"ADD {username} {score} ATTEMPTED")
        response = table.put_item(
            Item={
                'username': username,
                'datetime': time_made,
                'info': {
                    'highscore': int(score)
                }
            }
        )
        log(response)

    user_score = UserScore(username, score, time_made)
    result = response_build(user_score.to_json(), HTTPStatus.OK)

    return result

if __name__ == '__main__':
    app.run(threaded=False, port=8002)
