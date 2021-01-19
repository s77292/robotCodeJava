package frc.robot.Utilities;

import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.music.Orchestra;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.RobotMap;

public class MusicBox {

    private TalonFX[] _Falcons = {new TalonFX(2), new TalonFX(4), new TalonFX(6), new TalonFX(8), new TalonFX(5)};


    private Orchestra _orchestra;

    private int _timeToPlayLoops = 0;
    private int _songSelection = 0;

    private boolean started;

    private XBoxCon xbc;


    private String[] songList = new String[] {

        "ImpMarch.chrp",
        "DuelOfTheFates.chrp",
        "CrabRave.chrp",
        "ThunderStruck.chrp",
        "HWYToHLL.chrp",
        "1812Overture.chrp",
        "WiiChannel.chrp",
        "Megalovania.chrp",
        "Despacito.chrp",
        "PkmnRBYChamp.chrp",
        "LavenderTown.chrp",
        "jeopardy.chrp",
        "WeLike2Party.chrp",
        "RickRoll.chrp",
        "Halo.chrp"

    };

    public MusicBox(XBoxCon xbc) {
        ArrayList<TalonFX> _instruments = new ArrayList<TalonFX>();

        for (int i = 0; i < _Falcons.length; ++i) {
            _instruments.add(_Falcons[i]);
        }

        _orchestra = new Orchestra(_instruments);

        this.xbc = xbc;
        

    }

    private void LoadMusicSelection(int offset) {
        _songSelection += offset;

        if(_songSelection >= songList.length) {
            _songSelection = 0;
        }
        if(_songSelection < 0) {
            _songSelection = songList.length -1;
        }

        _orchestra.loadMusic(songList[_songSelection]);

        System.out.println("Song selected is: " + songList[_songSelection] + ".  Press left/right bumper to change.");
        

        _timeToPlayLoops = 10;
    }


    public void update() {
        //System.out.println("update Mb");



        if (started) {

            if (_timeToPlayLoops > 0) {
                --_timeToPlayLoops;
                if (_timeToPlayLoops ==0) {
                    System.out.println("Auto-Playing song.");
                    _orchestra.play();
                    System.out.println("Played music");
                }
            }


            //Changes the song selection
            if (xbc.getBumperPressed(Hand.kLeft)) {
                LoadMusicSelection(-1);
            }

            if (xbc.getBumperPressed(Hand.kRight)) {
                //_songSelection += 1;
                //System.out.println("SongSelection: " + _songSelection);
                LoadMusicSelection(1);
            }

            if(xbc.getYButtonPressed()) {
                _orchestra.pause();
            }
            else if (xbc.getBButtonPressed()) {
                _orchestra.play();
            }

        }
        
        //Starts or stops the music
        if (xbc.getXButtonPressed()) {

            if (started) {
                System.out.println("Music Box disabled");
                _orchestra.stop();
                started = false;
                RobotMap.compressor.start();
                RobotMap.compressor.setClosedLoopControl(true);
            }
            else {
                System.out.println("Music Box enabled");
                started = true;
                RobotMap.compressor.stop();
                LoadMusicSelection(0);
            }
        }

    }
    

}