package se.miun.caha1906.dt031g.dialer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SharedElementCallback;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_ABOUT_VALUE = "Aboutvalue";
    private boolean about = false;

    SoundPlayer s;

    String defaultVoice = Util.DEFAULT_VOICE; //Nytt

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //File from = new File() //Fungerqr ej
        File newFile = new File(this.getFilesDir(), "text.txt");
        File u = Util.getDirForDefaultVoice(this); ///data/user/0/se.miun.caha1906.dt031g.dialer/files/voices/mamacita_us
        File x = Util.getInternalStorageDir(this); ///data/user/0/se.miun.caha1906.dt031g.dialer/files
        this.deleteFile("NewTextFile.txt");
        //TESTAR, alla filer kom in när jag använde den här metoden??
        writeFileOnInternalStorage(this,"rolig.txt","Snart är det jul");
        File file = new File(u, "text.txt"); ///data/user/0/se.miun.caha1906.dt031g.dialer/files/text.txt

        Util.copyDefaultVoiceToInternalStorage(this);



        Log.d("Mainactivity","L25 onCreate"); //TODO: Remove!

        Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, DialActivity.class));
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(view -> {
            //s.testXX(); // not working
            startActivity(new Intent(MainActivity.this, CallListActivity.class));
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        });

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, MapsActivity.class));
        });

        Button button5 = findViewById(R.id.button5);
        button5.setOnClickListener(view -> {
            // If about have not been clicked
            if (!about) {
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
                // Set the value to "clicked" or true
                about = true;
                Log.d("Mainactivity", "L62 onButton5: about.value = " + about); //TODO: Remove!
            // If about have been clicked
            } else {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, R.string.toast_msg, duration);
                toast.show();
            }

        });

        if (savedInstanceState != null) {

            boolean value = savedInstanceState.getBoolean(KEY_ABOUT_VALUE);
            // Resets the value
            about = value;
            Log.d("MainActivity","L66 onCreate: about.value = " + about); //TODO: Remove!
        }

    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        Log.d("@Override","L90 onSavedInstanceState: about.value = " + about); //TODO: Remove!
        outState.putBoolean("Aboutvalue", about);

        super.onSaveInstanceState(outState);
    }

    @Override //När man fortsätter kanske
    protected  void onResume() {
        super.onResume(); // Always call super first

        storeSound();
    }

    @Override //Save data in onPause
    protected void onPause() {
        super.onPause();
        // Från youtube2 4:27
//        SharedPreferences.Editor preferencesEditor =
//                mPreferences.edit();


        //Spara ljuden?
        writeFileOnInternalStorage(this,"rolig.txt","Snart är det jul");
    }

    public void writeFileOnInternalStorage(Context mcoContext, String sFileName, String sBody){
            File dir = new File(mcoContext.getFilesDir(), "mydir");
            if(!dir.exists()){
                dir.mkdir();
            }

            try {
                File gpxfile = new File(dir, sFileName);
                FileWriter writer = new FileWriter(gpxfile);
                writer.append(sBody);
                writer.flush();
                writer.close();
            } catch (Exception e){
                e.printStackTrace();
            }
    }

    private void storeSound() {




        File localDir = getApplicationContext().getFilesDir(); //localDir: "/data/user/0/se.miun,caha1906.dt031g.dialer.files"

//        String test = "afag";
//        File file = new File(
//                getApplicationContext(), "filename", "clicks.txt");

//        try (PrintWriter out = new PrintWriter(new FileWriter(
//                new File(localDir, Util..getFilename(this))))) {
//            out.println(button1Clicks);
//            out.println(button2Clicks);
//        } catch (IOException e) {
//            // If an IOException occurs, we just ignore it
//            Log.e(TAG, "Error writing number of clicks to file: " + e.getMessage());
//        }
        // Från stackoverflow nedan
//        public void writeFileOnInternalStorage(Context mcoContext, String sFileName, String sBody){
//            File dir = new File(mcoContext.getFilesDir(), "mydir");
//            if(!dir.exists()){
//                dir.mkdir();
//            }
//
//            try {
//                File gpxfile = new File(dir, sFileName);
//                FileWriter writer = new FileWriter(gpxfile);
//                writer.append(sBody);
//                writer.flush();
//                writer.close();
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        }

    }


}