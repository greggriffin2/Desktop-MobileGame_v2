extends Node2D
class_name gameStage

## When the game stage scene is loaded, the following values are reset to zero. 
func _ready():
	$SlowMusic.play()
	ScoreSystem.reset()
	
