extends Area2D
class_name Enemy2Laser

var enemy2_laser_effect := preload("res://projectiles/EnemyLaserEffect.tscn")
export var speed := 400
var move = Vector2.ZERO;


func _physics_process(delta):
	move = global_position.direction_to(PlayerSingleton.player_position)
	global_position.x += move.x
	global_position.y += speed * delta

func _on_Enemy2Laser_area_entered(area):
	if area is Player:
		area.take_damage(30)
		var laser_effect := enemy2_laser_effect.instance()
		laser_effect.position = position
		get_parent().add_child(laser_effect)
		queue_free()

func _on_VisibilityNotifier2D_screen_exited():
	queue_free()



