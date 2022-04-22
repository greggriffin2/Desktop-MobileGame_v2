extends Node2D

onready var high_score_label := $HighScore

func _ready():
	score_display()
	
func score_display():
	high_score_label.set_text("High Score: " + str(ScoreSystem.session_high_score))

# Starts the game
func _on_StartButton_pressed():
	get_tree().change_scene("res://GameStage/gameStage.tscn")

# Exits the game
func _on_ExitButton_pressed():
	get_tree().quit()
