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
	
func _user_joined():
	if PlayerSingleton.connection_status == "User Connected!":
		get_tree().change_scene("res://MainMenu/MainMenu.tscn")
		print("Connection made")


func update_gamecode(code: String):
	gamecode_label.text = code
	PlayerSingleton.game_code = code


func _on_Reload_pressed():
	mp_singleton.reload_connection()


func _on_ContinueButton_pressed():
	get_tree().change_scene("res://MainMenu/MainMenu.tscn")
