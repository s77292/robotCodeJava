package frc.robot.Mechanisms;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.RobotMap;
import frc.robot.Utilities.XBoxCon;

public class Climber {



    private final int topOfElevator = 10000;
    private WPI_TalonSRX Cmotor;
    private WPI_TalonFX Wmotor;

    private XBoxCon xbc;
    private Joystick joystick;

    /**
     * Initializes Climbing mechanism
     */
    public Climber(XBoxCon xbc, Joystick joystick) {

        this.xbc = xbc;
        this.joystick = joystick;

        Cmotor = RobotMap.ClimbMotor;
        Cmotor.setSelectedSensorPosition(0);
        Cmotor.config_kP(0, .5 * 1023 / 4096);
        Cmotor.config_kI(0, .00025);
        Cmotor.config_kD(0, 10);

        Wmotor = RobotMap.WenchMotor;

        resetSensors();
    }

    public void resetSensors() {
        Cmotor.setSelectedSensorPosition(0);
    }


    public void update() {

        /*
        if (joystick.getRawButton(5)) {
            Cmotor.set(.3);
        } else if (joystick.getRawButton(3)) {
            Cmotor.set(-.3);
        } else {
            Cmotor.set(0);
            System.out.println("Climb Delivery Encoder: " + Cmotor.getSelectedSensorPosition());
        }*/


        Cmotor.set(joystick.getRawAxis(1));



        //if (joystick.getRawButton(4) && joystick.getRawButton(6)) {
        if (joystick.getRawButton(6)) {
            //Wmotor.set(.3); // this is the wrong way to move (if this runs it bends metal)
        } else if (joystick.getRawButton(4)) {
            Wmotor.set(-1);
        } else {
            Wmotor.set(0);
        }

    }



    /**
     * Extends elevator to bring out hook
     *  - Hopefully pid loop up till stop
     */
    public void Extend() {
        Cmotor.set(ControlMode.MotionMagic, topOfElevator);
    }


    /**
     * Retracts the pole for the hook
     */
    public void Retract() {
        Cmotor.set(ControlMode.MotionMagic, 0);
    }

    public void Lift() {
        Wmotor.set(0.6);
    }



}