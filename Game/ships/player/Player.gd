extends Area2D
class_name Player

##@Desc:
##	This class stores and distributes all data and methods related to the player.

## Creating preloads of the player laser and some on-death effects for the player.
var player_laser := preload("res://projectiles/PlayerLaser.tscn")
var player_laser_up := preload("res://projectiles/LaserUpgrade.tscn")
var on_death_effects := [
	preload("res://Explosions/ODELayer1.tscn"),
	preload("res://Explosions/ODELayer2.tscn"),
	preload("res://Explosions/ODELayer3.tscn")
]
var player_bomb := preload("res://projectiles/Bomb.tscn")
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
onready var laser_time := $LaserTime
onready var speed_time := $SpeedTime
onready var speed_up_timer := $SpeedPowerUpTimer
onready var laser_up_timer := $LaserPowerUpTimer
onready var laser_timer_label := $LaserTimerLabel
onready var speed_timer_label := $SpeedTimerLabel
onready var power_up_label := $PowerUpLabel
onready var power_up_label_timer := $PowerUpLabelTimer
onready var invincibility_timer := $InvincibilityFrames
onready var invincibility_shield := $InvincibilityShield
onready var shield_woompf := $ShieldWoompf
onready var death_audio := $DeathAudio
onready var base_laser_audio_list := [$BaseLaserAudio, $BaseLaserAudio2, $BaseLaserAudio3, $BaseLaserAudio4, $BaseLaserAudio5]
onready var laser_up_audio_list := [$LaserUpAudio, $LaserUpAudio2, $LaserUpAudio3, $LaserUpAudio4, $LaserUpAudio5]
onready var base_music := $SlowMusic
onready var fast_music := $FastMusic
onready var stinger := $Stinger
onready var power_up_notifier := $PowerUpNotifier
onready var care_package_notifier := $CarePackageNotifier


## Initializing input vector.
var input_vector = Vector2.ZERO
var enemies: int = 0
var sync_singleton: SynchronizationSingleton
var press_counter: int = 0
var laser_power_up_seconds: int
var speed_power_up_seconds: int
var laser_audio_count: int

## Setting boolean for the laser powerup to false.
var laser_up = false

## Signal(s) loaded at the start of the scene.
func _ready():
	invincibility_shield.visible = false
	Signals.emit_signal("on_player_life_change", hit_points)
	sync_singleton = get_node("/root/NetworkSynchronizationSingleton")
	sync_singleton.connect("on_app_button_press", self, "_on_app_button_press")

## System checks for each of these processes repeatedly.
## In this case, the system will check for player movement and input actions.
func _process(delta):
	if input_vector.x < 0:
		sprite.frame = 0
	elif input_vector.x > 0:
		sprite.frame = 2
	else:
		sprite.frame = 1
	
	enemies = ScoreSystem.enemies_killed
		
	if Input.is_action_pressed("fire_weapon") and slow_fire_timer.is_stopped():
		slow_fire_timer.start(slow_fire_delay)
		if laser_up == false:
			base_laser_audio_list[laser_audio_count].play()
			laser_audio_count += 1
			if laser_audio_count == len(base_laser_audio_list):
				laser_audio_count = 0
			if enemies < 30:
				var laser := player_laser.instance()
				laser.position = position
				get_tree().current_scene.add_child(laser)
			elif enemies < 60:
				for child in firing_positions.get_children():
					if child != $FiringPositions/CenterMuzzle:
						var laser := player_laser.instance()
						laser.global_position = child.global_position
						get_tree().current_scene.add_child(laser)
			else:
				for child in firing_positions.get_children():
					var laser := player_laser.instance()
					laser.global_position = child.global_position
					get_tree().current_scene.add_child(laser)
		else:
			laser_up_audio_list[laser_audio_count].play()
			laser_audio_count += 1
			if laser_audio_count == len(laser_up_audio_list):
				laser_audio_count = 0
			if enemies < 30:
				var laser := player_laser_up.instance()
				laser.position = position
				get_tree().current_scene.add_child(laser)
			elif enemies < 60:
				for child in firing_positions.get_children():
					if child != $FiringPositions/CenterMuzzle:
						var laser := player_laser_up.instance()
						laser.global_position = child.global_position
						get_tree().current_scene.add_child(laser)
			else:
				for child in firing_positions.get_children():
					var laser := player_laser_up.instance()
					laser.global_position = child.global_position
					get_tree().current_scene.add_child(laser)
					
		if Input.is_action_pressed("fire_auto") and slow_fire_timer.is_stopped():
			pass


## Handles player movement using physics and user input.
## Also handles screen boundaries for the player.
func _physics_process(delta):
	input_vector.x = Input.get_action_strength("ui_right") - Input.get_action_strength("ui_left")
	input_vector.y = (Input.get_action_strength("ui_down") + 0.1) - Input.get_action_strength("ui_up")

	global_position += input_vector * speed * delta
	print(global_position)
	PlayerSingleton.update_player_position(global_position.x, global_position.y)
	PlayerSingleton.update_health(hit_points)
	
	var viewRect := get_viewport_rect()
	global_position.x = clamp(global_position.x, 15, viewRect.size.x - 15)
	global_position.y = clamp(global_position.y, 15, viewRect.size.y - 15)


## Handles any health deductions or events that may occur from the player's ship taking damage.
func take_damage(damage):
	if !invincibility_timer.is_stopped():
		return
	hit_points -= damage
	Signals.emit_signal("on_player_life_change", hit_points)
	if enemies > 10:
		base_music.stream_paused = true
		fast_music.stream_paused = false
	if hit_points <= 0:
		hit_points = 0
		ScoreSystem.sessions_played = true
		on_death()
	invincibility_timer.start(1)
	invincibility_shield.visible = true
	shield_woompf.play()
	
func _on_app_button_press():
	press_counter += 1
	if press_counter % 10 == 0:
		care_package_notifier.play()
		PlayerSingleton.connection_status = "Co-pilot Established!"
		if !get_tree().paused:
			hit_points += 20
			if hit_points >= 100:
				hit_points = 100
				var bomb := player_bomb.instance()
				bomb.position = position
				get_tree().current_scene.add_child(bomb)
		
func on_death():
	$DeathAudio.play()
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
		power_up_notifier.play()
		speed_up_timer.start(5)
		if speed >= 600:
			speed = 800
			power_up_label.set_text("SPEED UP 2X!")
			power_up_label_timer.start(2)
		else:
			speed += 200
			power_up_label.set_text("SPEED UP 1.5X!")
			power_up_label_timer.start(2)
		speed_power_up_seconds = 5
		speed_timer_label.set_text(str(speed_power_up_seconds))
		speed_time.start(1)
	elif area.is_in_group("laserpowerup"):
		power_up_notifier.play()
		laser_up = true
		laser_up_timer.start(10)
		power_up_label.set_text("LASER UP 2X!")
		power_up_label_timer.start(2)
		laser_power_up_seconds = 10
		laser_timer_label.set_text(str(laser_power_up_seconds))
		laser_time.start(1)

## Removes speed bonus once the speed powerup timer runs out.
func _on_SpeedPowerUpTimer_timeout():
	speed = 400
	speed_time.stop()
	
## Removes laser bonus once the laser powerup timer runs out.
func _on_LaserPowerUpTimer_timeout():
	laser_up = false
	laser_time.stop()


func _on_PowerUpLabelTimer_timeout():
	power_up_label.set_text("")
	

func _on_LaserTime_timeout():
	laser_power_up_seconds -= 1
	laser_timer_label.set_text(str(laser_power_up_seconds))
	if laser_power_up_seconds == 0:
		laser_power_up_seconds = 10
		laser_timer_label.set_text("")


func _on_SpeedTime_timeout():
	speed_power_up_seconds -= 1
	speed_timer_label.set_text(str(speed_power_up_seconds))
	if speed_power_up_seconds == 0:
		speed_power_up_seconds = 5
		speed_timer_label.set_text("")


func _on_InvincibilityFrames_timeout():
	invincibility_shield.visible = false
	invincibility_timer.stop()


func _on_SlowMusic_finished():
	fast_music.play()
