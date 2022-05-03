extends OnDeathExplosionEffect


func _on_Area2D_area_entered(area):
	if area.is_in_group("damageable"):
		area.take_damage(20)
