extends Node2D
class_name enemySpawner

##@Desc:
##	enemySpawner is intended to spawn iterations of an enemy at the top of the screen.
##	It does this by spawning enemies at random x-axis positions at next_spawn_time.

## Creating a list of enemy preloads (more enemies to come).
var Enemies := [
	preload("res://ships/enemies/enemy1.tscn")
	]
## Creating a preload for the meteor.
var preload_meteor := preload("res://Meteor/Meteor.tscn")

## Creating an object from the spawn timer and establishing an initial time set.
onready var spawn_timer := $spawnTimer
var next_spawn_time := 3.0

## Spawn positions are initially null so that enemies do not spawn before the timer starts.
## This will be used later to spawn boss ships at specified points.
var spawn_positions = null


## ready covers all events that happen on start.
## In this case, _ready calls randomize() and creates an object from all of the spawn nodes.
## These will be used later to spawn boss ships at specified points.
func _ready():
	randomize()
	spawn_positions = $spawnPositions.get_children()

## The enemy spawn timer starts at 3 second increments.
## When the timer reaches zero, it will decrement by 0.1 seconds and a new damageable will spawn.
## Once the timer reached 0.5 seconds, it will stay there.
## One of two things can spawn from this spawner: a basic enemy, or a meteor.
## The spawner will generate a random spawn point from the top of the screen.
func _on_spawnTimer_timeout():
	var view_rect := get_viewport_rect()
	var x_pos := rand_range(view_rect.position.x + 50, view_rect.end.x - 50)
	
	if randf() < 0.1:
		var meteor := preload_meteor.instance()
		meteor.position = Vector2(x_pos, position.y)
		get_tree().current_scene.add_child(meteor)
	else:
		var enemy_preload = Enemies[0]
		var enemy: enemy1 = enemy_preload.instance()
		enemy.position = Vector2(x_pos, position.y)
		get_tree().current_scene.add_child(enemy)
	
	if next_spawn_time > 0.5:
		next_spawn_time -= 0.1
	else:
		next_spawn_time = 0.5
	
	spawn_timer.start(next_spawn_time)
