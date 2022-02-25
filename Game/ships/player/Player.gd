extends Area2D

##@Desc:
##	This class stores and distributes all data and methods related to the Player.
##	Player speed, initial input state, and player hit points are some initial variables.
##	The player can also move, shoot, collide

var speed = 400
var input_vector = Vector2.ZERO
var hitPoints = 100


## Handles player movement using physics and user input.
func _physics_process(delta):
	input_vector.x = Input.get_action_strength("moveRight") - Input.get_action_strength("moveLeft")
	input_vector.y = Input.get_action_strength("moveDown") - Input.get_action_strength("moveUp")

	global_position += input_vector * speed * delta


## Handles any health deductions or events that may occur from the player's ship taking damage.
func take_damage(dmg):
	pass


## Handles any changes that may occur when a player enters a passed area, or scene.
func _on_player_area_entered(area):
	pass


## Handles firing the player's main weapon.
func fire_Weapon():
	pass


## Handles switching the player's main weapon.
func switch_Weapon():
	pass


## Handles deploying the player's fully-charged special move.
func fire_Special():
	pass


## Handles collision with a passed object (i.e. enemy ships, meteors).
func onCollision(object):
	pass


## Handles all events occurring at player death (particle switch, menu prompts).
func onDeath():
	pass
