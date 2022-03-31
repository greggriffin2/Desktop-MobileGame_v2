extends CPUParticles2D
class_name ODELayer1

## This script's purpose is to emit the given effect once it is instantiated.
## It will then remove the effect from the scene once the emission has concluded.

func _ready():
	emitting = true
	
func _process(delta):
	if !emitting:
		queue_free()
