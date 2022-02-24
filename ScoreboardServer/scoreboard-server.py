from flask import Flask, request
import json
app = Flask(__name__)

""" Retrieves leaderboard.

Retrieves leaderboard as a JSON object. Communicates with remote database to 
get remote score data, then assembles into a JSON object to reply back to the
client. 

Args:
	username (str): Username to store into remote database.
	score (str): Game highscore to store into remote database.

Returns:
	A JSON string containing highscore data.

"""
@app.route('/retrieve/')
def retrieve():
	if request.method == 'GET':
		print("RETRIEVE ATTEMPTED")
		return "RETRIEVE ATTEMPTED"

""" Adds a username & score to database.

Adds a username and score to a remote database as a single new entry in the table. 

Args:
	username (str): Username to store into remote database.
	score (str): Game highscore to store into remote database.

"""
@app.route('/add/<username>/<score>/')
def add(username, score):
	if request.method == 'GET':
		print("ADD {} {} ATTEMPTED".format(username, score))
		return "ADD ATTEMPTED"

if __name__ == '__main__':
    app.run(threaded=False, port=8002)
