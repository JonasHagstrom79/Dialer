package se.miun.caha1906.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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


    }


}