package se.miun.caha1906.dt031g.dialer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

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

        }



    }
}