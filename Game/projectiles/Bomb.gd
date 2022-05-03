extends Area2D

var bomb_effect := preload("res://projectiles/BombEffect.tscn")
export var speed := 800


## _physics_process handles laser movement.
## The player's laser will move from the player ship's muzzle to the top of the screen.
func _physics_process(delta):
	global_position.y -= speed * delta


func _on_Bomb_area_entered(area):
	if area.is_in_group("damageable"):
		var boom := bomb_effect.instance()
		boom.position = position
		get_parent().add_child(boom)
		area.take_damage(10)
		queue_free()

## Notifies the game to remove the player laser if it exits the screen.
func _on_VisibilityNotifier2D_screen_exited():
	queue_free()
