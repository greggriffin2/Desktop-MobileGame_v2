extends Sprite
class_name PlayerLaserEffect

## This script's only purpose is to remove the player laser effect from the scene.
## This is done when the effect's timer expires.

func _on_Timer_timeout():
	queue_free()
