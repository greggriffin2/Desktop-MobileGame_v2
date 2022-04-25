extends Area2D
class_name enemyUFO

var on_death_explosion := preload("res://Explosions/OnDeathExplosionEffect.tscn")
var speed_powerup := preload("res://PowerUps/SpeedPowerUp.tscn")
var laser_powerup := preload("res://PowerUps/LaserPowerUp.tscn")

export var speed: int = 200
export var hit_points: int = 5
var move = Vector2.ZERO


func _physics_process(delta):
	move = global_position.direction_to(PlayerSingleton.player_position) * 2
	global_position += move

func take_damage(damage):
	hit_points -= damage
	if hit_points <= 0:
		ScoreSystem.add_score(250)
		ScoreSystem.add_enemy_kill()
		var effect := on_death_explosion.instance()
		effect.global_position = global_position
		get_tree().current_scene.add_child(effect)
		
		var powerup_roll = randf()
		if powerup_roll < 0.05:
			var powerup: SpeedPowerUp = speed_powerup.instance()
			powerup.position = position
			get_tree().current_scene.add_child(powerup)
		elif powerup_roll > 0.05 and powerup_roll < 0.2:
			var powerup: LaserPowerUp = laser_powerup.instance()
			powerup.position = position
			get_tree().current_scene.add_child(powerup)
		queue_free()


func _on_enemyUFO_area_entered(area):
	if area.is_in_group("player"):
		area.take_damage(25)


func _on_VisibilityNotifier2D_screen_exited():
	queue_free()
