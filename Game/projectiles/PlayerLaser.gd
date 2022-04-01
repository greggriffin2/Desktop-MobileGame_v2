extends Area2D
class_name PlayerLaser

## PlayerLaser has two variables: a given speed and a preloaded on-hit effect.
var player_laser_effect := preload("res://projectiles/PlayerLaserEffect.tscn")
export var speed := 1000


## _physics_process handles laser movement.
## The player's laser will move from the player ship's muzzle to the top of the screen.
func _physics_process(delta):
	global_position.y -= speed * delta


## _on_PlayerLaser_area_entered is a signal method.
## The parameter accepted represents the collision area of the object.
## If an enemy ship is hit, it will take 1 damage.
## Additionally, when an enemy ship is damaged, an on-hit effect appears.
func _on_PlayerLaser_area_entered(area):
	if area.is_in_group("damageable"):
		var laser_effect := player_laser_effect.instance()
		laser_effect.position = position
		get_parent().add_child(laser_effect)
		area.take_damage(1)
		queue_free()


## Notifies the game to remove the player laser if it exits the screen.
func _on_VisibilityNotifier2D_screen_exited():
	queue_free()
