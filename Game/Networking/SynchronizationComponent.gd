## Global Syncronization Component
class_name SynchronizationSingleton
extends Node

signal on_signaling_initialized
signal on_gamecode_update
signal on_webrtc_initialized


export var websocket_url = "wss://pedanticmonkey.space/rooms"

var webrtc_mp: WebRTCMultiplayer
var signaling_connection: WebSocketClient
var initialized: bool
var game_code: String


# Called when the syncrhonization component is first initialized
func _ready():
	signaling_connection = WebSocketClient.new()
	# webrtc_mp = WebRTCMultiplayer.new()
	init_signaling_server()
	# init_webrtc_server()


func _process(_delta):
	signaling_connection.poll()


func _on_closed(was_clean = false):
	# was_clean will tell you if the disconnection was correctly notified
	# by the remote peer before closing the socket.
	print_debug("Closed, clean: ", was_clean)
	set_process(false)


# initializes the connection with the room server
func _on_connected(protocol):
	signaling_connection.get_peer(1).set_write_mode(WebSocketPeer.WRITE_MODE_TEXT)
	print_debug("Websocket connected to ", signaling_connection.get_connected_host())
	print_debug("Websocket protocol: ", protocol)
	var packet_response = signaling_connection.get_peer(1).put_packet('"NewRoom"'.to_utf8())
	print_debug("Packet response: ", packet_response)


func _on_data():
	var data = signaling_connection.get_peer(1).get_packet()
	var json_parse = JSON.parse(data.get_string_from_utf8())
	if json_parse.error != OK:
		print("Failed to parse data as json object!\n", json_parse.error_string)
		signaling_connection.disconnect_from_host(1011, "Recieved invalid json object")
		initialized = false
		return
	# Due to cyclic dependency issues this has to be generated here.
	# There are better ways to do this.

	handle(json_parse, self)


func connect_ws():
	print_debug("Connecting to server...")
	var err = webrtc_mp.connect_to_url(websocket_url)
	if err != OK:
		print("Could not connect to websocket server, Error:", err)


# Msc initialization
func init_signaling_server():
	initialized = false
	game_code = "[Generating...]"
	signaling_connection.connect("connection_established", self, "_on_connected")
	signaling_connection.connect("connection_failed", self, "_on_closed")
	signaling_connection.connect("connection_error", self, "_on_closed")
	signaling_connection.connect("data_received", self, "_on_data")


func init_webrtc_server():
	pass


# Updates the game code for any events listening for the signal
func set_gamecode(code: String):
	game_code = code
	emit_signal("on_gamecode_update", game_code)


func reload_connection():
	print("Reloading connection...")
	signaling_connection.disconnect_from_host(1000, "User reloaded")
	initialized = false
	var err = signaling_connection.connect_to_url(websocket_url)
	if err != OK:
		print_debug("Could not connect to websocket server, Error: ", err)


func send_powerup_status(statusEnum: int, duration: float):
	var data = "{'PowerUpStatus:'"+str(statusEnum)+",'duration:"+str(duration)+"}"
	send_data(data.to_ascii())

func handle(jsn: JSONParseResult, mpsingleton):
	if typeof(jsn.result) == TYPE_ARRAY:
		return handle_array_data(jsn.result, mpsingleton)
	elif typeof(jsn.result) == TYPE_DICTIONARY:
		return handle_dictionary_data(jsn.result, mpsingleton)


func handle_dictionary_data(data: Dictionary, mpsingleton):
	match data:
		{}:
			pass
		{"JoinRoom": var roomKey, ..}:
			mpsingleton.set_gamecode(roomKey)
		{"status": var status, "duration": var duration, ..}:
			pass
		{"timesPressed": var timesPressed, ..}:
			pass
		{"enemyType": var enemyType, ..}:
			pass
		_:
			pass


func handle_array_data(data: Array, mpsingleton):
	pass
