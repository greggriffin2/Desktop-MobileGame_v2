import datetime
import time
import json
import boto3
from flask import Flask, request
app = Flask(__name__)
DB = boto3.resource('dynamodb', endpoint_url="http://dynamodb.us-east-1.amazonaws.com", region_name='us-east-1')

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


@app.route('/', methods=['POST', 'GET'])
def post_endpoint():
    """ Respond to POST data.

    Receive POST data and respond based on the value of the 'type' field in the data.
    Data is expected to be JSON format.
    ---

    responses:
      200:
        description: A JSON string containing response data.
    """

    if request.method == 'POST':
        post_data = request.get_json()
        if post_data is None:
            print("Bad JSON data in POST body!")
            return ""
        if 'type' in post_data and isinstance(post_data, dict):
            print("POST DATA IS DICT AND HAS A TYPE! TYPE IS:  {}".format(post_data['type']))
        else:
            print("!!! POST DATA INCORRECT FORMAT")
        print("POST ENDPOINT ACCESSED: {}".format(post_data))
        return "POST ENDPOINT ACCESSED"

    return ""

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
        result = str(table.scan())
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
                'datetime': int(time.mktime(datetime.datetime.now().timetuple())),
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
