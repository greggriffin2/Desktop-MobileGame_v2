extends Node2D

onready var high_score_label := $HighScore
onready var game_code_label := $GameCodeLabel

func _ready():
	score_display()
	game_code_label.set_text("Game Code: " + PlayerSingleton.game_code)
	
func score_display():
	high_score_label.set_text("High Score: " + str(ScoreSystem.session_high_score))

# Starts the game
func _on_StartButton_pressed():
	get_tree().change_scene("res://GameStage/gameStage.tscn")

# Exits the game
func _on_ExitButton_pressed():
	get_tree().quit()

func _on_NewCodeButton_pressed():
	get_tree().change_scene("res://Networking/GameCodeDisplay/GameCodeDisplay.tscn")


func _on_CreditsButton_pressed():
	get_tree().change_scene("res://Scenes/CreditsScene.tscn")
