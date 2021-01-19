package frc.robot.Utilities;

import edu.wpi.first.wpilibj.XboxController;

public class XBoxCon extends XboxController {


    public XBoxCon(int id) {


        super(id);
    }

    public double getTurnSpeed() {
        return getRawAxis(4);
    }
    public double getDriveSpeed() {
        return -getRawAxis(1);
    }
    public double getAimSpeed() { return getRawAxis(0); }





}