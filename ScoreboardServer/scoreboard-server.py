from flask import Flask, request
import json
app = Flask(__name__)

""" figure this out later i guess maybe
@app.route('/', methods=['POST'])
def post_endpoint():
	pass
"""

@app.route('/retrieve/')
def retrieve():
	if request.method == 'GET':
		print("RETRIEVE ATTEMPTED")
		return "RETRIEVE ATTEMPTED"

""" TODO figure this out later i guess maybe
@app.route('/add')
def add():
	if request.method == 'POST':
		pass
"""
@app.route('/add/<username>/<score>/')
def add(username, score):
	if request.method == 'GET':
		print("ADD {} {} ATTEMPTED".format(username, score))
		return "ADD ATTEMPTED"

if __name__ == '__main__':
    app.run(threaded=False, port=8002)
