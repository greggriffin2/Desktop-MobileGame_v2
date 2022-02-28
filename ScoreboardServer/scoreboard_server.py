import datetime
import time
import json
import boto3
import decimal
from flask import Flask, request
app = Flask(__name__)
DB = boto3.resource('dynamodb', endpoint_url="http://dynamodb.us-east-1.amazonaws.com", region_name='us-east-1')

class DecimalEncoder(json.JSONEncoder):
    def default(self, o):
        if isinstance(o, decimal.Decimal):
            return int(o)
        return super(DecimalEncoder, self).default(o)

def datetime_generate():
    return int(time.mktime(datetime.datetime.now().timetuple()))

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
            result = "Bad JSON data in POST body!"
            return result
        if isinstance(post_data, dict):
            if 'username' in post_data and 'info' in post_data:
                post_data['datetime'] = datetime_generate()
                result = "POST DATA GOOD FORMAT {}".format(str(post_data))
                print(result)
                table.put_item(Item=post_data)
        else:
            result = "!!! POST DATA INCORRECT JSON FORMAT"
            print(result)

    return result

@app.route('/retrieve/')
def retrieve():
    """ Retrieves leaderboard.

    Retrieves leaderboard as a JSON object. Communicates with remote database to
    get remote score data, then assembles into a JSON object to reply to the
    client.
    ---

    responses:
      200:
        description: A JSON string containing highscore data.
    """
    table = DB.Table('Scores')
    result = ""

    if request.method == 'GET':
        scan = table.scan()
        if 'Items' in scan:
            print("GOOD ")
            items = json.dumps(scan['Items'], cls=DecimalEncoder)
            result += items
        print(result)

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
      201:
        description: Successful
    """

    table = DB.Table('Scores')
    result = ""

    if request.method == 'GET':
        print("ADD {} {} ATTEMPTED".format(username, score))
        response = table.put_item(
            Item={
                'username': username,
                'datetime': datetime_generate(),
                'info': {
                    'highscore': int(score)
                }
            }
        )
        result = str(response)
        print(result)
    return result

if __name__ == '__main__':
    app.run(threaded=False, port=8002)
