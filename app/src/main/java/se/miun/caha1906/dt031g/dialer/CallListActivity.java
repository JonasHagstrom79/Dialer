package se.miun.caha1906.dt031g.dialer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CallListActivity extends AppCompatActivity {

    private RecyclerView phoneNumberList;

    private TextView emptyListText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_list);

        // Get the views
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
        //phoneNumberList = findViewById(R.id.recyclerViewCallList);
        phoneNumberList.setLayoutManager(new LinearLayoutManager(this));
        phoneNumberList.setAdapter(new PhoneNumberAdapter(new ArrayList<>(phoneNumbers)));

    }

    // Get the views
    private void findViews() {

        phoneNumberList = findViewById(R.id.recyclerViewCallList);
        emptyListText = findViewById(R.id.empty_list_text);

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