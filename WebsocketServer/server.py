import asyncio
import websockets
import json

## Server Backend globals

active_connections = [] # list of active uuids 
# todo? change to dictionary? store conn w/ join-code? use other system?

## Server Backend functions

def build_init_ok_message(u): # server respond OK to init with game code
	u = str(u)
	d = {"type": "OK_INIT", "id": u, "code": "TESTCODE"}
	result = json.dumps(d)
	return result

def build_close_ok_message(u): # server respond to CLOSE with an OK
	u = str(u)
	d = {"type": "OK_CLOSE", "id": u}
	result = json.dumps(d)
	return result

## Server functions

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
			print("Saw INIT, Adding new uuid to list.")
			active_connections.append(received["id"])
			await websocket.send(build_init_ok_message(received["id"]))
		elif received["type"] == "CLOSE":
			print("Saw CLOSE, attempting remove from active list.")
			active_connections.remove(received["id"])
			await websocket.send(build_close_ok_message(received["id"]))
		else:
			await websocket.send(message) # echo (should probably have a custom server OK type of message to reply back with?)
			
		print("Current connections: {}".format(active_connections))

async def main():
	async with websockets.serve(handler, "", 8001):
		await asyncio.Future()  # run forever

## Server launch

if __name__ == "__main__":
	asyncio.run(main())

