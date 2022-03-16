extends Area2D

##@Desc:
##	This is a small sample of the general Enemy class.
##	There will be variations on each of these properties in each Enemy class.
##	Possible candidate for a superclass/subclass situation.
##	Each enemy has a movement speed, a point value (score gained by player after defeat), and hit points.
##	Each enemy will also have a unique projectile and tactical pattern.

var speed = 100
var point_value = 15
var hit_points = 2

## fire_timer creates an object from the FireTimer node.
## muzzle creates an object from the E1Muzzle node.
onready var fire_timer = $FireTimer
onready var muzzle = $E1Muzzle

## Signals are added in the script to communicate with other classes.
signal spawn_enemy_laser(location)
signal enemy_died


## _physics_process handles enemy ship movement and potential tactical patterns.
func _physics_process(delta):
	global_position.y += speed * delta
	if fire_timer.is_stopped():
		projectile()


## take_damage handles any event that may occur when a ship takes damage.
## The parameter accepted is the damage taken by the enemy ship.
func take_damage(damage):
	hit_points -= damage
	if hit_points <= 0:
		queue_free()
		emit_signal("enemy_died")

## projectile spawns enemy lasers from the muzzle of the enemy ship.
func projectile():
	emit_signal("spawn_enemy_laser", muzzle.global_position)


## onDeath handles any and all events that may occur when an enemy ship reaches zero hit points.
func on_death():
	pass

## _on_enemy1_area_entered is a signal method.
## The parameter accepted is the object type that enters the enemy's collision area.
## Currently, the player ship will take 50 damage when an enemy ship makes contact.
func _on_enemy1_area_entered(area):
	if area is Player:
		area.take_damage(50)

## on_FireTimer_timeout is a signal method.
## This function calls projectile to fire lasers every 1 second from enemy ships.
func _on_FireTimer_timeout():
	projectile()
