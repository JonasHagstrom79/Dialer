package se.miun.caha1906.dt031g.dialer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CallListActivity extends AppCompatActivity {

    // Declare a RecyclerView and a TextView as class members
    private RecyclerView phoneNumberList;
    private TextView emptyListText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_list);
        setTitle(R.string.call_list_bar_display_name);

        // Call the findViews method to initialize the views
        findViews();

        // Get the phone numbers from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("phone_numbers", MODE_PRIVATE);
        Set<String> phoneNumbers = sharedPreferences.getStringSet("phone_numbers", new HashSet<>());

        // Show or hide the empty list text view depending on whether there are phone numbers to show
        if (phoneNumbers.isEmpty()) {

            emptyListText.setVisibility(View.VISIBLE);

        } else {

            emptyListText.setVisibility(View.GONE);

        }

        // Initialize the RecyclerView and set the adapter
        phoneNumberList.setLayoutManager(new LinearLayoutManager(this));
        phoneNumberList.setAdapter(new PhoneNumberAdapter(new ArrayList<>(phoneNumbers)));

    }

    /**
     * Method to initialize the views
     */
    private void findViews() {

        phoneNumberList = findViewById(R.id.recyclerViewCallList);
        emptyListText = findViewById(R.id.empty_list_text);

    }

    // This method is called to inflate the menu layout in the AppBar.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.call_list_menu, menu);
        return true;
    }

    // This method is called when a menu item in the AppBar is selected.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Get the id
        int id = item.getItemId();

        // If the "Delete all numbers" menu item is selected, remove all numbers from SharedPreferences and display a toast
        if (id == R.id.call_list_menu_delete_all_numbers) {

            // Get the SharedPreferences object
            SharedPreferences sharedPreferences = getSharedPreferences("phone_numbers", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // Remove the phone numbers from the set
            editor.remove("phone_numbers");
            editor.apply();

            // Notify the user that the numbers have been deleted
            Toast.makeText(this, getString(R.string.toast_text_numbers_deleted), Toast.LENGTH_SHORT).show();

            // Reload the screen
            reload();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Reload the screen
     */
    private void reload() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    /**
     * This is an inner class that extends RecyclerView.Adapter to bind the data set
     * (a list of phone numbers) to the RecyclerView
     * */
    private static class PhoneNumberAdapter extends RecyclerView.Adapter<PhoneNumberAdapter.ViewHolder> {

        // Stores the numbers
        private final List<String> phoneNumbers;

        // Constructor to initialize the phone numbers list
        public PhoneNumberAdapter(List<String> phoneNumbers) {
            this.phoneNumbers = phoneNumbers;
        }

        // onCreateViewHolder() is called when a new ViewHolder is created
        // Inflates a layout from android.R.layout.simple_list_item_1 for each item
        // in the data set and returns a ViewHolder instance
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }


        // onBindViewHolder() is called to update the contents of a ViewHolder
        // Sets the phone number at the current position in the data set to the
        // text view in the ViewHolder
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.textView.setText(phoneNumbers.get(position));

        }

        // getItemCount() is called to get the number of items in the data set
        @Override
        public int getItemCount() {

            return phoneNumbers.size();

        }

        // This is an inner class that extends RecyclerView.ViewHolder and represents
        // the views of an item in the RecyclerView
        public class ViewHolder extends RecyclerView.ViewHolder {

            public final TextView textView;

            public ViewHolder(View view) {
                super(view);
                textView = view.findViewById(android.R.id.text1);
            }
        }
    }
}