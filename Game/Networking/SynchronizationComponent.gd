## Global Syncronization Component
class_name SynchronizationSingleton
extends Node

signal add_score

var peer = WebRTCMultiplayer.new()
var serverConnection = WebSocketClient.new()


# Called when the syncrhonization component is first initialized
func _ready():
	# TODO: Implement setting up connection with server
	pass


func player_score_update(current_score: int):
	emit_signal("add_score")


# Health update called when the health of the player updates
func player_health_update(current_health: float, max_health: float):
	pass


# Called when powerups are obtained
# TODO: What information does the player need?
func notify_powerup(powerup):
	pass


func get_gamecode() -> String:
	return "Not Implemented!"


# Should be called when the game state is updated
func notify_gamestate():
	pass
