extends Area2D

##@Desc:
##	This is a small sample of the general Enemy class. 
##	There will be variations on each of these properties in each Enemy class.
##	Possible candidate for a superclass/subclass situation.
##	Each enemy has a movement speed, a point value (score gained by player after defeat), and hit points.
##	Each enemy will also have a unique projectile and tactical pattern.

var speed = 200
var pointValue = 15
var hitPoints = 1

##	_physics_process handles enemy ship movement and potential tactical patterns.
func _physics_process(delta):
	global_position.y += speed * delta

##	take_damage handles any event that may occur when a ship takes damage.
##	The parameter accepted is the object that collides with the enemy ship.
func take_damage(object):
	pass
	
##	
func projectile():
	pass
	
##	onDeath handles any and all events that may occur when an enemy ship reaches zero hit points.
func onDeath():
	pass
	

