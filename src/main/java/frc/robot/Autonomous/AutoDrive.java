package frc.robot.Autonomous;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.Utilities.GlobalVars;

public class AutoDrive extends SubsystemBase {

    AHRS gyro = RobotMap.gyro;

    private DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(Units.inchesToMeters(24));
    private DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(getHeading());

    private WPI_TalonFX LeftMaster;
    private WPI_TalonFX RightMaster;

    private double gearRatio;

    private Pose2d pose;

    SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(.0043, 1.38, .0047);
    private int leftEncoderPos;
    private int rightEncoderPos;


    PIDController leftPIDController = new PIDController(.5, 0, .1);
    PIDController rightPIDController = new PIDController(.5, 0, .1);

    public AutoDrive() {


        LeftMaster = RobotMap.DriveFrontLeft;
        RightMaster = RobotMap.DriveFrontRight;

        gearRatio = GlobalVars.lowGearRatio;
        resetEncoders();
        //RobotMap.DriveBackLeft.follow(LeftMaster);
        //RobotMap.DriveBackRight.follow(RightMaster);
    }


    /**
     * Sets the voltage for the robot
     * @param leftVolts
     * @param rightVolts
     */
    public void setOutput(double leftVolts, double rightVolts) {
        LeftMaster.set(leftVolts/12);
        //RobotMap.DriveBackLeft.set(leftVolts/12);
        RightMaster.set(-rightVolts/12);
        //RobotMap.DriveBackRight.set(-rightVolts/12);
    }


    /**
     * The direction the robot is facing
     * @return Rotation2d
     */
    public Rotation2d getHeading() {
        return Rotation2d.fromDegrees(-gyro.getAngle());
    }

    /**
     * Returns wheel speeds for the drive train
     * @return Speeds for wheels
     */
    public DifferentialDriveWheelSpeeds getSpeeds() {
        // Falcon gives you velocity in units/100ms
        // Conversion Equation: (1/2048)(10)(/gear ratio)(pi)(.1524)

        /*return new DifferentialDriveWheelSpeeds(
            LeftMaster.getSelectedSensorVelocity() / gearRatio * Math.PI * Units.inchesToMeters(6) /100 * 2,
            -RightMaster.getSelectedSensorVelocity() / gearRatio * Math.PI * Units.inchesToMeters(6) /100 * 2
        ); */
        return new DifferentialDriveWheelSpeeds(
            LeftMaster.getSelectedSensorVelocity() / gearRatio / 2048 * 10 * Math.PI * .1524,
            -RightMaster.getSelectedSensorVelocity() / gearRatio / 2048 * 10 * Math.PI * .1524
        );
    }

    @Override
    public void periodic() {
        pose = odometry.update(getHeading(), getSpeeds().leftMetersPerSecond, getSpeeds().rightMetersPerSecond);
        System.out.println(getSpeeds());
        System.out.println(RobotMap.gyro.getAngle());
    }

    /**
     * Getter for the Feed forward
     * @return
     */
    public SimpleMotorFeedforward getFeedForward() {
        return feedforward;
    }
    

    /**
     * PID Controller for the right side of the robot
     * @return
     */
    public PIDController getRightPIDController() {
        return rightPIDController;
    }

    /**
     * PID Controller for the left side of the robot
     * @return
     */
    public PIDController getLeftPIDController() {
        return leftPIDController;
    }

    /**
     * Returns the kinematics of the drive train
     * @return DifferentialDriveKinematics
     */
    public DifferentialDriveKinematics getKinematics() {
        return kinematics;
    }


    /**
     * Returns the pose of the robot
     * @return Pose2d
     */
    public Pose2d getPose() {
        return pose;
    }


    public void resetEncoders() {
        RobotMap.DriveFrontRight.setSelectedSensorPosition(0);
        RobotMap.DriveFrontLeft.setSelectedSensorPosition(0);
        RobotMap.DriveBackRight.setSelectedSensorPosition(0);
        RobotMap.DriveBackLeft.setSelectedSensorPosition(0);
        rightEncoderPos = 0;
        leftEncoderPos = 0;
    }

    public double getRightEncoderDistance() {

        int encoderDistance = RightMaster.getSelectedSensorPosition() - rightEncoderPos;
        rightEncoderPos = RightMaster.getSelectedSensorPosition();
        return encoderDistance / gearRatio / 2048 * 10 * Math.PI * .1524;
    }

    public double getLeftEncoderDistance() {

        int encoderDistance = LeftMaster.getSelectedSensorPosition() - leftEncoderPos;
        leftEncoderPos = LeftMaster.getSelectedSensorPosition();
        return encoderDistance / gearRatio / 2048 * 10 * Math.PI * .1524;
    }


}