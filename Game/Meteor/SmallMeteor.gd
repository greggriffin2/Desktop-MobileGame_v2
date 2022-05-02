extends Area2D
class_name SmallMeteor

##@Desc:
##	This class stores and distributes all data and methods related to the meteor.

onready var health_label := $HealthLabel
onready var destruction_audio := $DestructionAudio

## Exported variable for health value.
export var health: int
export var score_value: int

## Used to tell the meteor if it is colliding with the player.
var player_in_area: Player = null

## Creating a preload for the meteor's on-death effect.
var small_meteor_effect := preload("res://Meteor/SmallMeteorEffect.tscn")
var speed_powerup := preload("res://PowerUps/SpeedPowerUp.tscn")
var laser_powerup := preload("res://PowerUps/LaserPowerUp.tscn")

## Variables for randomizing rotation and speed.
var speed: float = 0
var rotation_rate: float = 0

## Establishes meteor speed and rotation at a random, limited value on spawn.
func _ready():
	speed = rand_range(20, 60)
	rotation_rate = rand_range(-50, 50)
	
## Tells the meteor to damage the player while it is colliding with the player.
func _process(delta):
	health_label.set_text(str(health))
	if player_in_area != null:
		player_in_area.take_damage(1)
	
## Handles the physics processes of meteor rotation and speed.
func _physics_process(delta):
	if rotation_rate > 0:
		position.x += (speed * delta) / 2
	else:
		position.x -= (speed * delta) / 2
	rotation_degrees += rotation_rate * delta
	position.y += speed * delta
	
## Handles any events that occur when the meteor takes damage or "dies".
## Once the meteor's health reaches zero, it will explode into smaller bits.
## After the smaller bits dissipate, the meteor object disappears from the scene.
func take_damage(amount: int):
	health -= amount
	if health <= 0:
		destruction_audio.play()
		ScoreSystem.add_score(score_value)
		ScoreSystem.add_meteor_kill()
		var effect := small_meteor_effect.instance()
		effect.position = position
		get_parent().add_child(effect)
		var powerup_roll = randf()
		if powerup_roll < 0.5:
			var powerup: SpeedPowerUp = speed_powerup.instance()
			powerup.position = position
			get_tree().current_scene.add_child(powerup)
		else:
			var powerup: LaserPowerUp = laser_powerup.instance()
			powerup.position = position
			get_tree().current_scene.add_child(powerup)
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
