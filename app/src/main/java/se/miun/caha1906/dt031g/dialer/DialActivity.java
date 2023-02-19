package se.miun.caha1906.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class DialActivity extends AppCompatActivity {

    // Gets the user to the phone
    Intent intent = new Intent(Intent.ACTION_DIAL);

    // Phone number to be called
    public static final String ACTION_DIAL = "tel:#0101428138";

    // Initiate views
    DialpadButton buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix,
            buttonSeven, buttonEight, buttonNine, buttonZero, buttonStar, buttonPound;//,
            //buttonCall;
    TextView numberDisplay;

    Button buttonCall;

    Dialpad d;

    // Handle the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dial_menu, menu);
        return true;
    }

    // Handle user interactions with the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle menu item selection
//        switch (item.getItemId()) {
//            case R.id.menu_item_1:
//                // Do something when menu item 1 is selected
//                return true;
//            case R.id.menu_item_2:
//                // Do something when menu item 2 is selected
//                return true;
//            default:

        int id = item.getItemId();

        if(id == R.id.menu_settings) {

            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;

        }


        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialpad);//test ger dailpad där den ska vara

        //Get the views
        findViews();

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
            public void onClick(DialpadButton dialpadButton) { updateNumberDisplay(dialpadButton); }
        });


        // Set click listener
        buttonCall.setOnClickListener(view -> {

            // Get the phone number from the display text
            String phoneNumber = numberDisplay.getText().toString();

            // Save the phone number to SharedPreferences
            savePhoneNumber(phoneNumber);

            // Encodes the phone number to get #
            String encodedPhoneNumber = Uri.encode(phoneNumber);

            // Create a Uri with the phone number to be used in the Intent
            Uri phoneUri = Uri.parse("tel:" + encodedPhoneNumber);

            // Create a new Intent with the ACTION_DIAL action and the Uri with the phone number
            Intent callIntent = new Intent(Intent.ACTION_DIAL, phoneUri);

//            if (callIntent.resolveActivity(getPackageManager()) != null) {
//
//                // Start the Intent, which will open the device's phone app with the phone number pre-filled
//                startActivity(callIntent);
//
//            } else {
//                // Handle the case where there is no activity that can handle the intent
//                Toast.makeText(this, "No phone app found", Toast.LENGTH_SHORT).show();
//
//            }

            // Start the Intent, which will open the device's phone app with the phone number pre-filled
            startActivity(callIntent);

        });

        // Button for delete number
        Button buttonDelete = (Button) findViewById(R.id.btnDelete);

        // Set listener for click
        buttonDelete.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(DialActivity.this, "Short click", Toast.LENGTH_SHORT).show();
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
                        buttonDelete.setText("LOOOOOONG Click!");
                        Toast.makeText(DialActivity.this, "Long click", Toast.LENGTH_SHORT).show();
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
     * Saves phone nuber to Set
     * */
    private void savePhoneNumber(String phoneNumber) {

        // Get the SharedPreferences object
        SharedPreferences sharedPreferences = getSharedPreferences("phone_numbers", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Get the existing set of phone numbers, or create a new set if it doesn't exist
        Set<String> phoneNumbers = sharedPreferences.getStringSet("phone_numbers", new HashSet<>());

        // Add the new phone number to the set
        phoneNumbers.add(phoneNumber);

        //TODO:remove
        Toast.makeText(this, "X"+phoneNumbers, Toast.LENGTH_SHORT).show();
        //toast.show();

        // Save the updated set of phone numbers
        editor.putStringSet("phone_numbers", phoneNumbers);
        editor.apply();
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

        // Dial buttons
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

        // Regular buttons
        buttonCall = findViewById(R.id.btnCall);

        // Textview
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