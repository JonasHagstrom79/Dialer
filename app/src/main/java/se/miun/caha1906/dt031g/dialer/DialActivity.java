package se.miun.caha1906.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class DialActivity extends AppCompatActivity {

    Intent intent = new Intent(Intent.ACTION_DIAL);

    public static final String ACTION_DIAL = "tel:#0101428138";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dial);
        setContentView(R.layout.dialpad);//test ger dailpad där den ska vara

        // Button for call
        Button buttonCall = findViewById(R.id.btnCall);
        // Set click listener
        buttonCall.setOnClickListener(view -> {
            // Adds phone number from text field to intent
            intent.setData(Uri.parse(String.valueOf(R.id.textView_CallList)));

            // Handle #
            android.net.Uri.encode(ACTION_DIAL);
            Log.d("Onclicklistener", "ACTION_DIAL " +ACTION_DIAL); //TODO: REMOVE!
            intent.setData(Uri.parse(ACTION_DIAL)); // Hardcoded for test //TODO: REMOVE!

            Log.d("Onclicklistener", "buttonCall " +intent); //TODO: REMOVE!
            // Starts the activity
            startActivity(intent);

        });

        // Button for delete number
        Button buttonDelete = findViewById(R.id.btnDelete);
        // Set listener for click
        buttonDelete.setOnClickListener(view -> {
            TextView myText = (TextView) findViewById(R.id.textView_CallList);
            myText.setText("Button clicked");
        });


    }

    // Free resources from SoundPlayer as it stops
    @Override
    protected void onStop() {
        super.onStop();
        SoundPlayer s = SoundPlayer.getInstance(this);
        s.destroy();
    }


}