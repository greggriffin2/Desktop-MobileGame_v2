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
    * Build following instructions in the README.md contained in the `Relay` folder.
    * Run the executable with `--help` for command line options
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

###	Amazon DynamoDB Integration Setup Instructions

- Sign up for Amazon AWS. [AWS Signup Page](https://portal.aws.amazon.com/billing/signup)
- Create an administrator IAM User. [IAM User Guide](https://docs.aws.amazon.com/IAM/latest/UserGuide/getting-started_create-admin-group.html)
- Create an access key for the administrator User. [Credentials Guide](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/SettingUp.DynamoWebService.html#SettingUp.DynamoWebService.GetCredentials)
- Download and install AWS CLI. [CLI Guide](https://aws.amazon.com/cli/)
- In a terminal window, run `aws configure`. Enter your AWS Access Key ID, AWS Secret Access Key, default region name, and default output format when prompted.
- Run the following command to create the DynamoDB database table for storing game scores:

```
aws dynamodb create-table \
    --table-name Scores \
    --attribute-definitions \
        AttributeName=username,AttributeType=S \
        AttributeName=datetime,AttributeType=N \
    --key-schema \
        AttributeName=username,KeyType=HASH \
        AttributeName=datetime,KeyType=RANGE \
--provisioned-throughput \
        ReadCapacityUnits=10,WriteCapacityUnits=10
```

- Run `python -m pip install boto3` to install the Amazon AWS boto3 Python module.
- Run `python3 scoreboard_server.py` to start the Scoreboard Server.

### Relay Server Setup instructions
* Run executable
* Pass `--help` for more information