package frc.robot.Mechanisms;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.RobotMap;

public class WheelOfFortune {


    private String gameData;

    //Vars for spin 3 times
    private Color firstColor;
    private Color pastColor;
    private int timesSeen;



    // Color sensor stuff
    private ColorSensorV3 cSensor;
    private ColorMatch cMatch;

    //COLORS- MUST CHECK AT COMPETITION TO CALIBRATE;
    private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);


    /**
     * Initializes the wheel of fortune code
     */
    public WheelOfFortune() {
        cSensor = RobotMap.colorSensor;

        cMatch = new ColorMatch();
        
        cMatch.addColorMatch(kBlueTarget);
        cMatch.addColorMatch(kGreenTarget);
        cMatch.addColorMatch(kRedTarget);
        cMatch.addColorMatch(kYellowTarget);

        SmartDashboard.putBoolean("Correct Color?", false);

    }


    /**
     * Tries to recieve data from the fms, if it gets it it will store it and return true
     * @return boolean
     */
    public boolean checkData() {
        gameData = DriverStation.getInstance().getGameSpecificMessage();

        if (gameData.length() > 0) {
            return true;
        }
        else {
           return false; 
        }
    }




    /**
     * Program to find the specific color on the pannel (lines up the given color with their sensors)
     * 
     * - if given red look for blue
     * - if given yellow look for green
     * - if given blue look for red
     * - if given green look for yellow
     * 
     */
    public void setToColor() {

        if (checkDistance()) {

            char color = getColorChar();

            if (color != '0') {
                System.out.println(gameData.charAt(0));
                switch (gameData.charAt(0)) {
                    case 'R':
                        // if color given is red
                        System.out.println("Looking for blue to set to Red");
                        if (getColorChar() != 'B') {
                            RobotMap.WoFMotor.set(0.3);
                            SmartDashboard.putBoolean("Correct Color?", false);
                        }
                        else {
                            RobotMap.WoFMotor.set(0);
                            SmartDashboard.putBoolean("Correct Color?", true);
                        }
                        break;
                    case 'G':
                        // if color given is green
                        System.out.println("Looking for yellow to set to green");
                        if (getColorChar() != 'Y') {
                            RobotMap.WoFMotor.set(0.3);
                            SmartDashboard.putBoolean("Correct Color?", false);
                        }
                        else {
                            RobotMap.WoFMotor.set(0);
                            SmartDashboard.putBoolean("Correct Color?", true);
                        }
                        break;
                    case 'B':
                        // if color given is blue
                        System.out.println("Looking for red to set to Blue");
                        if (getColorChar() != 'R') {
                            RobotMap.WoFMotor.set(0.3);
                            SmartDashboard.putBoolean("Correct Color?", false);
                        }
                        else {
                            RobotMap.WoFMotor.set(0);
                            SmartDashboard.putBoolean("Correct Color?", true);
                        }
                        break;
                    case 'Y':
                        // if color given is yellow
                        System.out.println("Looking for green To set Yellow");
                        if (getColorChar() != 'G') {
                            RobotMap.WoFMotor.set(0.3);
                            SmartDashboard.putBoolean("Correct Color?", false);
                        }
                        else {
                            RobotMap.WoFMotor.set(0);
                            SmartDashboard.putBoolean("Correct Color?", true);
                        }
                        break;
                    default:
                        //Something broke
                        System.out.println("SOMETHING BROKE");
                        break;
        
                }
            }
        }
        else {
            SmartDashboard.putBoolean("Correct Color?", false);
        }   
    }

    /**
     * Spins the wheel 3 times but less than 5
     */
    public void spin3Times() {

        if(checkDistance()) {
            Color currentColor = getWheelColor();
            if (firstColor == null) {
                firstColor = currentColor;
                pastColor = firstColor;
                timesSeen = 0;
                SmartDashboard.putBoolean("Spun 3x", false);
            } else {
                if (currentColor != pastColor && currentColor == firstColor) {
                    timesSeen++;
                }
                pastColor = currentColor;
            }
            if (timesSeen <= 5) {
                //RobotMap.WoFMotor.set(.5);
            } else {
                //RobotMap.WoFMotor.set(0);
                SmartDashboard.putBoolean("Spun 3x", true);

            }
        }
        else {
            firstColor = null;
        }

    }
    


    /**
     * Gets the color as a char from the color sensor
     */
    private char getColorChar() {
        char color = '0';

        
        Color detectedColor = cSensor.getColor();        

        ColorMatchResult match = cMatch.matchClosestColor(detectedColor);

        if (match.color == kBlueTarget) {
            color = 'B';
        } else if (match.color == kGreenTarget){
            color = 'G';
        } else if (match.color == kRedTarget) {
            color = 'R';
        } else if (match.color == kYellowTarget) {
            color = 'Y';
        }
        
        return color;
    }

    /**
     * Returns the current color on the wheel
     * @return Color 
     */
    private Color getWheelColor() {
        Color detectedColor = cSensor.getColor();
        ColorMatchResult match = cMatch.matchClosestColor(detectedColor);
        return match.color;
    }



    /**
     * 
     * @return boolean - if it is close enough to wheel or not
     */
    public boolean checkDistance() {

        System.out.println(cSensor.getIR());
        if (cSensor.getIR() > 20) {
            SmartDashboard.putBoolean("Spinner Ready?", true);
            return true;
        }        
        else {
            SmartDashboard.putBoolean("Spinner Ready?", false);
            return false;
        }
        
    }
}