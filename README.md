#### Steps to compile and run

* Mobile App
  * Windows:
    * Open Powershell in the `SCCoPilotApp` directory
    * run `gradle.bat build`
  * Linux and MacOS
    * Open terminal in the `SCCoPilotApp` directory
    * run `./gradlew build`
  * Generated builds are located in `SCCoPilotApp/build/outputs/apk/`
* Server
  * Scoreboard Server:
    * Run `python scoreboard_server.py` from the `ScoreBoardServer` directory
  * Websocket/Relay Server:
    * Run `python SignalingServer.py`
* Game
  * Open the `project.godot` in Godot
  * Go to Project -> Export -> Select your desired target system
  * Press "Export Project" and then "Save" on the next screen
  * Generated builds will be wherever you saved it.

#### Required Linting Tools

* Godot
  * [Godot GDScript Toolkit](https://github.com/Scony/godot-gdscript-toolkit/issues) provides a set of style guides and checks for Godot projects using GDScript
* Android Studio
  * Use the provided linting and code formatting tools.
  * To autoformat code right click on the project -> Reformat code
* Python Project
  * Use pep8 in PyCharm or similar.
* 
