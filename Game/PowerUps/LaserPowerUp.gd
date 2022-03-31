extends "res://PowerUps/PowerUp.gd"
class_name LaserPowerUp

## This script inherits behaviors from the base powerup class.
## The only added function is to remove the powerup from the scene if the player collects it.

func _on_LaserPowerUp_area_entered(area):
	if area.is_in_group("player"):
		queue_free()
