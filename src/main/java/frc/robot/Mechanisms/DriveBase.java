package frc.robot.Mechanisms;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Utilities.PiDataProcessor;
import frc.robot.Utilities.XBoxCon;
import frc.robot.RobotMap;

public class DriveBase {

    private DifferentialDrive driveTrain;
    private double turnMultiplier;
    private boolean HumanOperator;
    private XBoxCon xbController;
    private boolean alreadyActuated;

    private PiDataProcessor piData;


    // Shoot direction - 1 for backwards, -1 for forwards
    private int ShootDirection = -1;


    /**
     * Initializes the drive base stuff
     * @param xbController
     */
    public DriveBase(XBoxCon xbController, PiDataProcessor piData) {
        this.xbController = xbController;
        turnMultiplier = 1.0;
        HumanOperator = true;
        this.piData = piData;
        RobotMap.limelight.set(false);
        initMotors();
        initSolenoids();
    }

    public void reInit() {
        initMotors();
        initSolenoids();
    }

    /**
     * Drive for human operator and auto target tracking
     */
    public void Drive() {

        //Starts the auto line up code
        if (xbController.getBButton()) {
            HumanOperator = false;
            RobotMap.limelight.set(true);
        } else {
            HumanOperator = true;
            RobotMap.limelight.set(false);
        }

        //Human operator stuff, duh
        if (HumanOperator) {
            driveTrain.arcadeDrive(xbController.getDriveSpeed(), xbController.getTurnSpeed() * turnMultiplier);
            //System.out.println("updated drive base");


            
            if (xbController.getBumper(Hand.kLeft) && xbController.getBumper(Hand.kRight) && !alreadyActuated) {
                actuateSolenoid();
                alreadyActuated = true;
            } else if (alreadyActuated && (!xbController.getBumper(Hand.kLeft) || !xbController.getBumper(Hand.kRight))) {
                alreadyActuated = false;
            }
            


            if (xbController.getAButtonPressed()) {
                if (RobotMap.compressor.enabled()) {
                    RobotMap.compressor.stop();
                } else {
                    RobotMap.compressor.start();
                    RobotMap.compressor.setClosedLoopControl(true);
                }
            }



        
        }
        else {

            /**
             * Code for the auto aiming capabilities with our budget limelight
             */
            if (xbController.getBButton()) {
                System.out.println("Auto Aim");
                double yaw = piData.getYaw();
                System.out.println("yaw: " + yaw);
                
                if (yaw > 10) {
                    driveTrain.arcadeDrive(0, .4 * ShootDirection);
                    System.out.println("Turning: " + .4 * ShootDirection);
                } else if (yaw > 4) {
                    driveTrain.arcadeDrive(0, .3 * ShootDirection);
                    System.out.println("Turning: " + .3 * ShootDirection);
                } else if (yaw < -10) {
                    driveTrain.arcadeDrive(0, -.4 * ShootDirection);
                    System.out.println("Turning: " + -.4 * ShootDirection);
                } else if (yaw < -4) {
                    driveTrain.arcadeDrive(0, -.3);
                    System.out.println("Turning: " + -.3 * ShootDirection);
                } else {
                    driveTrain.arcadeDrive(0,0);
                    System.out.println("Stopped");
                    SmartDashboard.putBoolean("Lined Up", true);
                }
                
                
            }
            else {
               HumanOperator = true;
            }
        }

    }

    /**
     * Allows the driving for autonomous programs
     * @param driveSpeed Forward and backward speed
     * @param turnSpeed Turning power
     */
    public void DriveAuto(double driveSpeed, double turnSpeed) {
        driveTrain.arcadeDrive(driveSpeed, turnSpeed);
    }

    /**
     * Sets up the drive train
     */
    private void initMotors() {
        driveTrain = new DifferentialDrive(RobotMap.DriveFrontLeft, RobotMap.DriveFrontRight);
        RobotMap.DriveBackLeft.follow(RobotMap.DriveFrontLeft);
        RobotMap.DriveBackRight.follow(RobotMap.DriveFrontRight);
        driveTrain.setSafetyEnabled(false);
    }


    /**
     * Initializes Solenoids in proper gear for drive train
     */
    private void initSolenoids() {
        
        RobotMap.gearSwitcher.set(Value.kReverse);

        RobotMap.compressor.setClosedLoopControl(true);
        
    }


    /**
     * Switches the drive train gearing
     */
    public void actuateSolenoid() {
        // Opens and closes the gear switcher solenoid
        System.out.println("Actuate Solenoid Method Called");
    
        if (RobotMap.gearSwitcher.get() == Value.kForward) {
          RobotMap.gearSwitcher.set(Value.kReverse);
        } else {
          RobotMap.gearSwitcher.set(Value.kForward);
        }
      }




}