extends ParallaxBackground

##@Desc:
##	This script's sole purpose is auto-scrolling the background and everything attached to it.

## Offsets the position of the scaling background on the y-axis.
## The screen moves at 100 units per second as a result.
## The parameter accepted, delta, represents real time.
func _process(delta):
	scroll_base_offset += Vector2(0, 150) * delta
