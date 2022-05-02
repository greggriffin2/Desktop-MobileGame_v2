extends Node

var game_code = ""
var connection_status = ""
var sync_singleton: SynchronizationSingleton

var player_position = Vector2.ZERO
var player_health = 0

func _ready():
	sync_singleton = get_node("/root/NetworkSynchronizationSingleton")
	sync_singleton.connect("user_joined", self, "_user_joined")
	sync_singleton.connect("user_left", self, "_user_left")
	
func update_player_position(x, y):
	player_position = Vector2(x,y)
	
func update_health(health):
	player_health = health
	
	
func _user_joined():
	connection_status = "User Connected!"
	$GameCodeDisplay/Control/ConnectedButton.visible = true
	
func _user_left():
	connection_status = "User Disconnected"
