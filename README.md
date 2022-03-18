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
