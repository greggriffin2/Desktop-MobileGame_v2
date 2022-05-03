[![Godot CI](https://github.com/UNCW-CSC-450/team-project-team2/actions/workflows/godot-CI.yml/badge.svg)](https://github.com/UNCW-CSC-450/team-project-team2/actions/workflows/godot-CI.yml) [![Android CI](https://github.com/UNCW-CSC-450/team-project-team2/actions/workflows/android-ci.yml/badge.svg)](https://github.com/UNCW-CSC-450/team-project-team2/actions/workflows/android-ci.yml)

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


#### Steps to run Tests

* Mobile App
    * Open and build `SCCoPilotApp` in Android Studio
    * Ensure that an emulator or physical device is set up with the IDE to run the tests 
    * Navigate to the Terminal at the bottom of the screen
    * run `./gradlew connectedAndroidTest`
    * This will output results to a text file, instructions for accessing it within the IDE will be displayed upon completion
