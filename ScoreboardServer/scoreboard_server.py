"""
Scoreboard Server implemented with Flask HTTP GET/POST endpoints and Amazon DynamoDB.
"""

import datetime
import time
import json
import decimal
from http import HTTPStatus
import boto3
from flask import Flask, request, Response
app = Flask(__name__)
ENDPOINT = "http://dynamodb.us-east-1.amazonaws.com"
DB = boto3.resource('dynamodb', endpoint_url=ENDPOINT, region_name='us-east-1')

def datetime_generate():
    """ Generates a new datetime integer.

    Uses datetime and time modules to assemble and return a current UNIX datetime.
    ---
    returns:
      Integer representing current UNIX datetime, at 1-second precision.

    """

    return int(time.mktime(datetime.datetime.now().timetuple()))

class DecimalEncoder(json.JSONEncoder):
    """ Custom JSON encoder for Decimal conversion. """
    def default(self, o):  # pylint: disable=E0202
        """ Default conversion method.

        Converts Decimal() types to native Python integer type.

        ---
        parameters:
          - name: o
            required: true
            description: Object instance to convert to integer.
        returns:
            Integer-converted value of Decimal object parameter.
        """
        if isinstance(o, decimal.Decimal):
            return int(o)
        return super(DecimalEncoder, self).default(o)

class UserScore:
    """
    Class representing a user and their respective score data.
    """
    def __init__(self, username, score, scoretime=None):
        """ UserScore constructor.

        Creates a new UserScore object instance.

        ---
        parameters:
          - name: username
            required: true
            description: Username of the user.
        parameters:
          - name: score
            required: true
            description: Highscore of the user.
        parameters:
          - name: scoretime
            required: false
            description: UNIX-format datetime that the score was made. If not
                         set, a new datetime is generated for the UserScore.
        """
        self.username = username
        self.score = score
        if scoretime is None:
            self.scoretime = datetime_generate()
        else:
            self.scoretime = scoretime
        log(f"NEW USER SCORE CREATED: {username} {score}")

    def to_json(self):
        """ Creates and returns a JSON string representing the UserScore object.

        Assembles a result dictionary in the format expected by the DynamoDB remote database,
        and adds to it the UserScore data. Username and datetime are given as primary keys for
        the UserScore, and other data is provided under the 'info' dictionary.
        ---
        returns:
          JSON string containing UserScore data, in the format expected
          by the DynamoDB remote database.

        """
        result = {}
        result["username"] = self.username
        result["datetime"] = self.scoretime
        result["info"] = {}
        result["info"]["highscore"] = self.score
        return json.dumps(result)

def response_build(data, response_status):
    """ Create and return a Response object using input data and status code.

    Builds an HTTP response using a Flask Response object, and returns it. Response
    mimetype is set to 'application/json', since the server deals with JSON data.

    ---
    parameters:
      - name: data
        required: true
        description: Input data (any type) to return as a JSON string via Response.
                     Input is converted to a string prior to passing to Response constructor.
      - name: response_status
        required: true
        description: HTTP Status Code to assign to the HTTP Response. It is recommended
                     to pass these using named HTTPStatus attributes. (e.g.: HTTPStatus.OK)
    returns:
        Response object with provided status code containing given payload data.
    """
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
    result = response_build(json.dumps(error), response_status)
    return result

def log(data):
    """ Log data to the console.

    Log some input data to the console. Resulting output format
    also contains time of log event.

    ---
    parameters:
      - name: data
        required: true
        description: Input data (any type) to log to the console.
                     Input is converted to a string prior to print() call.

    """
    log_time = datetime.datetime.now().strftime("%d/%b/%Y %H:%M:%S")
    print(f"LOG  -  [{log_time}]  {str(data)}")

@app.errorhandler(404)
@app.errorhandler(400)
def error_handle(_):
    return error_response_build("Endpoint not recognized, check game/app URL path to scoreboard server.", HTTPStatus.NOT_FOUND)

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
      - name: count
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
            for i, _ in enumerate(sorted_scores):
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
      - name: username
        in: path
        required: true
        description: Username to store into remote database.
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
