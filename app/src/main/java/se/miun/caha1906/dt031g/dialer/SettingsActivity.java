package se.miun.caha1906.dt031g.dialer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

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

        private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            Preference deleteNumbersPref = findPreference(getString(R.string.delete_numbers_key));
            deleteNumbersPref.setOnPreferenceClickListener(preference -> {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
                //preferences.edit().remove(getString(R.string.stored_numbers_key)).apply();
                Toast.makeText(requireContext(), "All stored numbers deleted", Toast.LENGTH_SHORT).show();
                return true;
            });
        }



    }
}