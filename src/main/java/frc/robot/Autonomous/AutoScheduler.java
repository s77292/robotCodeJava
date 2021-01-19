package frc.robot.Autonomous;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

import java.util.ArrayList;

public class AutoScheduler {


    private AutonomousSwitcher autoSwitcher;
    private TrajectoryBuilder builder;
    private String SelectedAuto;
    private boolean scheduled;
    private boolean isFinished;

    private boolean FiveBallAuto = true;

    public AutoScheduler(AutonomousSwitcher autoSwitcher, TrajectoryBuilder builder, AutoDrive adrive) {
        this.builder = builder;
        this.autoSwitcher = autoSwitcher;
        scheduled = false;
        isFinished = false;
        CommandScheduler.getInstance().registerSubsystem(adrive);

    }

    public void selectAuto() {

        String testAuto = autoSwitcher.getTestAuto();
        String basicAuto = autoSwitcher.getBasicAuto();

        if (!testAuto.equals("none")) {
            SelectedAuto = testAuto;
        }
        if (!basicAuto.equals("none")) {
            SelectedAuto = basicAuto;
        }



    }


    /**
     * Runs all the other  auto things based on what is selected
     */
    public void runAuto() {

        if(SelectedAuto.equals("Forward-Back-W-Curve")) {
            Test_Forward_Back_W_Curve();
        }
        if (SelectedAuto.equals("5-Ball-Trench")) {
            Basic_Five_Ball_Auto_Trench();
        }


        CommandScheduler.getInstance().run();
    }


    private void Test_Forward_Back_W_Curve() {
        if (scheduled) {
            System.out.println("Running Test_Forward_Back_W_Curve");
        } else {

            ArrayList<RamseteCommand> commands = builder.getTestForward_Back_W_Curve_Commands();

            CommandScheduler.getInstance().schedule(commands.get(0).andThen(commands.get(1)));
            scheduled = true;
        }
    }

    private void Basic_Five_Ball_Auto_Trench() {
        if (scheduled) {
            System.out.println("Basic_Five_Ball_Auto_Trench");
        } else {

            ArrayList<RamseteCommand> commands = builder.getTrenchFiveBallCommands();

            CommandScheduler.getInstance().schedule(commands.get(0).andThen(commands.get(1)).andThen(commands.get(2)));
            scheduled = true;
        }
    }






}