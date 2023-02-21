package se.miun.caha1906.dt031g.dialer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SharedElementCallback;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {


    private static final String KEY_ABOUT_VALUE = "Aboutvalue";
    private boolean about = false;


    Button button, button2, button3, button4, button5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the views
        findViews();


        // Copies soundfiles to internal storage if the doesnt exist
        if (!Util.defaultVoiceExist(this)) {
            Util.copyDefaultVoiceToInternalStorage(this);
        }

        button.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, DialActivity.class));
        });

        button2.setOnClickListener(view -> {
            //s.testXX(); // not working
            startActivity(new Intent(MainActivity.this, CallListActivity.class));
        });

        button3.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        });

        button4.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, MapsActivity.class));
        });

        button5.setOnClickListener(view -> {

            // If about have not been clicked
            if (!about) {

                aboutMessage();
                about = true;

            // If about have been clicked
            } else {

                aboutMessageAlreadyDisplayed();

            }

        });

        if (savedInstanceState != null) {

            boolean value = savedInstanceState.getBoolean(KEY_ABOUT_VALUE);
            // Resets the value
            about = value;

        }

    }

    /**
     * Get the views
     * */
    private void findViews() {

        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);

    }

    /**
     * Display that about-message already been viewed
     * */
    private void aboutMessageAlreadyDisplayed() {

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, R.string.toast_msg, duration);
        toast.show();
    }

    /**
     * Dispalys toast-message for about button
     * */
    private void aboutMessage() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.title_about)
                .setMessage(R.string.about_message)
                .setCancelable(false)
                .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, R.string.toast_ok, Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putBoolean("Aboutvalue", about);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected  void onResume() {

        super.onResume();

        storeSound();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    private void storeSound() {

        File localDir = getApplicationContext().getFilesDir();

    }

}