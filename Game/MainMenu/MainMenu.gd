extends Node2D

# Starts the game
func _on_StartButton_pressed():
	get_tree().change_scene("res://GameStage/gameStage.tscn")

# Exits the game
func _on_ExitButton_pressed():
	get_tree().quit()
