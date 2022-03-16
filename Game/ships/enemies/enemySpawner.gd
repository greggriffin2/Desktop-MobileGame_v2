extends Node2D

##@Desc:
##	enemySpawner is intended to spawn iterations of an enemy at the top of the screen.
##	It does this by spawning enemies at one of five given positions.
##	The system will choose each successive position at random once every two seconds.

## Creating an instance preload of an enemy.
var enemy1 = preload("res://ships/enemies/enemy1.tscn")

## Spawn positions are initially null so that enemies do not spawn before the timer starts.
var ship_count = 0
var spawn_positions = null

## Signals are added in the script to communicate with other classes.
signal add_score
signal spawn_enemy_laser(location)

## ready covers all events that happen on start.
## In this case, _ready calls randomize() and creates an object from all of the spawn nodes.
func _ready():
	randomize()
	spawn_positions = $spawnPositions.get_children()

## spawn_enemy grabs an index using the remainder of a random integer divided by the size of the spawn node list.
## It then creates an object from our preloaded Enemy class and assigns it to a spawn position.
## The add_child function simply spawns the enemy at the given position.
func spawn_enemy():
	var index = randi() % spawn_positions.size()
	var enemy = enemy1.instance()
	enemy.global_position = spawn_positions[index].global_position
	enemy.connect("enemy_died", self, "enemy_died")
	add_child(enemy)

## _on_spawnTimer_timeout calls spawn_enemy when the spawnTimer reaches zero.
## Once the ship count has been incremented a certain amount, enemy ships
## will begin to vary as well as spawn faster.
func _on_spawnTimer_timeout():
	spawn_enemy()
	ship_count += 1
	if ship_count >= 5:
		pass

## enemy_died is a signal method connected to the score method from the gameStage script.
func enemy_died():
	emit_signal("add_score")


