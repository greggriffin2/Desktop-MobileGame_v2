from aiohttp import web
import socketio

sio = socketio.AsyncServer()
app = web.Application()
sio.attach(app)


async def index(request):
    """Serve the client-side application."""
    return web.Response(text="f.read()", content_type='text/html')


@sio.on("connect")
def connect(sid, environ):
    """
    Handles websocket connection events
    :param sid: socketio object
    :param environ: environment object
    :return:
    """
    print("connect ", sid)


@sio.on("chat_message")
async def chat_message(sid, data):
    """
    Handles messages between websockets in the same room
    :param sid: socketio object
    :param data: string-like data
    :return: None
    """
    print("message ", data)


@sio.on("disconnect")
def disconnect(sid):
    """
    Handles websockets disconnecting
    :param sid: socketio object
    :return: None
    """
    print('disconnect ', sid)

if __name__ == '__main__':
    web.run_app(app)
