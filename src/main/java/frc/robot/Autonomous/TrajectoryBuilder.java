package frc.robot.Autonomous;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

public class TrajectoryBuilder {

    private Trajectory firstTrajectory;
    private AutoDrive aDrive;

    // Array Lists for the trajectories, more for organization than anything
    private ArrayList<Trajectory> TrenchFiveBall;

    private ArrayList<Trajectory> TestForward_Back_W_Curve;


    // Array Lists for routines
    private ArrayList<RamseteCommand> TrenchFiveBallCommands;
    private ArrayList<RamseteCommand> TestForward_Back_W_Curve_Commands;

    public TrajectoryBuilder(AutoDrive autoDrive) {
        TrenchFiveBall = new ArrayList<Trajectory>();
        TrenchFiveBallCommands = new ArrayList<RamseteCommand>();

        TestForward_Back_W_Curve = new ArrayList<Trajectory>();
        TestForward_Back_W_Curve_Commands = new ArrayList<RamseteCommand>();


        aDrive = autoDrive;
        importJsons();
    }


    /**
     * Imports the json files from the filesystem, you must add more when you make them.
     */
    private void importJsons() {

        // Trajectory for starting near the trench for 5 ball auto
        String trajectoryJSON = "paths/FBTrenchAuto/Approach.wpilib.json";
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
            TrenchFiveBall.add(TrajectoryUtil.fromPathweaverJson(trajectoryPath));
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
        }
        trajectoryJSON = "paths/FBTrenchAuto/Reverse.wpilib.json";
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
            TrenchFiveBall.add(TrajectoryUtil.fromPathweaverJson(trajectoryPath));
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
        }
        trajectoryJSON = "paths/FBTrenchAuto/LineUp.wpilib.json";
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
            TrenchFiveBall.add(TrajectoryUtil.fromPathweaverJson(trajectoryPath));
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
        }

        // Imports for test forward and back with curve
        trajectoryJSON = "paths/Test_Auto_Commands/Forward_Back_Curve/Test_Forward.wpilib.json";
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
            TestForward_Back_W_Curve.add(TrajectoryUtil.fromPathweaverJson(trajectoryPath));
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
        }
        trajectoryJSON = "paths/Test_Auto_Commands/Forward_Back_Curve/TestBackCurve.wpilib.json";
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
            TestForward_Back_W_Curve.add(TrajectoryUtil.fromPathweaverJson(trajectoryPath));
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
        }




        buildCommands();
    }


    /**
     * Builds the commands and puts them into lists in proper order
     */
    private void buildCommands() {

        //FiveBall with start in the trench
        if (TrenchFiveBall.size() == 3) {
            TrenchFiveBallCommands.add(setAutoCode(false, TrenchFiveBall.get(0)));
            TrenchFiveBallCommands.add(setAutoCode(true, TrenchFiveBall.get(1)));
            TrenchFiveBallCommands.add(setAutoCode(false, TrenchFiveBall.get(2)));
        } else {
            System.out.println("Failed to build Trench five ball commands");
        }

        if (TestForward_Back_W_Curve.size() == 2) {
            TestForward_Back_W_Curve_Commands.add(setAutoCode(false, TestForward_Back_W_Curve.get(0)));
            TestForward_Back_W_Curve_Commands.add(setAutoCode(true, TestForward_Back_W_Curve.get(1)));
        } else {
            System.out.println("Failed to build Test Forward + Curve backwards commands");
        }







    }

    public ArrayList<RamseteCommand> getTestForward_Back_W_Curve_Commands() {
        return TestForward_Back_W_Curve_Commands;
    }

    public ArrayList<RamseteCommand> getTrenchFiveBallCommands() {
        return TrenchFiveBallCommands;
    }

    /**
     * Creates the ramsete Commands for the autonomous period
     * @param isReversed states if the trajectory should be done backwards
     * @param trajectory The trajectory to be turned into a command
     * @return RamseteCommand the command you are looking for
     */
    public RamseteCommand setAutoCode(boolean isReversed, Trajectory trajectory) {

        RamseteCommand ramseteCommand;
        aDrive.resetEncoders();


        TrajectoryConfig config = new TrajectoryConfig(1, 1);

        if (isReversed) {
            config.setReversed(true);
        }


        config.setKinematics(aDrive.getKinematics());


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

        return ramseteCommand;
    }





}