package frc.robot.Utilities;

public class GlobalVars {

    public double DriveSpeed;
    public double TurnSpeed;
    public double dtTicksToFeetLow;
    public double dtTicksToFeetHigh;
    //public static double lowGearRatio = (60/14)/(42/14);
    public static double lowGearRatio = 15;
    public static double highGearRatio = (40/34)/(42/14);
    private int fxEncoder = 2048;


    // Motor - 12 - 42 - 14 - 50


    public GlobalVars() {

        DriveSpeed = 0;
        TurnSpeed = 0;
        
        //Ratio for low gear ticks to ft (slow gear)
        dtTicksToFeetLow = fxEncoder * lowGearRatio * 6 * Math.PI * (1/12);
        //Ratio for High gear ticks to ft (fast gear)
        dtTicksToFeetHigh = fxEncoder * highGearRatio * 6 * Math.PI * (1/12);
        
    }




}