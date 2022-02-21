import asyncio
import websockets

async def main():
	async with websockets.connect("ws://localhost:8001") as websocket:
		await websocket.send("Hello world!")
		x = await websocket.recv()
		print(x)

# Client launch
if __name__ == "__main__":
	asyncio.run(main())


