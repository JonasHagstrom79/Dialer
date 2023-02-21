package se.miun.caha1906.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

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


    // Initialize views for the buttons and number display
    DialpadButton buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix,
            buttonSeven, buttonEight, buttonNine, buttonZero, buttonStar, buttonPound;

    TextView numberDisplay;

    // Initialize views for the call and delete buttons
    Button buttonCall, buttonDelete;

    // Initialize a SoundPlayer object to play sounds
    SoundPlayer s;

    // Handle the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dial_menu, menu);
        return true;
    }

    // Handle user interactions with the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Get the ID of the selected menu item
        int id = item.getItemId();

        // Check which menu item was selected
        if(id == R.id.menu_settings) {

            // Create a new intent to start the SettingsActivity
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;

        }

        // If no menu item was selected, return the result of the superclass method
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialpad);


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

            // Start the Intent, which will open the device's phone app with the phone number pre-filled
            startActivity(callIntent);

        });


        // Set listener for click
        buttonDelete.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
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
     * Saves phone number to Set
     * */
    private void savePhoneNumber(String phoneNumber) {

        // Get the SharedPreferences object
        SharedPreferences sharedPreferences = getSharedPreferences("phone_numbers", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Get the value from save-switch
        boolean shouldStoreNumbers = SettingsActivity.SettingsFragment.shouldStoreNumbers(this);

        if (shouldStoreNumbers) {
            // Get the existing set of phone numbers, or create a new set if it doesn't exist
            Set<String> phoneNumbers = sharedPreferences.getStringSet("phone_numbers", new HashSet<>());

            // Add the new phone number to the set
            phoneNumbers.add(phoneNumber);

            // Save the updated set of phone numbers
            editor.putStringSet("phone_numbers", phoneNumbers);
            editor.apply();
        } else {
            // Notify the user that the phone number wasn't saved
            Toast.makeText(this, getString(R.string.toast_text_not_store_numbers), Toast.LENGTH_SHORT).show();
        }

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

        // Dialpad buttons
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
        buttonDelete = findViewById(R.id.btnDelete);

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

    // Re-initializes the soundpool when returning to DialActivity
    @Override
    protected void onResume() {
        super.onResume();
        s = SoundPlayer.getInstance(this);
    }


}