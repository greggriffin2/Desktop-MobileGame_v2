extends Area2D
class_name enemy2


var speed: int = 70
var hit_points: int = 20
var horizontal_direction: int

## Creating preloads for the enemy laser, an on-death explosion effect, and the two available powerups.
var enemy2_laser := preload("res://projectiles/Enemy2Laser.tscn")
var on_death_explosion := preload("res://Explosions/OnDeathExplosionEffect.tscn")
var on_death_explosion_2 := preload("res://Explosions/ODELayer1.tscn")
var speed_powerup := preload("res://PowerUps/SpeedPowerUp.tscn")
var laser_powerup := preload("res://PowerUps/LaserPowerUp.tscn")

## Creating an object from the FireTimer node.
onready var health_label = $HealthLabel
onready var dual_fire_timer = $DualFireTimer
onready var firing_positions = $FiringPositions
onready var laser_audio := $LaserAudio
var move = Vector2.ZERO


func _ready():
	var random_direction = randf()
	if random_direction < 0.5:
		horizontal_direction = -1
	else:
		horizontal_direction = 1

func _process(delta):
	health_label.set_text(str(hit_points))

## Handles enemy ship movement and potential tactical patterns.
func _physics_process(delta):
	var viewRect := get_viewport_rect()
	move = global_position.direction_to(PlayerSingleton.player_position)
	global_position.x += speed * delta * horizontal_direction
	if global_position.x < viewRect.position.x or global_position.x > viewRect.end.x:
		horizontal_direction *= -1
	global_position.y += move.y / 2
	
	if dual_fire_timer.is_stopped():
		projectile()


## Handles any event that may occur when a ship takes damage.
## The parameter accepted is the damage taken by the enemy ship.
func take_damage(damage):
	hit_points -= damage
	if hit_points <= 0:
		ScoreSystem.add_score(500)
		ScoreSystem.add_enemy_kill()
		var effect := on_death_explosion.instance()
		var effect2 := on_death_explosion_2.instance()
		effect.global_position = global_position
		effect2.global_position = global_position
		get_tree().current_scene.add_child(effect)
		get_tree().current_scene.add_child(effect2)
		
		var powerup_roll = randf()
		if powerup_roll < 0.05:
			var powerup: SpeedPowerUp = speed_powerup.instance()
			powerup.position = position
			get_tree().current_scene.add_child(powerup)
		elif powerup_roll < 0.1:
			var powerup: LaserPowerUp = laser_powerup.instance()
			powerup.position = position
			get_tree().current_scene.add_child(powerup)
		queue_free()


## Spawns enemy lasers from the muzzle of the enemy ship.
func projectile():
	laser_audio.play()
	for muzzle in firing_positions.get_children():
		var laser := enemy2_laser.instance()
		laser.global_position = muzzle.global_position
		get_tree().current_scene.add_child(laser)


## The parameter accepted is the object type that enters the enemy's collision area.
## Currently, the player ship will take 50 damage when an enemy ship makes contact.
func _on_enemy2_area_entered(area):
	if area is Player:
		area.take_damage(50)


## Notifies the game to remove the enemy if it exits the screen.
func _on_VisibilityNotifier2D_screen_exited():
	queue_free()


func _on_DualFireTimer_timeout():
	projectile()
