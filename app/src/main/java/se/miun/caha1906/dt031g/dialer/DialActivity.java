package se.miun.caha1906.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.SoundPool;
import android.os.Bundle;

public class DialActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dial);
        setContentView(R.layout.dialpad);//test ger dailpad där den ska vara

    }

    // Free resources from SoundPlayer as it stops
    @Override
    protected void onStop() {
        super.onStop();
        SoundPlayer s = SoundPlayer.getInstance(this);
        s.destroy();
    }


}