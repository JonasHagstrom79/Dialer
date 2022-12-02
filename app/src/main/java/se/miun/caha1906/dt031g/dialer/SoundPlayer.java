package se.miun.caha1906.dt031g.dialer;

import android.content.Context;
import android.media.SoundPool;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class SoundPlayer {



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

        // Load the sounds into the array
        soundId[0] = soundPool.load(context, R.raw.zero, 1);
        soundId[1] = soundPool.load(context, R.raw.one, 1);
        soundId[2] = soundPool.load(context, R.raw.two, 1);
        soundId[3] = soundPool.load(context, R.raw.three, 1);
        soundId[4] = soundPool.load(context, R.raw.four, 1);
        soundId[5] = soundPool.load(context, R.raw.five, 1);
        soundId[6] = soundPool.load(context, R.raw.six, 1);
        soundId[7] = soundPool.load(context, R.raw.seven, 1);
        soundId[8] = soundPool.load(context, R.raw.eight, 1);
        soundId[9] = soundPool.load(context, R.raw.nine, 1);
        soundId[10] = soundPool.load(context, R.raw.pound, 1);
        soundId[11] = soundPool.load(context, R.raw.star, 1);

        return soundPlayer;
    }

    /**
     * Plays the sound of the DialpadButton
     */
    public void playSound(DialpadButton d) { //Remove void

        // Use title to play sound in res/raw
        switch (d.getTitle()) {

            case "0":
                soundPool.play(soundId[0], 1f, 1f, 1,0,1f);
                break;
            case "1":
                soundPool.play(soundId[1], 1f, 1f, 1,0,1f);
                break;
            case "2":
                soundPool.play(soundId[2], 1f, 1f, 1,0,1f);
                break;
            case "3":
                soundPool.play(soundId[3], 1f, 1f, 1,0,1f);
                break;
            case "4":
                soundPool.play(soundId[4], 1f, 1f, 1,0,1f);
                break;
            case "5":
                soundPool.play(soundId[5], 1f, 1f, 1,0,1f);
                break;
            case "6":
                soundPool.play(soundId[6], 1f, 1f, 1,0,1f);
                break;
            case "7":
                soundPool.play(soundId[7], 1f, 1f, 1,0,1f);
                break;
            case "8":
                soundPool.play(soundId[8], 1f, 1f, 1,0,1f);
                break;
            case "9":
                soundPool.play(soundId[9], 1f, 1f, 1,0,1f);
                break;
            case "#":
                soundPool.play(soundId[10], 1f, 1f, 1,0,1f);
                break;
            case "*":
                soundPool.play(soundId[11], 1f, 1f, 1,0,1f);
                break;

        }

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
