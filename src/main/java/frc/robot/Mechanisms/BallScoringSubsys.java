package frc.robot.Mechanisms;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.RobotMap;

public class BallScoringSubsys {

    private Intake intake;
    private Yeeter yeeter;
    private Joystick joystick;
    private Hood hood;

    // This is a placeholder for the light sensor to see balls in the shooter
    private boolean ballStop;

    // TODO Ball mechanisms here

    public BallScoringSubsys(Intake intake, Yeeter yeeter, Joystick joystick, Hood hood) {
        this.intake = intake;
        this.yeeter = yeeter;
        this.joystick = joystick;
        this.hood = hood;
    }


    public void update() {

        ballStop = RobotMap.shooterSwitch.get();


        //if (joystick.getPOV() == 0) {
        if (joystick.getPOV() == 90) {
            // Exhaling balls

            intake.spinOut();
            intake.hopperReversed();
            intake.unfed();

        }
        else if (joystick.getPOV() == 45) {
            intake.hopperReversed();
            intake.unfed();
        }
        else if (joystick.getPOV() == 270) {
        //else if (joystick.getPOV() == 180) {
            // Inhales balls


            intake.spinIn();


            //Stops the indexing when there are balls ready to be shot
            if (RobotMap.shooterSwitch.get()) {
                intake.hopperIndex();
                intake.feed();
            } else {
                intake.hopperStop();
                intake.hopperIndex();
            }
        } //else if (joystick.getPOV() == 225) {
        else if (joystick.getPOV() == 180) {
            intake.spinIn();
        } //else if (joystick.getPOV() == 315) {
        else if (joystick.getPOV() == 0) {
            intake.spinOut();
        } else if (joystick.getTrigger()) {
            //Shooting for motors
            yeeter.yeet();
            if (RobotMap.FlywheelMotor.getSelectedSensorVelocity(1) > 15000) {
                intake.feed();
                intake.hopperIndex();
            } else {
                if (RobotMap.shooterSwitch.get()) {
                    intake.feed();
                    intake.hopperIndex();
                }
                else {
                    intake.stopFeed();
                    intake.hopperStop();
                }
            }
            intake.StopWheels();

        } else {
            //Stops motors
            yeeter.yoted();
            intake.stopFeed();
            intake.StopWheels();
            intake.hopperStop();
        }

        if (joystick.getRawButtonPressed(2)) {
            intake.actuate();
        }

        if (joystick.getRawButton(11)) {
            hood.moveUp(.5);
            //yeeter.trajectoryChange(.5);
        } else if (joystick.getRawButton(12)) {
            hood.moveDown(-.5);
            //yeeter.trajectoryChange(-.5);
        } else {
            //yeeter.trajectoryChange(0.0);
            hood.stop();
        }

        //System.out.println("Hood switch: " + RobotMap.hoodLimitSwitch.get());
        hood.update();


    }





}
