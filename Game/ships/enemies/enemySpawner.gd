extends Node2D
class_name enemySpawner

##@Desc:
##	enemySpawner is intended to spawn iterations of an enemy at the top of the screen.
##	It does this by spawning enemies at random x-axis positions at next_spawn_time.

## Creating a list of enemy preloads (more enemies to come).
var Enemies := [
	preload("res://ships/enemies/enemy1.tscn"),
	preload("res://ships/enemies/enemyUFO.tscn"),
	]
## Creating preloads of each enemy, along with every meteor.
var preload_enemy1 := preload("res://ships/enemies/enemy1.tscn")
var preload_enemyUFO := preload("res://ships/enemies/enemyUFO.tscn")
var preload_enemy2 := preload("res://ships/enemies/enemy2.tscn")
var preload_meteor := preload("res://Meteor/Meteor.tscn")
var preload_small_meteor := preload("res://Meteor/SmallMeteor.tscn")
var preload_giant_meteor := preload("res://Meteor/GiantMeteor.tscn")

## Declaring a variable from the spawn timer and establishing an initial time set.
onready var spawn_timer := $spawnTimer
onready var spawn_dec_timer := $SpawnDecrementTimer
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
	spawn_dec_timer.start(30)

## The enemy spawn timer starts at 3 second increments.
## When the timer reaches zero, it will decrement by 0.1 seconds and a new damageable will spawn.
## Once the timer reached 0.5 seconds, it will stay there.
## One of two things can spawn from this spawner: a basic enemy, or a meteor.
## The spawner will generate a random spawn point from the top of the screen.
func _on_spawnTimer_timeout():
	var view_rect := get_viewport_rect()
	var x_pos := rand_range(view_rect.position.x + 100, view_rect.end.x - 100)
	var enemy_roll = rand_range(0, 10)
	print(enemy_roll)
	
	if enemy_roll < 0.1:
		var meteor := preload_giant_meteor.instance()
		meteor.position = Vector2(x_pos, position.y)
		get_tree().current_scene.add_child(meteor)
	elif enemy_roll < 0.3:
		var meteor := preload_meteor.instance()
		meteor.position = Vector2(x_pos, position.y)
		get_tree().current_scene.add_child(meteor)
	elif enemy_roll < 0.6:
		var meteor := preload_small_meteor.instance()
		meteor.position = Vector2(x_pos, position.y)
		get_tree().current_scene.add_child(meteor)
	elif enemy_roll < 3:
		var enemy_preload = preload_enemyUFO
		var enemy: enemyUFO = enemy_preload.instance()
		enemy.position = Vector2(x_pos, position.y)
		get_tree().current_scene.add_child(enemy)
	elif enemy_roll < 3.5:
		var enemy_preload = preload_enemy2
		var enemy: enemy2 = enemy_preload.instance()
		enemy.position = Vector2(x_pos, position.y)
		get_tree().current_scene.add_child(enemy)
	else:
		var enemy_preload = preload_enemy1
		var enemy: enemy1 = enemy_preload.instance()
		enemy.position = Vector2(x_pos, position.y)
		get_tree().current_scene.add_child(enemy)
	
	spawn_timer.start(next_spawn_time)

func _on_SpawnDecrementTimer_timeout():
	if next_spawn_time > 0.5:
		next_spawn_time -= 0.5
	print(next_spawn_time)
