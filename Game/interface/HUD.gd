extends Control
class_name HUD

##@Desc:
##	The HUD script will track various signal methods.
##	It will utilize any changes made to player health, score, and player speed
##	to update the UI with the appropriate icons or values.
##	Also displays player statistics such as enemies and meteors killed.

## Preloading icons for UI use.
var life_icon := preload("res://interface/LifeIcon.tscn")
var speed_powerup_icon := preload("res://PowerUps/SpeedPowerUpIcon.tscn")
var laser_powerup_icon := preload("res://PowerUps/LaserPowerUpIcon.tscn")

## Creating objects from the children of HUD.
onready var life_container := $LifeContainer
onready var speed_powerup_container := $SpeedPowerUp
onready var laser_powerup_container := $LaserPowerUp
onready var score_label := $Score
onready var score_mult_label := $ScoreMult
onready var high_score_label := $HighScore
onready var enemy_label := $EnemiesKilled
onready var meteor_label := $MeteorsKilled
onready var death_transition_timer := $DeathTransitionTimer

## Signals and function calls loaded at the start of the scene.
func _ready():
	clear_lives()
	Signals.connect("on_player_life_change", self, "_on_player_life_change")
	Signals.connect("on_speed_change", self, "_on_speed_change")
	Signals.connect("on_laser_powerup", self, "_on_laser_powerup")
	
func _process(delta):
	var score = ScoreSystem.score
	var score_mult = ScoreSystem.score_multiplier
	var high_score = ScoreSystem.session_high_score
	var enemy_kills = ScoreSystem.enemies_killed
	var meteor_kills = ScoreSystem.meteors_killed
	score_label.set_text("Score: " + str(score))
	score_mult_label.set_text("Multiplier: " + str(score_mult) + "X")
	high_score_label.set_text("High Score: " + str(high_score))
	enemy_label.set_text("Enemies: " + str(enemy_kills))
	meteor_label.set_text("Meteors: " + str(meteor_kills))

## Clears the initial container so that it can be set using the player life change signal.
func clear_lives():
	for child in life_container.get_children():
		child.queue_free()

## Sets the proper amount of child icons for the life container.
## This is done using the conditions provided in the player life change signal.
func set_lives(lives: int):
	clear_lives()
	for life in range(lives):
		life_container.add_child(life_icon.instance())

## Signal method used to control the appropriate amount of lives to show in the life container.
func _on_player_life_change(life: int):
	if life >= 25:
		set_lives(life / 25)
	elif life < 25 and life > 0:
		set_lives(1)
	else:
		set_lives(0)
		death_transition_timer.start(2)


## Signal method used to monitor the player's current speed. 
## Displays the speed powerup icon when the player's speed exceeds 400.
func _on_speed_change(speed: float):
	if speed > 400:
		speed_powerup_container.visible = true
	else:
		speed_powerup_container.visible = false


func _on_DeathTransitionTimer_timeout():
	get_tree().change_scene("res://GameOver/GameOver.tscn")
