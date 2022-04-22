extends Area2D
class_name enemy1

##@Desc:
##	This is a sample of the general Enemy class.
##	There will be variations on each of these properties in each Enemy class.
##	Possible candidate for a superclass/subclass situation.
##	Each enemy has a movement speed, a point value (score gained by player after defeat), and hit points.
##	Each enemy will also have a unique projectile and tactical pattern.

var speed = 100
var hit_points = 2

## Creating preloads for the enemy laser, an on-death explosion effect, and the two available powerups.
var enemy1_laser := preload("res://projectiles/EnemyLaser.tscn")
var on_death_explosion := preload("res://Explosions/OnDeathExplosionEffect.tscn")
var speed_powerup := preload("res://PowerUps/SpeedPowerUp.tscn")
var laser_powerup := preload("res://PowerUps/LaserPowerUp.tscn")

## Creating an object from the FireTimer node.
onready var fire_timer = $FireTimer


## Handles enemy ship movement and potential tactical patterns.
func _physics_process(delta):
	global_position.y += speed * delta
	if fire_timer.is_stopped():
		projectile()


## Handles any event that may occur when a ship takes damage.
## The parameter accepted is the damage taken by the enemy ship.
func take_damage(damage):
	hit_points -= damage
	if hit_points <= 0:
		ScoreSystem.add_score(150)
		ScoreSystem.add_enemy_kill()
		var effect := on_death_explosion.instance()
		effect.global_position = global_position
		get_tree().current_scene.add_child(effect)
		
		var powerup_roll = randi()
		if powerup_roll % 20 == 0:
			var powerup: SpeedPowerUp = speed_powerup.instance()
			powerup.position = position
			get_tree().current_scene.add_child(powerup)
		elif powerup_roll % 30 == 1:
			var powerup: LaserPowerUp = laser_powerup.instance()
			powerup.position = position
			get_tree().current_scene.add_child(powerup)
		queue_free()


## Spawns enemy lasers from the muzzle of the enemy ship.
func projectile():
	var laser := enemy1_laser.instance()
	laser.position = position
	get_tree().current_scene.add_child(laser)


## The parameter accepted is the object type that enters the enemy's collision area.
## Currently, the player ship will take 50 damage when an enemy ship makes contact.
func _on_enemy1_area_entered(area):
	if area is Player:
		area.take_damage(50)


## This function calls projectile to fire lasers every 1 second from enemy ships.
func _on_FireTimer_timeout():
	projectile()

## Notifies the game to remove the enemy if it exits the screen.
func _on_VisibilityNotifier2D_screen_exited():
	queue_free()
