package se.miun.caha1906.dt031g.dialer;

import android.content.Context;
import android.media.SoundPool;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {


    // Path to the selected voice file
    private static String selectedVoicePath;

    private static SoundPlayer soundPlayer = null;

    private static SoundPool soundPool;

    // Array for storing sounds to play
    private static final int[] soundId = new int[12];

    // Creates the singelton
    public static SoundPlayer getInstance(Context context) {

        if(soundPlayer == null) {

            soundPlayer = new SoundPlayer();

        }
        soundPool = new SoundPool.Builder()
                // Config the soundpool
                // Max sounds at once
                .setMaxStreams(3)
                .build();


        Log.d("SoundplayerXXX", "path: "+selectedVoicePath);
        File path;
//        //File path = new File(context.getFilesDir(), "voices");
//        File path =Util.getDirForDefaultVoice(context);
        if (selectedVoicePath == null) {

            path =Util.getDirForDefaultVoice(context);

        } else {
            path = new File(selectedVoicePath);
        }
//        if (path.exists()) {
//            File[] files = path.listFiles();
//            for (int i = 0; i < files.length; i++) {
//                String filename = files[i].getName();
//                int index = Integer.parseInt(filename.substring(0, 1));
//                soundId[index] = soundPool.load(files[i].getPath(), 1);
//            }
//        }
//        return soundPlayer;
//    }


        // Get the sounds from internal storage
        //File path = Util.getDirForDefaultVoice(context);

        // Load the sounds into the array
        soundId[0] = soundPool.load(path + "/zero."+Util.DEFAULT_VOICE_EXTENSION,1);
        soundId[1] = soundPool.load(path + "/one."+Util.DEFAULT_VOICE_EXTENSION, 1);
        soundId[2] = soundPool.load(path + "/two."+Util.DEFAULT_VOICE_EXTENSION, 1);
        soundId[3] = soundPool.load(path + "/three."+Util.DEFAULT_VOICE_EXTENSION, 1);
        soundId[4] = soundPool.load(path + "/four."+Util.DEFAULT_VOICE_EXTENSION, 1);
        soundId[5] = soundPool.load(path + "/five."+Util.DEFAULT_VOICE_EXTENSION, 1);
        soundId[6] = soundPool.load(path + "/six."+Util.DEFAULT_VOICE_EXTENSION, 1);
        soundId[7] = soundPool.load(path + "/seven."+Util.DEFAULT_VOICE_EXTENSION, 1);
        soundId[8] = soundPool.load(path + "/eight."+Util.DEFAULT_VOICE_EXTENSION, 1);
        soundId[9] = soundPool.load(path + "/nine."+Util.DEFAULT_VOICE_EXTENSION, 1);
        soundId[10] = soundPool.load(path + "/pound."+Util.DEFAULT_VOICE_EXTENSION, 1);
        soundId[11] = soundPool.load(path + "/star."+Util.DEFAULT_VOICE_EXTENSION, 1);

        return soundPlayer;
    }

    /**
     * Plays the sound of the DialpadButton
     */
    public void playSound(DialpadButton d) {



            // Use title to play sound in res/raw
            switch (d.getTitle()) {

                case "0":
                    soundPool.play(soundId[0], 1f, 1f, 1, 0, 1f);
                    break;
                case "1":
                    soundPool.play(soundId[1], 1f, 1f, 1, 0, 1f);
                    break;
                case "2":
                    soundPool.play(soundId[2], 1f, 1f, 1, 0, 1f);
                    break;
                case "3":
                    soundPool.play(soundId[3], 1f, 1f, 1, 0, 1f);
                    break;
                case "4":
                    soundPool.play(soundId[4], 1f, 1f, 1, 0, 1f);
                    break;
                case "5":
                    soundPool.play(soundId[5], 1f, 1f, 1, 0, 1f);
                    break;
                case "6":
                    soundPool.play(soundId[6], 1f, 1f, 1, 0, 1f);
                    break;
                case "7":
                    soundPool.play(soundId[7], 1f, 1f, 1, 0, 1f);
                    break;
                case "8":
                    soundPool.play(soundId[8], 1f, 1f, 1, 0, 1f);
                    break;
                case "9":
                    soundPool.play(soundId[9], 1f, 1f, 1, 0, 1f);
                    break;
                case "#":
                    soundPool.play(soundId[10], 1f, 1f, 1, 0, 1f);
                    break;
                case "*":
                    soundPool.play(soundId[11], 1f, 1f, 1, 0, 1f);
                    break;

            }


    }

    /**
     * Sets the path to the sound to be played
     * @param path path to sound
     */
    public void setVoicePath(String path) {
        selectedVoicePath = path;
    }

    /**
     * Releases the resources SoundPlayer uses
     */
    public void destroy() {

        soundPool.release();
        this.soundPlayer = null;
        this.soundPool = null;

    }

}
