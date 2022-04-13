extends Control
class_name Pause

##@Desc:
##	This class is specifically for pausing and unpausing the game.
##	The variable "visible" is present in regards to the pause menu.
##	While unpaused is true, visible is false, and vise versa.
##	This class will also call events that may occur in the pause menu's buttons.

var unpaused = true

## _process accepts delta (real time variable) and processes get_tree().paused based on the boolean of un_paused.
## This method is meant to separate pausing the game from stopping this particular process,
## while layering on top of and stopping all other processes.
func _process(delta):
	if Input.is_action_just_pressed("ui_cancel"):
		if unpaused:
			get_tree().paused = true
			unpaused = false
			visible = true
		else:
			get_tree().paused = false
			unpaused = true
			visible = false


## Each of these buttons will perform the desired action.
	
## Resumes the game.
func _on_continueButton_pressed():
	get_tree().paused = false
	unpaused = true
	visible = false

## Restarts the current level.
func _on_restartButton_pressed():
	get_tree().paused = false
	get_tree().reload_current_scene()
	
## Returns the user to the main menu.
func _on_menuButton_pressed():
	get_tree().paused = false
	get_tree().change_scene("res://MainMenu/MainMenu.tscn")

## Exits the game.
func _on_exitButton_pressed():
	get_tree().paused = false
	get_tree().quit()
