package frc.robot.Utilities;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TFLidar {

    private double lastReadDistance;
    private LidarListener _listener;
    private Thread _thread;
    private boolean _initializedProperly;

    public TFLidar(SerialPort.Port port) {
        setup(port);
    }

    private void setup(SerialPort.Port port) {
        try {
            _listener = new LidarListener(this, port);
            _thread = new Thread(_listener);
            _thread.start();
            _initializedProperly = true;
        } catch (Exception e) {
            _initializedProperly = false;
            DriverStation.reportError("LidarProxy could not intialize properly. " + e.getStackTrace().toString(), false);
        }
        SmartDashboard.putBoolean("Lidar/initializedProperly", _initializedProperly);
    }

    public double get() {
        return lastReadDistance;
    }

    protected class LidarListener implements Runnable {
        private SerialPort _port;
        private TFLidar _proxy;

        protected LidarListener(TFLidar proxy, SerialPort.Port port) {
            _proxy = proxy;
            _port = new SerialPort(115200, port);
            //_port.setReadBufferSize(9);
        }

        public void run() {
            while (true) {
                try {
                    //SmartDashboard.putNumber("Lidar/_port.getBytesReceived()", _port.getBytesReceived());
                    byte[] read = _port.read(9);
                    SmartDashboard.putNumber("Lidar/readLength", read.length);
                    SmartDashboard.putNumber("Lidar/bytes/3", new Integer(read[2] & 0xFF));
                    _proxy.lastReadDistance = read[2] & 0xFF;
                    System.out.println("Data checked");
                } catch (Exception e) {
                    DriverStation.reportError("LidarListener exception: " + e.toString(), false);
                }
            }
        }
    }
}