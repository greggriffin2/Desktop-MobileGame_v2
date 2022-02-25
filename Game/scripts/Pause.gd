extends Control

##@Desc:
##	This class is specifically for pausing and unpausing the game.
##	The variable "visible" is present in regards to the pause menu.
##	While unPaused is true, visible is false, and vise versa.
##	This class will also call events that may occur in the pause menu's buttons.

var unPaused = true


## _process will accept user input and process get_tree().paused based on the boolean of unPaused.
## This method is meant to separate pausing the game from stopping this particular process,
## while layering on top of and stopping all other processes.
func _process(delta):
	if Input.is_action_just_pressed("gamePause"):
		if unPaused:
			get_tree().paused = true
			unPaused = false
			visible = true
		else:
			get_tree().paused = false
			unPaused = true
			visible = false


## Each of these buttons will perform the desired action.


## onContinue will resume the game.
func onContinue():
	pass


## onRestart will restart the current level.
func onRestart():
	pass


## onMenu will return the user to the main menu.
func onMenu():
	pass
