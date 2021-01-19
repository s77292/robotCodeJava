package frc.robot.Utilities;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class PiDataProcessor {


    public NetworkTableEntry yaw;
    public NetworkTableEntry isDriverMode;
    private XBoxCon xbc;

    public PiDataProcessor(XBoxCon xbc) {

        // Gets the default instance of NetworkTables
        NetworkTableInstance table = NetworkTableInstance.getDefault();

        // Gets the life cam table under the chamelon vision table
        NetworkTable cameraTable = table.getTable("chameleon-vision").getSubTable("Microsoft LifeCam HD-3000");

        //Gets the yaw to the target from the camera table
        yaw = cameraTable.getEntry("targetYaw");

        // Gets the driveMode boolean from the cameraTable
        isDriverMode = cameraTable.getEntry("driver_mode");

        this.xbc = xbc;
    }

    public void update() {
        //prints out the yaw of the target
        System.out.println(yaw.getDouble(0.0));

        // Sets the driver mode to true if button is pressed
        isDriverMode.setBoolean(xbc.getStickButton(Hand.kRight));
        System.out.println(getDriverMode());
    }


    public double getYaw() {
        return yaw.getDouble(0.0);
    }
    public boolean getDriverMode() {
        return isDriverMode.getBoolean(false);
    }


}