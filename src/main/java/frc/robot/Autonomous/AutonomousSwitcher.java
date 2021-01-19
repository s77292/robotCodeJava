package frc.robot.Autonomous;

import java.util.Arrays;
import java.util.List;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

public class AutonomousSwitcher {

    private SendableChooser<String> startLocations;
    private SendableChooser<String> secondLocation;
    private SendableChooser<String> shootingLocation;
    private SendableChooser<String> wheelOfFortune;
    private SendableChooser<String> BasicAuto;
    private SendableChooser<String> Test;
    private AutoDrive aDrive;
    public boolean AutoCodeSet;
    private RamseteCommand ramseteCommand;

    /**
     * Initializes AutonomousSwitcher
     */
    public AutonomousSwitcher(AutoDrive aDrive) {

        AutoCodeSet = false;

        startLocations = new SendableChooser<String>();
        startLocations.addOption("Start Position", "Trench");
        startLocations.addOption("Start Position", "Mid");
        startLocations.addOption("Start Position", "Far");
        startLocations.setDefaultOption("Start Position", "none");

        secondLocation = new SendableChooser<String>();
        secondLocation.addOption("Second Location", "Trench Balls");
        secondLocation.addOption("Second Location", "Mid Balls");
        secondLocation.addOption("Second Location", "Shoot");
        secondLocation.addOption("Second Location", "Off Line");
        secondLocation.setDefaultOption("Second Location", "none");

        shootingLocation = new SendableChooser<String>();
        shootingLocation.addOption("Shoot Location", "Trench");
        shootingLocation.addOption("Shoot Location", "Mid");
        shootingLocation.addOption("Shoot Location", "Far");
        shootingLocation.setDefaultOption("Shoot Location", "none");

        wheelOfFortune = new SendableChooser<String>();
        wheelOfFortune.addOption("Wheel Prep", "Yes");
        wheelOfFortune.setDefaultOption("Wheel Prep", "No");


        BasicAuto = new SendableChooser<String>();
        BasicAuto.setDefaultOption("none", "none");
        BasicAuto.addOption("Basic", "5-Ball-Trench");


        Test = new SendableChooser<String>();
        Test.setDefaultOption("none", "none");
        Test.addOption("Forward-Back-W-Curve", "Forward-Back-W-Curve");
        Test.addOption("Test-Auto", "S-Curve");
        Test.addOption("Test-Auto", "Forward-Back");
        Test.addOption("Test-Auto", "Figure-8");
        Test.addOption("Test-Auto", "S-Curve-Reverse");





        this.aDrive = aDrive;
    }


    public String getTestAuto() {
        return Test.getSelected();
    }

    public String getBasicAuto() {
        return Test.getSelected();
    }


    /**
     * DONT USE THIS ONE, WILL BE REMOVED IN THE FUTURE
     * @return Ramsete Command
     */
    public RamseteCommand setAutoCode() {
        

        aDrive.resetEncoders();


        TrajectoryConfig config = new TrajectoryConfig(1, 1);

        config.setKinematics(aDrive.getKinematics());
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            new Pose2d(0, 0, new Rotation2d(0)), //Start
            List.of(new Translation2d(1.5, 1), new Translation2d(2.5, 0)), // Middle points
            new Pose2d(4, 0, new Rotation2d(0)), // End point
            config); //config

        ramseteCommand = new RamseteCommand(
            trajectory, 
            aDrive::getPose, 
            new RamseteController(2, .7), 
            aDrive.getFeedForward(), 
            aDrive.getKinematics(), 
            aDrive::getSpeeds, 
            aDrive.getLeftPIDController(), 
            aDrive.getRightPIDController(), 
            aDrive::setOutput, 
            aDrive);


        
        AutoCodeSet = true;

        return ramseteCommand;
    }
}