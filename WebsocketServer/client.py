import asyncio
import websockets
import json
import uuid

## Client Backend globals

client_uuid = uuid.uuid4() # uuid object; not a str

## Client Backend functions

def build_init_session_message(): # game does this
	u = str(client_uuid)
	d = {"type": "INIT", "id": u}
	result = json.dumps(d)
	return result

def build_close_session_message(): # game does this
	u = str(client_uuid)
	d = {"type": "CLOSE", "id": u}
	result = json.dumps(d)
	return result

def build_push_message(game): # app does this
	d = {"type": "PUSH", "game": game}
	result = json.dumps(d)
	return result

## Client functions

async def main():
	async with websockets.connect("ws://localhost:8001") as websocket:
		await websocket.send(build_init_session_message())
		message = await websocket.recv() # get back stuff (session ID/game code ID ?)
		print(message)
		try:
			received = json.loads(message)
		except JSONDecodeError:
			print("Error receiving JSON data from connection {}".format(websocket))
			return

#		game = "0123456" # test value of some example game-join-code we got back
		game = received["code"]

		while (True):
			try:
				input() # wait for ENTER press / Ctrl-C
			except KeyboardInterrupt:
				await websocket.send(build_close_session_message())
				ack = await websocket.recv() # get back confirm closed
				return
			await websocket.send(build_push_message(game))
			x = await websocket.recv() # get back echo
			print(x)

## Client launch
if __name__ == "__main__":
	asyncio.run(main())


