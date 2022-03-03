extends Control

##@Desc:
##	This class is specifically for pausing and unpausing the game.
##	The variable "visible" is present in regards to the pause menu.
##	While unPaused is true, visible is false, and vise versa.
##	This class will also call events that may occur in the pause menu's buttons.

var un_paused = true


## _process accepts delta (real time variable) and processes get_tree().paused based on the boolean of un_paused.
## This method is meant to separate pausing the game from stopping this particular process,
## while layering on top of and stopping all other processes.
func _process(delta):
	if Input.is_action_just_pressed("ui_cancel"):
		if un_paused:
			get_tree().paused = true
			un_paused = false
			visible = true
		else:
			get_tree().paused = false
			un_paused = true
			visible = false


## Each of these buttons will perform the desired action.


## on_continue resumes the game.
func on_continue():
	pass


## on_restart restarts the current level.
func on_restart():
	pass


## on_menu returns the user to the main menu.
func on_menu():
	pass
