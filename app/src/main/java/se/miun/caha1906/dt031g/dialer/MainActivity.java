package se.miun.caha1906.dt031g.dialer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, DialActivity.class));
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(view -> {
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
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("About")
                    .setMessage("This app is supposed to mimic the keypad\n" +
                                "on a phone. Ths app willl consist of\n" +
                                "activities to:\n\n" +
                                "* Enter numbers to dial\n" +
                                "* See previously dialed numbers\n" +
                                "* Change th keypad settings\n" +
                                "* Show on a Map where previous calls are dialed from")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MainActivity.this,"Selected Option: Ok", Toast.LENGTH_SHORT).show();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();

        });

    }


}