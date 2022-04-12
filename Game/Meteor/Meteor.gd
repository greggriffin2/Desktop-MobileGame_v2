extends Area2D
class_name Meteor

##@Desc:
##	This class stores and distributes all data and methods related to the meteor.

## Exported variables for rotation and speed bounds, as well as health value.
export var min_speed: float = 20
export var max_speed: float = 60
export var min_rotation_speed: float = -50
export var max_rotation_speed: float = 50
export var health: int = 20

## Used to tell the meteor if it is colliding with the player.
var player_in_area: Player = null

## Creating a preload for the meteor's on-death effect.
var meteor_effect := preload("res://Meteor/MeteorEffect.tscn")

## Variables for randomizing rotation and speed.
var speed: float = 0
var rotation_rate: float = 0

## Establishes meteor speed and rotation at a random, limited value on spawn.
func _ready():
	speed = rand_range(min_speed, max_speed)
	rotation_rate = rand_range(min_rotation_speed, max_rotation_speed)
	
## Tells the meteor to damage the player while it is colliding with the player.
func _process(delta):
	if player_in_area != null:
		player_in_area.take_damage(1)
	
## Handles the physics processes of meteor rotation and speed.
func _physics_process(delta):
	rotation_degrees += rotation_rate * delta
	position.y += speed * delta
	
## Handles any events that occur when the meteor takes damage or "dies".
## Once the meteor's health reaches zero, it will explode into smaller bits.
## After the smaller bits dissipate, the meteor object disappears from the scene.
func take_damage(amount: int):
	health -= amount
	if health <= 0:
		var effect := meteor_effect.instance()
		effect.position = position
		get_parent().add_child(effect)
		
		Signals.emit_signal("on_score_increment", 50)
		
		queue_free()

## Notifies the game to remove the meteor if it exits the screen.
func _on_VisibilityNotifier2D_screen_exited():
	queue_free()

## The following two methods monitor player-meteor interaction.
## When the player is not in the meteor's area, the value is null.
## Otherwise, the value is Player and the player will take damage.
func _on_Meteor_area_entered(area):
	if area is Player:
		player_in_area = area
		

func _on_Meteor_area_exited(area):
	if area is Player:
		player_in_area = null