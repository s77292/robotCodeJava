Location of the plans for the general layout of the robot by folder.


api links:

Spark max java api: https://www.revrobotics.com/content/sw/max/sdk/REVRobotics.json
Rev Color sensor api: http://revrobotics.com/content/sw/color-sensor-v3/sdk/REVColorSensorV3.json
NavX api: https://www.kauailabs.com/dist/frc/2020/navx_frc.json
CTRE api: http://devsite.ctr-electronics.com/maven/release/com/ctre/phoenix/Phoenix-latest.json

DO NOT TOUCH MY AUTO

FOLDER: MECHANISMS:

- BallScoringSubsys
    controls the intake and shooter

    Controls:
        POV 0 - exhales balls
        POV 180 - Inhales balls
        POV 225 - intake only
        POV 315 - intake only
        Trigger - Shoots
        Button 2 - Actuates intake


- Intake
    contains code to control intake
    only use if something is horribly wrong
    and speeds need to be changed

- Shooter
    contains the code to control the shooter
    only modify when something is horribly wrong



- Drive Base
    Controls drive train and pneumatics for now



- Climber
    Will control everything to do with climbing/buddy climbing

- WheelOfFortune
    controls everything for the spinner

- HOOD
    PID DOESN'T WORK


FOLDER: UTILITIES:
- Global Vars
    Contains many ratios and constants that are useful

- XBoxCon
    controller with a few extras
    its a child of the XBoxController class



FOLDER: AUTONOMOUS:
- Cries a bit inside

- Auto Drive
    subsystem for drivetrain for ramsete commands

- Autonomous Switcher
    Gets and processes the selection from smart dashboard

- AutoScheduler
    Runs the auto programs, build them here

- Trajectory builder
    Imports and builds trajectories




To Do:
pneumatics, run literally everything.