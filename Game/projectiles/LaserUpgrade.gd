extends PlayerLaser

var laser_upgrade_effect := preload("res://projectiles/LaserUpgradeEffect.tscn")

func _on_LaserUpgrade_area_entered(area):
	if area.is_in_group("damageable"):
		var laser_effect := laser_upgrade_effect.instance()
		laser_effect.position = position
		get_parent().add_child(laser_effect)
		area.take_damage(2)
		queue_free()
