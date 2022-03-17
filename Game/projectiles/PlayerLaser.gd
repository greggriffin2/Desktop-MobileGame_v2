extends Area2D

var speed = 1000


## _physics_process handles laser movement.
## The player's laser will move from the player ship's muzzle to the top of the screen.
func _physics_process(delta):
	global_position.y += -speed * delta


## _on_PlayerLaser_area_entered is a signal method.
## The parameter accepted represents the collision area of the object.
## If an enemy ship is hit, it will take 1 damage and the laser will disappear.
func _on_PlayerLaser_area_entered(area):
	if area.is_in_group("enemies"):
		area.take_damage(1)
		queue_free()
