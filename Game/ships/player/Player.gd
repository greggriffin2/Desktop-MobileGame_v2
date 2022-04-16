extends Area2D
class_name Player

##@Desc:
##	This class stores and distributes all data and methods related to the player.

## Creating preloads of the player laser and some on-death effects for the player.
var player_laser := preload("res://projectiles/PlayerLaser.tscn")
var on_death_effects := [
	preload("res://Explosions/ODELayer1.tscn"),
	preload("res://Explosions/ODELayer2.tscn"),
	preload("res://Explosions/ODELayer3.tscn")
]
#var sync_component := get_node("/root/NetworkSynchronizationSingleton")

## Exported variables for speed, health, and automatic firing delays for both weapons.
export var fire_delay: float = 0.1
export var slow_fire_delay: float = 0.15
export var speed: float = 400
export var hit_points: int = 100

## Declaring variables for various children of Player.
onready var sprite := $Sprite
onready var firing_positions := $FiringPositions
onready var slow_fire_timer := $SlowAutoFireTimer
onready var fire_timer := $AutoFireTimer
onready var speed_up_timer := $SpeedPowerUpTimer
onready var laser_up_timer := $LaserPowerUpTimer

## Initializing input vector.
var input_vector = Vector2.ZERO

## Setting boolean for the laser powerup to false.
var dual_laser = false

## Signal(s) loaded at the start of the scene.
func _ready():
	Signals.emit_signal("on_player_life_change", hit_points)

## System checks for each of these processes repeatedly.
## In this case, the system will check for player movement and input actions.
func _process(delta):
	if input_vector.x < 0:
		sprite.frame = 0
	elif input_vector.x > 0:
		sprite.frame = 2
	else:
		sprite.frame = 1
		
	if Input.is_action_pressed("fire_weapon") and slow_fire_timer.is_stopped():
		slow_fire_timer.start(slow_fire_delay)
		var laser := player_laser.instance()
		laser.position = position
		get_tree().current_scene.add_child(laser)
		
	if Input.is_action_pressed("fire_auto") and fire_timer.is_stopped() and dual_laser == true:
		fire_timer.start(fire_delay)
		for child in firing_positions.get_children():
			var laser := player_laser.instance()
			laser.global_position = child.global_position
			get_tree().current_scene.add_child(laser)


## Handles player movement using physics and user input.
## Also handles screen boundaries for the player.
func _physics_process(delta):
	input_vector.x = Input.get_action_strength("ui_right") - Input.get_action_strength("ui_left")
	input_vector.y = (Input.get_action_strength("ui_down") + 0.1) - Input.get_action_strength("ui_up")

	global_position += input_vector * speed * delta
	
	var viewRect := get_viewport_rect()
	global_position.x = clamp(global_position.x, 15, viewRect.size.x - 15)
	global_position.y = clamp(global_position.y, 15, viewRect.size.y - 15)


## Handles any health deductions or events that may occur from the player's ship taking damage.
func take_damage(damage):
	hit_points -= damage
	Signals.emit_signal("on_player_life_change", hit_points)
	if hit_points <= 0:
		on_death()
	
		
func on_death():
	for effect in on_death_effects:
		var explosion = effect.instance()
		explosion.global_position = global_position
		get_tree().current_scene.add_child(explosion)
		queue_free()
			

## Handles any changes that may occur when an object enters the player's collision area.
## Currently, an enemy ship will take 3 damage upon colliding with the player ship.
## Additionally, the laser and speed powerups will boost the player accordingly.
func _on_Player_area_entered(area):
	if area.is_in_group("damageable"):
		area.take_damage(3)
	elif area.is_in_group("speedpowerup"):
		if speed == 550:
			speed = 550
		else:
			speed += 150
		Signals.emit_signal("on_speed_change", speed)
		speed_up_timer.start(5)
	elif area.is_in_group("laserpowerup"):
		dual_laser = true
		laser_up_timer.start(8)

## Removes speed bonus once the speed powerup timer runs out.
func _on_SpeedPowerUpTimer_timeout():
	speed = 400
	
## Removes laser bonus once the laser powerup timer runs out.
func _on_LaserPowerUpTimer_timeout():
	dual_laser = false
	
