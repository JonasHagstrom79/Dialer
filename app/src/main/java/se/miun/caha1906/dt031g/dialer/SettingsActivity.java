package se.miun.caha1906.dt031g.dialer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);


        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }


    public static class SettingsFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            // Handles the delete of all numbers
            Preference deleteNumbersPref = findPreference(getString(R.string.delete_numbers_key));
            deleteNumbersPref.setOnPreferenceClickListener(preference -> {
                // Get the SharedPreferences object
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("phone_numbers", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // Remove the phone numbers from the set
                editor.remove("phone_numbers");
                editor.apply();

                // Notify the user that the numbers have been deleted
                Toast.makeText(getContext(), "All stored numbers deleted", Toast.LENGTH_SHORT).show();

                return true;

            });

//            // Add listener to update "store_numbers" value
//            PreferenceManager.getDefaultSharedPreferences(getContext())
//                    .registerOnSharedPreferenceChangeListener((SharedPreferences.OnSharedPreferenceChangeListener) this);

            // Handels the storage of numbers On - Off
            SwitchPreferenceCompat storeNumbersPref = findPreference(getString(R.string.store_numbers_key));
            storeNumbersPref.setOnPreferenceChangeListener((preference, newValue) -> {
                // Get the new value of the switch
                boolean shouldStoreNumbers = (Boolean) newValue;
                Log.d("SettingsActivity", "BooleanSet "+shouldStoreNumbers);

                // Create an intent to pass the value to DialActivity
                Intent settingsIntent = new Intent(getActivity(), DialActivity.class);
//                Intent intent = new Intent(getContext(), DialActivity.class);
                settingsIntent.putExtra("storeNumbers", shouldStoreNumbers);

//                startActivity(intent);
                Log.d("SettingsActivity", "BooleanSetSist "+shouldStoreNumbers);
                return true;
            });

        }

        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        public static boolean shouldStoreNumbers(Context context) {
            SharedPreferences sharedPreferences = PreferenceManager.
                    getDefaultSharedPreferences(context);
            return sharedPreferences.getBoolean(
                    context.getString(R.string.store_numbers_key), true); // default true
        }

//        @Override
//        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//            if (key.equals(getString(R.string.store_numbers_key))) {
//                boolean shouldStoreNumbers = sharedPreferences.getBoolean(key, true);
//                Log.d("SettingsActivity", "BooleanSet "+shouldStoreNumbers);
//                // Update the value of "store_numbers"
//                sharedPreferences.edit().putBoolean(key, shouldStoreNumbers).apply();
//            }
//        }

//        @Override
//        public void onDestroy() {
//            super.onDestroy();
//            // Remove the listener when the fragment is destroyed
//            PreferenceManager.getDefaultSharedPreferences(getContext())
//                    .unregisterOnSharedPreferenceChangeListener((SharedPreferences.OnSharedPreferenceChangeListener) this);
//        }



    }
}