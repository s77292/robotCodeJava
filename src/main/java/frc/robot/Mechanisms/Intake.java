package frc.robot.Mechanisms;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.Utilities.XBoxCon;

public class Intake {

    private XBoxCon xbc;
    private Joystick joystick;
    private boolean armOut;

    private boolean XBControl = false;

    /**
     * Initializes the Intake, also contains the hopper mechanism and shooter feeder
     * @param xBc
     */
    public Intake(XBoxCon xBc, Joystick joystick) {

        SmartDashboard.putBoolean("ArmDown", false);
        armOut = false;
        resetSensors();
        this.xbc = xBc;
        this.joystick = joystick;
    }


    /**
     * Updates the intake -- use to control with xbox controller
     */
    public void update() {


        if (XBControl) {
            if (xbc.getAButtonPressed()) {

                if (armOut) {
                    armUp();
                    SmartDashboard.putBoolean("ArmDown", false);
                }
                else {
                    armDown();
                    SmartDashboard.putBoolean("ArmDown", true);
                }

            }

            if (xbc.getBButton()) {
                spinOut();
            } else if (xbc.getYButton()) {
                spinIn();
            }
            if (xbc.getBButtonReleased() || xbc.getBButtonReleased()) {
                StopWheels();
            }

            if (xbc.getBumper(Hand.kLeft)) {
                hopperIndex();
            }
            if (xbc.getBumperReleased(Hand.kLeft)) {
                hopperStop();
            }
        }
    }



    /**
     * Resets sensors if we have any
     */
    public void resetSensors() {

        armOut = false;
        RobotMap.intakeSolenoid.set(Value.kForward);

    }


    /**
    * Puts the intake down
    *   
    */
    private void armDown() {

        RobotMap.intakeSolenoid.set(Value.kForward);
        System.out.println("Arm down");
        armOut = true;
    }
    
    /**
     * Pulls the intake up
     * 
     */
    private void armUp() {
        RobotMap.intakeSolenoid.set(Value.kReverse);
        System.out.println("Arm Up");
        armOut = false;
    }

    /**
     * Logic for actuating the intake from the outside
     */
    public void actuate() {
        if (armOut) {
            armUp();
        } else {
            armDown();
        }
    }



    /**
     * Pushes out the balls
     */
    public void spinOut() {
        RobotMap.IntakeWheels.set(-.8);
    }

    /**
     * Sucks in the balls
     */
    public void spinIn() {
        //Sucks in motor
        RobotMap.IntakeWheels.set(.8);
    }

    /**
     * Stops the intake wheels
     */
    public void StopWheels() {
        RobotMap.IntakeWheels.set(0);
    }

    public void hopperIndex() {
        RobotMap.HopperWheels.set(.5);
        System.out.println("Index spinning");
    }

    public void hopperReversed() {
        RobotMap.HopperWheels.set(-.5);
        System.out.println("Index Spinning");
    }

    public void hopperStop() {
        RobotMap.HopperWheels.set(0);
    }


    public void feed() {
        RobotMap.feedMotor.set(-1);
    }
    public void unfed() {
        RobotMap.feedMotor.set(1);
    }
    public void stopFeed() {
        RobotMap.feedMotor.set(0);
    }



}   