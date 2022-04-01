extends Area2D
class_name EnemyLaser

## EnemyLaser has two variables: a given speed and a preloaded on-hit effect.
var enemy1_laser_effect := preload("res://projectiles/EnemyLaserEffect.tscn")
export var speed := 700


## Handles laser movement.
## The enemy laser will travel to the bottom of the screen from the enemy ship.
func _physics_process(delta):
	global_position.y += speed * delta


## The parameter accepted represents the collision area of the object.
## If the player ship is hit, the player will take 15 damage and the laser will disappear.
func _on_EnemyLaser_area_entered(area):
	if area.is_in_group("player"):
		var laser_effect := enemy1_laser_effect.instance()
		laser_effect.position = position
		get_parent().add_child(laser_effect)
		area.take_damage(25)
		queue_free()

## Notifies the game to remove the enemy laser if it exits the screen.
func _on_VisibilityNotifier2D_screen_exited():
	queue_free()
