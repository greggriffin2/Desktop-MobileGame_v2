import asyncio
import websockets

async def handler(websocket):
	while True:
		try:
			message = await websocket.recv()
		except websockets.exceptions.ConnectionClosedOK:
			return # connection was closed, everything is happy
		print(message)
		await websocket.send(message) # echo


async def main():
	async with websockets.serve(handler, "", 8001):
		await asyncio.Future()  # run forever


if __name__ == "__main__":
	asyncio.run(main())

