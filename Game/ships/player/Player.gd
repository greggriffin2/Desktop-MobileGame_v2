extends Area2D
class_name Player

##@Desc:
##	This class stores and distributes all data and methods related to the Player.
##	Player speed, initial input state, and player hit points are some initial variables.
##	The player can also move, shoot, and collision is detected.

## Signals are added in the script to communicate with other classes.
signal spawn_player_laser(location)
signal minus_health

## muzzle creates an object from the PMuzzle node.
onready var muzzle = $PMuzzle

var speed = 400
var input_vector = Vector2.ZERO
var hit_points = 100


## Handles player movement using physics and user input.
func _physics_process(delta):
	input_vector.x = Input.get_action_strength("ui_right") - Input.get_action_strength("ui_left")
	input_vector.y = Input.get_action_strength("ui_down") - Input.get_action_strength("ui_up")

	global_position += input_vector * speed * delta

	if Input.is_action_just_pressed("fire_weapon"):
		fire_weapon()


## Handles any health deductions or events that may occur from the player's ship taking damage.
func take_damage(damage):
	hit_points -= damage
	if hit_points <= 0:
		queue_free()
		emit_signal("player_died")


## Handles firing the player's main weapon by
## emitting the player laser signal at the player ship's muzzle.
func fire_weapon():
	emit_signal("spawn_player_laser", muzzle.global_position)


## Handles any changes that may occur when an object enters the player's collision area.
## Currently, an enemy ship will take 3 damage upon colliding with the player ship.
func _on_Player_area_entered(area):
	if area.is_in_group("enemies"):
		area.take_damage(3)


## Handles switching the player's main weapon.
func switch_weapon():
	pass


## Handles deploying the player's fully-charged special move.
func fire_special():
	pass


## Handles collision with a passed object (i.e. enemy ships, meteors).
func on_collision(object):
	pass


## Handles all events occurring at player death (particle switch, menu prompts).
func on_death():
	pass
