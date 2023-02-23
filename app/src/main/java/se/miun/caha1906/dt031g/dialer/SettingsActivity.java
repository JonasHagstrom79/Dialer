package se.miun.caha1906.dt031g.dialer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.FileObserver;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

import java.io.File;

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

            ListPreference voicePref = findPreference(getString(R.string.listPreference_voice_key));
            voicePref.setSummary(voicePref.getEntry());

            // Get the directory that contains the voices
            File voicesDir = new File(getActivity().getFilesDir(), getString(R.string.voices_path));

            // Add a FileObserver to monitor the directory for changes
            FileObserver fileObserver = new FileObserver(voicesDir.getPath()) {
                @Override
                public void onEvent(int event, String path) {
                    // The directory has changed, so update the list of voices
                    updateVoiceList();
                }
            };
            fileObserver.startWatching();

            // Update the list of available voices
            updateVoiceList();

            voicePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    int index = voicePref.findIndexOfValue(newValue.toString());
                    String voicePath = voicePref.getEntryValues()[index].toString();
                    voicePref.setSummary(voicePref.getEntries()[index]);

                    Toast.makeText(getContext(), voicePath, Toast.LENGTH_SHORT).show();

                    // Set the selected voice path in SoundPlayer
                    SoundPlayer.getInstance(getContext()).setVoicePath(voicePath);

                    Log.d("SettingsActivityXX", "Voicepath: "+voicePath);//TODO:test!
                    return true;
                }
            });

//            ListPreference voicePref = findPreference(getString(R.string.voice_key));

//            voicePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//                @Override
//                public boolean onPreferenceChange(Preference preference, Object newValue) {
//                    int index = voicePref.findIndexOfValue(newValue.toString());
//                    voicePref.setSummary(voicePref.getEntries()[index]);
//                    return true;
//                }
//            });


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
                Toast.makeText(getContext(), getString(R.string.toast_text_numbers_deleted), Toast.LENGTH_SHORT).show();

                return true;

            });


            // Handles the storage of numbers On - Off
            SwitchPreferenceCompat storeNumbersPref = findPreference(getString(R.string.store_numbers_key));
            storeNumbersPref.setOnPreferenceChangeListener((preference, newValue) -> {
                // Get the new value of the switch
                boolean shouldStoreNumbers = (Boolean) newValue;


                // Create an intent to pass the value to DialActivity
                Intent settingsIntent = new Intent(getActivity(), DialActivity.class);

                settingsIntent.putExtra("storeNumbers", shouldStoreNumbers);

                return true;

            });

        }

        /**
         * This is a public static method that takes a Context object as an argument and returns a
         * boolean. The method is used to retrieve the value of a shared preference store_numbers_key
         * from the default SharedPreferences object associated with the given context. If the
         * preference does not exist, the method returns true as default.
         * @param context
         * @return
         */
        public static boolean shouldStoreNumbers(Context context) {
            SharedPreferences sharedPreferences = PreferenceManager.
                    getDefaultSharedPreferences(context);
            return sharedPreferences.getBoolean(
                    context.getString(R.string.store_numbers_key), true);
        }

        /**
         * Updats the 'ListPreference' with names and paths of the voice in the directory 'voices',
         * It also enables or disables the ListPreference depending on whether there are any voice
         * files in the directory
         */
        private void updateVoiceList() {

//            // Null check //TODO:gav jättefel!!
//            if (!isAdded() || getContext() == null) {
//                // Fragment is not attached or context is null, do nothing
//                return;
//
//            }

            ListPreference voicePref = findPreference(getString(R.string.listPreference_voice_key));
            voicePref.setSummary(voicePref.getEntry());

            // Get the directory that contains the voices
            File voicesDir = new File(getActivity().getFilesDir(), getString(R.string.voices_path));

            // Get the list of voice files in the directory
            File[] voiceFiles = voicesDir.listFiles();

            if (voiceFiles != null && voiceFiles.length > 0) {
                // Create arrays for the voice names and paths
                String[] voiceNames = new String[voiceFiles.length];
                String[] voicePaths = new String[voiceFiles.length];

                // Populate the arrays with the voice names and paths
                for (int i = 0; i < voiceFiles.length; i++) {
                    voiceNames[i] = voiceFiles[i].getName();
                    voicePaths[i] = voiceFiles[i].getPath();
                }

                // Set the entries and entryValues for the ListPreference
                voicePref.setEntries(voiceNames);
                voicePref.setEntryValues(voicePaths);

                // Enable the ListPreference if it was disabled
                if (!voicePref.isEnabled()) {

                    voicePref.setEnabled(true);

                }

            } else {

                // If there are no voice files, disable the ListPreference
                voicePref.setEnabled(false);

            }
        }


    }
}