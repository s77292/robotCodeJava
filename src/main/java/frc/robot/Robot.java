/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Autonomous.*;
import frc.robot.Mechanisms.*;
import frc.robot.Utilities.*;

/**
 * The VM is configured to automatically run this class. If you change the name
 * of this class or the package after creating this project, you must also
 * update the build.gradle file in the project.
 */
public class Robot extends RobotBase {
  
  public GlobalVars gVars;

  // Mechanisms - view Plans to see functions
  private DriveBase driveTrain;

  private WheelOfFortune WoF;

  private Yeeter yeeter;

  private Intake intake;

  private Climber climber;

  private Hood hood;

  private BallScoringSubsys BallSubsystem;

  // Controllers - view Plans to see functions
  private XBoxCon xbC;

  private Joystick joystick;

  private MusicBox musicBox;

  // Autonomous Things
  private AutonomousSwitcher autoSwitcher;
  private AutoDrive aDrive;
  private TrajectoryBuilder trajectoryBuilder;
  private AutoScheduler autoScheduler;

  private PiDataProcessor piData;

  //private TFLidar lidar = new TFLidar(SerialPort.Port.kUSB);


  //Boolean values
  private boolean recievedColor;


  public void robotInit() {

    xbC = new XBoxCon(1);

    piData = new PiDataProcessor(xbC);

    driveTrain = new DriveBase(xbC, piData);

    WoF = new WheelOfFortune();

    joystick = new Joystick(0);

    yeeter = new Yeeter(xbC, joystick);

    intake = new Intake(xbC, joystick);

    climber = new Climber(xbC, joystick);

    hood = new Hood();

    BallSubsystem = new BallScoringSubsys(intake, yeeter, joystick, hood);

    recievedColor = false;

    SmartDashboard.putBoolean("Do you have the color?", false);

    musicBox = new MusicBox(xbC);


    // Autonomous stuff
    aDrive = new AutoDrive();

    autoSwitcher = new AutonomousSwitcher(aDrive);

    trajectoryBuilder = new TrajectoryBuilder(aDrive);

    autoScheduler = new AutoScheduler(autoSwitcher, trajectoryBuilder, aDrive);




    
  }


  /**
   * Called when the robot is disabled
   */
  public void disabled() {

    CommandScheduler.getInstance().cancelAll();
    
    recievedColor = false;
    autoSwitcher.AutoCodeSet = false;
  }


  /**
   * Called during the autonomous period
   */
  public void autonomous() {

    autoScheduler.selectAuto();
    autoScheduler.runAuto();
  }

  public void autonomous2() {


  }


  public void teleopInit() {
    driveTrain.reInit();
    intake.resetSensors();
    climber.resetSensors();
  }

  /**
   * TELEOP CODE
   */
  public void teleop() {
    CommandScheduler.getInstance().disable();
    // Drive train
    driveTrain.Drive();


    // Runs the Wheel of fortune stuff
    
    if (!recievedColor) {
      WoF.spin3Times();
      recievedColor = WoF.checkData();
    }
    else {
      WoF.setToColor();
    }
    SmartDashboard.putBoolean("Do you have the color?", recievedColor);


    //Balls
    BallSubsystem.update();

    //Climber stuff
    climber.update();


    //System.out.println(RobotMap.gyro.getAngle());


  }


  /**
   * Test Mode code
   */
  public void test() {
    //System.out.println("testTick");
    //WoF.spin3Times();
    //System.out.println(WoF.checkDistance());

    //SmartDashboard.putNumber("encoder Tick", RobotMap.DriveFrontLeft.getSelectedSensorPosition());
    //System.out.println(RobotMap.DriveFrontLeft.getSelectedSensorPosition());

    musicBox.update();

    if (shootingTest) {
      if (xbC.getAButton()) {
        yeeter.yeet();
      } else {
        yeeter.yoted();
      }
      yeeter.trajectoryChange(xbC.getDriveSpeed());
      yeeter.update();
    }

    //System.out.println("Ball State: " + RobotMap.shooterSwitch.get());

    //System.out.println("Console tick");
    //System.out.println("POV: " + joystick.getPOV());
    //System.out.println(lidar.get());
  }

  /**
   * Test mode booleans
   * These Enable different modes in test mode to run different things as needed
   */
//Enables testing for shooter
private boolean shootingTest = false;


  /**
   * End of test mode booleans
   */



  private volatile boolean m_exit;

  @SuppressWarnings("PMD.CyclomaticComplexity")
  @Override
  public void startCompetition() {
    robotInit();

    // Tell the DS that the robot is ready to be enabled
    HAL.observeUserProgramStarting();

    while (!Thread.currentThread().isInterrupted() && !m_exit) {
      if (isDisabled()) {
        m_ds.InDisabled(true);
        disabled();
        m_ds.InDisabled(false);
        while (isDisabled()) {
          disabled();
          m_ds.waitForData();
        }
      } else if (isAutonomous()) {
        m_ds.InAutonomous(true);
        autonomous();
        m_ds.InAutonomous(false);
        while (isAutonomous() && !isDisabled()) {
          autonomous();
          m_ds.waitForData();
        }
      } else if (isTest()) {
        LiveWindow.setEnabled(true);
        Shuffleboard.enableActuatorWidgets();
        m_ds.InTest(true);
        test();
        m_ds.InTest(false);
        while (isTest() && isEnabled()) {
          test();
          m_ds.waitForData();
        }
        LiveWindow.setEnabled(false);
        Shuffleboard.disableActuatorWidgets();
      } else {
        m_ds.InOperatorControl(true);
        teleop();
        m_ds.InOperatorControl(false);
        while (isOperatorControl() && !isDisabled()) {
          m_ds.waitForData();
          teleop();
          //System.out.println("Operator Control --> Thread Tick");
        }
      }
      //System.out.println("ThreadTick");
    }
  }

  @Override
  public void endCompetition() {
    m_exit = true;
  }
}