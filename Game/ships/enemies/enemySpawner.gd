extends Node2D

##@Desc:
##	enemySpawner is intended to spawn iterations of an enemy at the top of the screen.
##	It does this by spawning enemies at one of five given positions.
##	The system will choose each successive position at random once every two seconds.

##	Spawn positions are initially null so that enemies do not spawn before the timer starts.
var spawn_positions = null

##	Creating an instance preload of an enemy
var Enemy = preload("res://ships/enemies/enemy1.tscn")


##	_ready covers all events that happen on start.
##	In this case, _ready calls randomize() and creates an object from all of the spawn nodes.
func _ready():
	randomize()
	spawn_positions = $spawnPositions.get_children()


##	spawn_enemy grabs an index using the remainder of a random integer divided by the size of the spawn node list.
##	It then creates an object from our preloaded Enemy class and assigns it to a spawn position.
##	The add_child function simply spawns the enemy at the given position.
func spawn_enemy():
	var index = randi() % spawn_positions.size()
	var enemy = Enemy.instance()
	enemy.global_position = spawn_positions[index].global_position
	add_child(enemy)


##	This method calls spawn_enemy when the spawnTimer reaches zero.
func _on_spawnTimer_timeout():
	pass
