package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import com.kauailabs.navx.frc.AHRS;
//import com.revrobotics.CANSparkMax;
//import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.I2C.Port;

public class RobotMap {

    // Drive train
    public static WPI_TalonFX DriveFrontRight = new WPI_TalonFX(8);
    public static WPI_TalonFX DriveFrontLeft = new WPI_TalonFX(2);
    public static WPI_TalonFX DriveBackRight = new WPI_TalonFX(6);
    public static WPI_TalonFX DriveBackLeft = new WPI_TalonFX(4);


    // Intake Mechanisms
    public static WPI_TalonSRX IntakeWheels = new WPI_TalonSRX(10);
    public static WPI_TalonSRX HopperWheels = new WPI_TalonSRX(3);


    //Climbing mechanisms
    public static WPI_TalonSRX ClimbMotor = new WPI_TalonSRX(7);
    public static WPI_TalonFX WenchMotor = new WPI_TalonFX(11);

    // Shooter Motors
    public static WPI_TalonFX FlywheelMotor = new WPI_TalonFX(5);
    public static WPI_TalonSRX trajectoryMotor = new WPI_TalonSRX(1);
    public static WPI_TalonSRX feedMotor = new WPI_TalonSRX(15);

    // Wheel of fortune motor
    public static WPI_TalonSRX WoFMotor = new WPI_TalonSRX(20);


    //Sensors
    public static ColorSensorV3 colorSensor = new ColorSensorV3(Port.kOnboard);
    public static  AHRS gyro = new AHRS(SPI.Port.kMXP);
    public static DigitalInput shooterSwitch = new DigitalInput(1);
    public static DigitalInput hoodLimitSwitch = new DigitalInput(2);


    //Pneumatics - hopefully won't be here forever

    public static Compressor compressor = new Compressor();
    //public static Solenoid gearSwitcher = new Solenoid(0);

    //public static DoubleSolenoid intakeSolenoid = new DoubleSolenoid(0, 1);
    //public static DoubleSolenoid gearSwitcher = new DoubleSolenoid(2, 3);
    public static DoubleSolenoid intakeSolenoid = new DoubleSolenoid(2,3);
    public static DoubleSolenoid gearSwitcher = new DoubleSolenoid(0,1);

    // Not a solenoid ;D
    public static Solenoid limelight = new Solenoid(4);


}