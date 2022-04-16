extends Node

##@Desc:
## This is an autoloaded script meant to keep track of the player's score and high score.
## Also tracks in-game statistics such as enemies killed, meteors killed, and score multiplier.

## Declaring initial stat variables.
var score: int = 0
var score_multiplier: int = 1
var session_high_score: int = 0
var enemies_killed: int = 0
var meteors_killed: int = 0

## Handles any increments to player score and sets a multiplier. 
## This multiplier is based on the number of enemies the player has killed.
func add_score(amount):
	if enemies_killed < 9:
		score += amount
		score_multiplier = 1
	elif enemies_killed in range(9,25):
		score += (2 * amount)
		score_multiplier = 2
	elif enemies_killed in range(24,40):
		score += (3 * amount)
		score_multiplier = 3
	elif enemies_killed in range(39,65):
		score += (4 * amount)
		score_multiplier = 4
	elif enemies_killed in range(64,100):
		score += (5 * amount)
		score_multiplier = 5
	else:
		score += (10 * amount)
		score_multiplier = 10

## Handles any updating that needs to be done for the session's high score.
func update_high_score():
	if score > session_high_score:
		session_high_score = score
	else:
		session_high_score = session_high_score

## Increments enemy kill count and checks to see if high score needs to be updated.
func add_enemy_kill():
	enemies_killed += 1
	update_high_score()

## Increments meteor kill count and checks to see if high score needs to be updated.
func add_meteor_kill():
	meteors_killed += 1
	update_high_score()
