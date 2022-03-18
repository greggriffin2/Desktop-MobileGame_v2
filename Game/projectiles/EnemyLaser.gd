extends Area2D

var speed = 1000


## _physics_process handles laser movement.
## The enemy laser will travel to the bottom of the screen from the enemy ship.
func _physics_process(delta):
	global_position.y += speed * delta


## _on_EnemyLaser_area_entered is a signal method.
## The parameter accepted represents the collision area of the object.
## If the player ship is hit, the player will take 15 damage and the laser will disappear.
func _on_EnemyLaser_area_entered(area):
	if area.is_in_group("player"):
		area.take_damage(15)
		queue_free()
