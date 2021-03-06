extends Control

var gamecode_label: RichTextLabel
var mp_singleton: SynchronizationSingleton


# Called when the node enters the scene tree for the first time.
func _ready():
	mp_singleton = get_node("/root/NetworkSynchronizationSingleton")
	gamecode_label = get_node("GameCodeLabel")
	mp_singleton.connect("on_gamecode_update", self, "update_gamecode")
	mp_singleton.connect("user_joined", self, "_user_joined")
	mp_singleton.reload_connection()
	update_gamecode(mp_singleton.game_code)
	$ConnectedLabel.set_text("")
	
func _process(delta):
	$ConnectedLabel.set_text(PlayerSingleton.connection_status)
	if PlayerSingleton.connection_status == "Co-pilot Established!":
		$ConnectedButton.visible = true
		$ContinueButton.visible = false


func update_gamecode(code: String):
	gamecode_label.text = code
	PlayerSingleton.game_code = code


func _on_Reload_pressed():
	mp_singleton.reload_connection()


func _on_ContinueButton_pressed():
	get_tree().change_scene("res://MainMenu/MainMenu.tscn")


func _on_ConnectedButton_pressed():
	get_tree().change_scene("res://MainMenu/MainMenu.tscn")


func _on_ConnectedButton_visibility_changed():
	$VineBoom.play()
