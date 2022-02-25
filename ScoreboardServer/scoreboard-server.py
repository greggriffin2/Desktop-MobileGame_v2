from flask import Flask, request
import json

app = Flask(__name__)


@app.route('/retrieve/')
def retrieve():
    """ Retrieves leaderboard.

    Retrieves leaderboard as a JSON object. Communicates with remote database to
    get remote score data, then assembles into a JSON object to reply to the
    client.

    parameters:
      - in: path
        name: username
        required: false ???
        description: Username to retrieve
      - in: path
        name: score
        required: false ???
        description: Game highscore to store into remote database.
    responses:
      200:
        description: A JSON string containing highscore data.
    """
    if request.method == 'GET':
        print("RETRIEVE ATTEMPTED")
        return "RETRIEVE ATTEMPTED"


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
    if request.method == 'GET':
        print("ADD {} {} ATTEMPTED".format(username, score))
        return "ADD ATTEMPTED"


if __name__ == '__main__':
    app.run(threaded=False, port=8002)
