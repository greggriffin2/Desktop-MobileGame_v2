extends PlayerLaser


func _on_LaserUpgrade_area_entered(area):
	if area.is_in_group("damageable"):
		area.take_damage(2)
		queue_free()
