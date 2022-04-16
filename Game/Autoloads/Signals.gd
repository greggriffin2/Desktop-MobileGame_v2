extends Node

## This is a singleton script that houses every signal method used in the game.
## Since it is a singleton, these signals can be accessed from anywhere in the project.

signal on_player_life_change(life)
signal on_speed_change(speed)
signal on_laser_powerup(powerup)
