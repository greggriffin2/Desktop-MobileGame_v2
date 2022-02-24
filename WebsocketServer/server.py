import asyncio
import websockets
import json

## Server Backend globals

active_connections = [] # list of active uuids 
# todo? change to dictionary? store conn w/ join-code? use other system?

## Server Backend functions


""" Build a JSON response to game client 'INIT' request.

Constructs and returns a JSON string that contains a 'type' key with value 'OK_INIT', an 'id'
key with value equal to the string of the received client UUID, and a 'code' key with value
of the string representing the game's Join Code for use with the companion app.

Args:
	u (str): Game client UUID received from client's 'INIT' request.

Returns:
	result (str): JSON string containing response.
"""
def build_init_ok_message(u): # server respond OK to init with game code
	d = {"type": "OK_INIT", "id": u, "code": "TESTCODE"}
	result = json.dumps(d)
	return result

""" Build a JSON response to game client 'CLOSE' request.

Constructs and returns a JSON string that contains a 'type' key with value 'OK_CLOSE', and
an 'id' key with value equal to the string of the received client UUID.

Args:
	u (str): Game client UUID received from client's 'CLOSE' request.

Returns:
	result (str): JSON string containing response.
"""
def build_close_ok_message(u): # server respond to CLOSE with an OK
	d = {"type": "OK_CLOSE", "id": u}
	result = json.dumps(d)
	return result

## Server functions

""" Handle a client 'INIT' request.

Add client's UUID to the active connections, and send an 'OK_INIT' response.

Args:
	websocket (obj): Websocket object passed down from the handler method. 
	uuid (str): Game client UUID received from client 'INIT' request.
"""
async def handleInit(websocket, uuid): # note uuid is a string!
	print("Saw INIT, Adding new uuid to list.")
	active_connections.append(uuid)
	await websocket.send(build_init_ok_message(uuid))

""" Handle a client 'CLOSE' request.

Remove client's UUID from the active connections, and send an 'OK_CLOSE' response.

Args:
	websocket (obj): Websocket object passed down from the handler method. 
	uuid (str): Game client UUID received from client 'CLOSE' request.
"""
async def handleClose(websocket, uuid):
	print("Saw CLOSE, attempting remove from active list.")
	active_connections.remove(uuid)
	await websocket.send(build_close_ok_message(uuid))

""" Handle a client websocket request.

Handle a websocket request, parsing its JSON contents and either calling
an appropriate subhandler depending on the value of the 'type' key in the JSON data,
or echoing the data back to the client.

Add client's UUID to the active connections, and send an 'OK_INIT' response.

Args:
	websocket (obj): Websocket object automatically passed from prior websockets.serve call. 
"""
async def handler(websocket):
	while True:
		try:
			message = await websocket.recv()
		except websockets.exceptions.ConnectionClosedOK:
			print("Connection closed remotely.")
			return
		print(message)
		try:
			received = json.loads(message)
		except JSONDecodeError:
			print("Error receiving JSON data from connection {}".format(websocket))
			return

		if received["type"]  == "INIT":
			await handleInit(websocket, received["id"])
		elif received["type"] == "CLOSE":
			await handleClose(websocket, received["id"])
		else:
			await websocket.send(message) # echo (should probably have a custom server OK type of message to reply back with?)

		print("Current connections: {}".format(active_connections))

""" Run websocket server.

Launches a websocket server which is able to asynchronously establish
and close remote client connections.

"""
async def main():
	async with websockets.serve(handler, "", 8001):
		await asyncio.Future()  # run forever

## Server launch

if __name__ == "__main__":
	asyncio.run(main())

