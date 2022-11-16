package se.miun.caha1906.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);


        //DialpadButton myButton = new DialpadButton(this); // Gör så skiten hänger sig
        //myButton.setTitle("3"); // Fungerar ej!
        //myButton.setMessage("ABCF"); // Fungerar ej!
        //setContentView(R.layout.dialpadbutton); // Fungerar!! :)
        //setContenView(R.layout.dialpadbutton);
    }


}