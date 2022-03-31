extends Area2D
class_name PowerUp

##@Desc:
##	This is the base powerup class. Each powerup class is inherited from the methods of this class.
##	Each powerup falls down the screen after an enemy is killed.
##	If the powerup exits the screen without being collected, it will remove itself from the scene.

var speed := 100

func _ready():
	set_process(true)
	
func _process(delta):
	global_position.y += speed * delta
	
func _on_VisibilityNotifier2D_screen_exited():
	queue_free()
