extends Node2D

# Declaring a variable for the end-screen score label
onready var score_label := $Score
var score: int = 0	# Placeholder for high score implementation

# Placeholder for end score implementation
func score_display():
	score_label.text = "SCORE: " + str(score)

# Restarts the game stage scene
func _on_RetryButton_pressed():
	get_tree().change_scene("res://GameStage/gameStage.tscn")

# Placeholder for leaderboard implementation
func _on_LeaderboardButton_pressed():
	pass

# Returns the user to the main menu
func _on_MenuButton_pressed():
	get_tree().change_scene("res://MainMenu/MainMenu.tscn")

# Exits the game
func _on_ExitButton_pressed():
	get_tree().quit()
