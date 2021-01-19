package frc.robot.Mechanisms;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Hood {

    private WPI_TalonSRX motor;
    private Counter counter;
    private boolean hasBeenReset;
    private DigitalInput hoodLimit;


    //PID Constants for the pid loop
    private final double kP = .05;
    private final double kI = 0;
    private final double kD = 0;


    /**
     * Specifically for the hood movement, does not work yet
     */
    public Hood() {
        counter = new Counter(0);
        hasBeenReset = false;
        motor = RobotMap.trajectoryMotor;
        hoodLimit = RobotMap.hoodLimitSwitch;
    }


    public void moveUp(double velocity) {
        if (hoodLimit.get()) {
            counter.setReverseDirection(true);
            motor.set(velocity);
        } else {
            stop();
        }

    }

    public void moveDown(double velocity) {
        counter.setReverseDirection(false);
        motor.set(velocity);
    }

    public void stop() {
        motor.set(0);
    }

    public void update() {

        if (!hasBeenReset) {
            moveUp(.5);
            if (hoodLimit.get()) {
                stop();
                hasBeenReset = true;
            }
        }



        System.out.println("Hood Position: " + counter.get());

    }


    public void StupidPid(int position) {

        double error = position - counter.get();

        double outputSpeed = kP * error;


        if (outputSpeed < 0) {
            moveUp(outputSpeed);
        }
        if (outputSpeed > 0) {
            moveDown(outputSpeed);
        }

    }




}
