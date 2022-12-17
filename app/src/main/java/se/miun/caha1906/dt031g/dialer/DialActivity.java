package se.miun.caha1906.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DialActivity extends AppCompatActivity {

    // Gets the user to the phone
    Intent intent = new Intent(Intent.ACTION_DIAL);

    // Phone number to be called
    public static final String ACTION_DIAL = "tel:#0101428138";

    // Initiate views
    DialpadButton buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix,
            buttonSeven, buttonEight, buttonNine, buttonZero, buttonStar, buttonPound, x;
    TextView numberDisplay;

    Dialpad d; //TODO: remove?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialpad);//test ger dailpad där den ska vara //TODO: remove comment!

        //Get the views
        findViews();

//        onCreateOptionsMenu(){ //TODO:här!!
//            inflate(this, R.layout.dial_menu, this);
//        }

        // Set up listener on buttons
        buttonOne.setDialpadClickListener(new DialpadButton.DialpadClickListener() {
            @Override
            public void onClick(DialpadButton dialpadButton) {
                updateNumberDisplay(dialpadButton);
            }
        });

        buttonTwo.setDialpadClickListener(new DialpadButton.DialpadClickListener() {
            @Override
            public void onClick(DialpadButton dialpadButton) {
                updateNumberDisplay(dialpadButton);
            }
        });

        buttonThree.setDialpadClickListener(new DialpadButton.DialpadClickListener() {
            @Override
            public void onClick(DialpadButton dialpadButton) {
                updateNumberDisplay(dialpadButton);
            }
        });

        buttonFour.setDialpadClickListener(new DialpadButton.DialpadClickListener() {
            @Override
            public void onClick(DialpadButton dialpadButton) {
                updateNumberDisplay(dialpadButton);
            }
        });

        buttonFive.setDialpadClickListener(new DialpadButton.DialpadClickListener() {
            @Override
            public void onClick(DialpadButton dialpadButton) {
                updateNumberDisplay(dialpadButton);
            }
        });

        buttonSix.setDialpadClickListener(new DialpadButton.DialpadClickListener() {
            @Override
            public void onClick(DialpadButton dialpadButton) {
                updateNumberDisplay(dialpadButton);
            }
        });

        buttonSeven.setDialpadClickListener(new DialpadButton.DialpadClickListener() {
            @Override
            public void onClick(DialpadButton dialpadButton) {
                updateNumberDisplay(dialpadButton);
            }
        });

        buttonEight.setDialpadClickListener(new DialpadButton.DialpadClickListener() {
            @Override
            public void onClick(DialpadButton dialpadButton) {
                updateNumberDisplay(dialpadButton);
            }
        });

        buttonNine.setDialpadClickListener(new DialpadButton.DialpadClickListener() {
            @Override
            public void onClick(DialpadButton dialpadButton) {
                updateNumberDisplay(dialpadButton);
            }
        });

        buttonStar.setDialpadClickListener(new DialpadButton.DialpadClickListener() {
            @Override
            public void onClick(DialpadButton dialpadButton) {
                updateNumberDisplay(dialpadButton);
            }
        });

        buttonPound.setDialpadClickListener(new DialpadButton.DialpadClickListener() {
            @Override
            public void onClick(DialpadButton dialpadButton) {
                updateNumberDisplay(dialpadButton);
            }
        });

        buttonZero.setDialpadClickListener(new DialpadButton.DialpadClickListener() {
            @Override
            public void onClick(DialpadButton dialpadButton) {
                updateNumberDisplay(dialpadButton);
            }
        });


        // Button for call
        Button buttonCall = findViewById(R.id.btnCall);
        TextView numberDisplay = findViewById(R.id.numbersDialedTextView); //Går med knappen men ej med textview på samma komponent??

        // Set click listener
        buttonCall.setOnClickListener(view -> {
            // Adds phone number from text field to intent
            intent.setData(Uri.parse(String.valueOf(R.id.textView_CallList))); //TODO:vad är textView_Calllist?

            // Handle #
            android.net.Uri.encode(ACTION_DIAL); //TODO: Does not handle #
            Log.d("Onclicklistener", "ACTION_DIAL " +ACTION_DIAL); //TODO: REMOVE!
            intent.setData(Uri.parse(ACTION_DIAL)); // Hardcoded for test //TODO: REMOVE!

            Log.d("Onclicklistener", "buttonCall " +intent); //TODO: REMOVE!
            // Starts the activity
            startActivity(intent);

        });

        // Button for delete number
        Button buttonDelete = (Button) findViewById(R.id.btnDelete);

        // Set listener for click
        buttonDelete.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(DialActivity.this, "Short click", Toast.LENGTH_SHORT).show(); //TODO:delete text from textView
                        // Gets the text value
                        TextView mess = findViewById(R.id.numbersDialedTextView);
                        // Transform to string
                        String a = mess.getText().toString();
                        // Prevents the app from crashing
                        if (a.length() >= 1) {
                            // Removes last char each click
                            String res = a.substring(0, a.length() -1);
                            // Displays the new string
                            mess.setText(res);
                        } else {
                            // Displays an empty string
                            mess.setText("");
                        }

                    }
                }
        );
        // Set listener for long-click
        buttonDelete.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v)
                    {
                        buttonDelete.setText("LOOOOOONG Click!");//TODO:delete text from textView
                        Toast.makeText(DialActivity.this, "Long click", Toast.LENGTH_SHORT).show(); //TODO:delete text from textView
                        // Gets the text value
                        TextView mess = findViewById(R.id.numbersDialedTextView);
                        // Transform to string
                        String a = mess.getText().toString();
                        // Removes all charachters
                        String res = a.substring(0, a.length() -a.length());
                        // Displays the new string
                        mess.setText(res);
                        return true;
                    }
                }
        );

    }

    /**
     * Updates the numberdisplay every time a new digit is pushed
     * */
    private void updateNumberDisplay(DialpadButton dialpadButton) {

        // Get number pushed
        String numberPushed = dialpadButton.getTitle();
        // Get numbers already pushed
        String numbers = numberDisplay.getText().toString();
        // Update the display string
        String newNumbers = numbers+numberPushed;
        // Displays the new string
        numberDisplay.setText(newNumbers);
    }

    /**
     * Get the views
     * */
    private void findViews() {

        buttonOne = findViewById(R.id.DialpadButtonOne);
        buttonTwo = findViewById(R.id.DialpadButtonTwo);
        buttonThree = findViewById(R.id.DialpadButtonThree);
        buttonFour = findViewById(R.id.DialpadButtonFour);
        buttonFive = findViewById(R.id.DialpadButtonFive);
        buttonSix = findViewById(R.id.DialpadButtonSix);
        buttonSeven = findViewById(R.id.DialpadButtonSeven);
        buttonEight = findViewById(R.id.DialpadButtonEight);
        buttonNine = findViewById(R.id.DialpadButtonNine);
        buttonStar = findViewById(R.id.DialpadButtonStar);
        buttonPound = findViewById(R.id.DialpadButtonPound);
        buttonZero = findViewById(R.id.DialpadButtonZero);

        numberDisplay = findViewById(R.id.numbersDialedTextView);

    }


    // Free resources from SoundPlayer as it stops
    @Override
    protected void onStop() {
        super.onStop();
        SoundPlayer s = SoundPlayer.getInstance(this);
        s.destroy();
    }


}