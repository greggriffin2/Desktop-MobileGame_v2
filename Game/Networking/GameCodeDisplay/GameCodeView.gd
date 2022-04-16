extends Control

var gamecode_label: RichTextLabel
var mp_singleton: SynchronizationSingleton


# Called when the node enters the scene tree for the first time.
func _ready():
	mp_singleton = get_node("/root/NetworkSynchronizationSingleton")
	gamecode_label = get_node("GameCodeLabel")
	mp_singleton.connect("on_gamecode_update", self, "update_gamecode")
	update_gamecode(mp_singleton.game_code)


func update_gamecode(code: String):
	gamecode_label.text = code


func _on_Reload_pressed():
	mp_singleton.reload_connection()
