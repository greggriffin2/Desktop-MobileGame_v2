extends Sprite
class_name EnemyLaserEffect

## This script's only purpose is to remove the enemy laser effect from the scene.
## This is done when the effect's timer expires.

func _on_Timer_timeout():
	queue_free()
