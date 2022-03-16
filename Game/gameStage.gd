extends Node2D

## Preloads of the player and enemy laser objects are declared here.
## These will be used to instantiate these objects in the game scene.
var player_laser = preload("res://projectiles/PlayerLaser.tscn")
var enemy1_laser = preload("res://projectiles/EnemyLaser.tscn")

## Creating an instance of the SynchronizationSingleton class.
## This will be used to update the game sync server with any changes on the game side.
var synchronization = SynchronizationSingleton.new()

var score = 0
var health = 100

## score adds to the player's score whenever an enemy dies and updates the UI accordingly.
## score also sends this information to the game sync server.
func score():
	score += 10
	synchronization.player_score_update(score)
	$Score.text = "SCORE: " + str(score)

## health keeps the player's current health updated.
func health():
	health -= 15
	$Health.text = "HEALTH: " + str(health)

## _on_Player_spawn_player_laser is a signal method.
## The creation of player lasers in the game scene is controlled by this method.
## The parameter accepted is the current location of the player's weapon muzzle.
func _on_Player_spawn_player_laser(location):
	var laser = player_laser.instance()
	laser.global_position = location
	add_child(laser)

## _on_enemy1_spawn_enemy_laser is a signal method.
## The creation of enemy lasers in the game scene is controlled by this method.
## The parameter accepted is the current location of the enemy's weapon muzzle.
func _on_enemy1_spawn_enemy_laser(location):
	var laser = enemy1_laser.instance()
	laser.global_position = location
	add_child(laser)
