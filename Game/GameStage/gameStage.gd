extends Node2D

## When the game stage scene is loaded, the following values are reset to zero. 
func _ready():
	ScoreSystem.score = 0
	ScoreSystem.score_multiplier = 1
	ScoreSystem.enemies_killed = 0
	ScoreSystem.meteors_killed = 0
