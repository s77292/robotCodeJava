package frc.robot.Mechanisms;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.RobotMap;
import frc.robot.Utilities.XBoxCon;

public class Yeeter {


    private boolean hasReset;
    private XBoxCon xbc;
    private int inputVoltage;

    // pid konstants for flywheel shooter;
    private static double kpS;
    private static double kiS;
    private static double kdS;

    // pid konstants for shooter line up (going to use custom pid loop)
    private static double kpT;
    private static double kiT;
    private static double kdT;

    private Counter rotatorEncoder;
    /**
     * Initializes the Yeeter
     */
    public Yeeter(XBoxCon xbc, Joystick joystick) {

        this.xbc = xbc;
        rotatorEncoder = new Counter(5);
        hasReset = false;
    }


    /**
     * Updates the shooter for use in test mode
     */
    public void update() {
        /*
        System.out.println("Encoder Direction: " + rotatorEncoder.getDirection());
        System.out.println("Encoder velocity (ticks per sec): "+ rotatorEncoder.getRate());
        System.out.println("Encoder Count: " + rotatorEncoder.get()); */

        if (xbc.getXButton()){
            yeet();
        }
        if (xbc.getXButtonReleased()) {
            yoted();
        }

        trajectoryChange(xbc.getAimSpeed());
    }


    /**
     * Yeets, what else is needed?
     */
    public void yeet() {
        RobotMap.FlywheelMotor.set(.9);
    }

    /**
     * Attempt to set the shooter at a specific input rpm - converts for you
     * @param velocity
     */
    public void yeetSpecificVelocity(int velocity) {


        // math is rpm * units per rev / minutes per 100ms / wheel radius

        velocity = velocity * 4096 / 600 / 2;

        RobotMap.FlywheelMotor.set(ControlMode.Velocity, velocity);
    }

    /**
     * Gets the velocity of the flywheel shooter
     * @return Velocity_in_RPM
     */
    public int getVelocityRPM() {
        return RobotMap.FlywheelMotor.getSelectedSensorVelocity() / 4096 * 600 * 2;
    }


    /**
     * Stops the flywheel motor
     */
    public void yoted() {
        RobotMap.FlywheelMotor.set(0);
        inputVoltage = 0;
    }

    /**
     * controlls the shooter hood
     * @param speed
     */
    public void trajectoryChange(double speed) {
        RobotMap.trajectoryMotor.set(speed);
    }

    /**
     * controls the shooter hood with positions
     * @param position
     */
    public void trajectorySet(int position) {
        RobotMap.trajectoryMotor.set(ControlMode.Position, position);
    }

    public void resetTrajectory() {

    }






}