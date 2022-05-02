extends Node2D
class_name GameOver

# Declaring a variable for the end-screen score label
onready var score_label := $Score
onready var high_score_label := $HighScore
onready var score: int = ScoreSystem.score
onready var high_score: int = ScoreSystem.session_high_score

var empty_filter = ["", " ", "  ", "   "]

var profanity_filter = ["ass", "asS", "aSs", "Ass", "aSS", "AsS", "ASs", "ASS",
						"cum", "cuM", "cUm", "Cum", "cUM", "CuM", "CUm", "CUM",
						"dik", "diK", "dIk", "Dik", "dIK", "DiK", "DIk", "DIK",
						"nig", "niG", "nIg", "Nig", "nIG", "NiG", "NIg", "NIG",
						"fag", "faG", "fAg", "Fag", "fAG", "FaG", "FAg", "FAG",
						"gay", "gaY", "gAy", "Gay", "gAY", "GaY", "GAy", "GAY",
						"fuk", "fuK", "fUk", "Fuk", "fUK", "FuK", "FUk", "FUK"]

func _ready():
	$SubmissionConfirmation.visible = false
	$ProfanityError.visible = false
	$EmptyError.visible = false
	score_display()

# Displays the player's most recent score and overall high score.
func score_display():
	score_label.set_text("SCORE: " + str(score))
	high_score_label.set_text("HIGH SCORE: " + str(high_score))

# Restarts the game stage scene.
func _on_RetryButton_pressed():
	get_tree().change_scene("res://GameStage/gameStage.tscn")

# Returns the user to the main menu.
func _on_MenuButton_pressed():
	get_tree().change_scene("res://MainMenu/MainMenu.tscn")

# Exits the game.
func _on_ExitButton_pressed():
	get_tree().quit()

func _on_LineEdit_text_entered(new_text):
	var username: String = new_text
	if username in empty_filter:
		print("Username must not be empty if you wish to submit your score.")
		$EmptyError.visible = true
		$EmptyTimer.start(3)
	elif username in profanity_filter:
		print("Username is inappropriate - please enter a new username.")
		$ProfanityError.visible = true
		$ProfanityTimer.start(4)
	else:
		var error = $HTTPRequest.request("https://coolspacegame.ddns.net/add/" + username + "/" + str(ScoreSystem.score) + "/")
		if error != OK:
			print("request() call Error")

func _on_HTTPRequest_request_completed(result, response_code, headers, body):
	print(str(result))
	print(str(response_code))
	print(str(headers))
	print(str(body))
	print(str(body.get_string_from_utf8()))
	$SubmissionConfirmation.visible = true
	$ProfanityError.visible = false
	$EmptyError.visible = false
	$UsernameLabel.visible = false
	$LineEdit.visible = false

func _on_ProfanityTimer_timeout():
	$ProfanityError.visible = false

func _on_EmptyTimer_timeout():
	$EmptyError.visible = false


