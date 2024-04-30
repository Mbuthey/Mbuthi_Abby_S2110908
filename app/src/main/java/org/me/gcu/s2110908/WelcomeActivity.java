package org.me.gcu.s2110908;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.me.gcu.s2110908.adapter.WelcomeAdapter;
import org.me.gcu.s2110908.models.WelcomeItemModel;

import java.util.ArrayList;
import java.util.Locale;

public class WelcomeActivity extends AppCompatActivity {

    private ListView listView;
    private EditText searchEditText;
    private ArrayList<WelcomeItemModel> originalWelcomeItems;
    private ArrayList<WelcomeItemModel> filteredWelcomeItems;
    private FloatingActionButton fab;
    private WelcomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

        listView = findViewById(R.id.cityList);
        searchEditText = findViewById(R.id.searchCity);
        fab = findViewById(R.id.fabMap);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        originalWelcomeItems = new ArrayList<>();
        filteredWelcomeItems = new ArrayList<>();

        // Add the welcome items
        originalWelcomeItems.add(new WelcomeItemModel("Glasgow", "Cloudy", "12°C", "High: 15°C, Low: 10°C"));
        originalWelcomeItems.add(new WelcomeItemModel("Mauritius", "Sunny", "14°C", "High: 16°C, Low: 12°C"));
        originalWelcomeItems.add(new WelcomeItemModel("Oman", "Rainy", "10°C", "High: 12°C, Low: 8°C"));
        originalWelcomeItems.add(new WelcomeItemModel("Bangladesh", "Snowy", "5°C", "High: 8°C, Low: 2°C"));
        originalWelcomeItems.add(new WelcomeItemModel("London", "Sunny", "15°C", "High: 18°C, Low: 12°C"));
        originalWelcomeItems.add(new WelcomeItemModel("New York", "Cloudy", "10°C", "High: 12°C, Low: 8°C"));

        // Initially, set the filtered list to the original list
        filteredWelcomeItems.addAll(originalWelcomeItems);

        // Set the adapter for the list view
        adapter = new WelcomeAdapter(filteredWelcomeItems, this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WelcomeItemModel selectedItem = filteredWelcomeItems.get(position);
                String selectedLocation = selectedItem.getLocation();

                Intent intent = new Intent(WelcomeActivity.this, MainActivity2.class);
                intent.putExtra("selectedLocation", selectedLocation);
                startActivity(intent);
            }
        });

        // Set the search listener for the search edit text
        searchEditText.addTextChangedListener(new SearchTextWatcher());
    }

    private class SearchTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Not needed for this implementation
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Filter the list view based on the search text
            String searchText = charSequence.toString().toLowerCase(Locale.ROOT);
            filteredWelcomeItems.clear();

            if (searchText.isEmpty()) {
                // If the search text is empty, show the original list
                filteredWelcomeItems.addAll(originalWelcomeItems);
            } else {
                // Filter the list based on the search text
                for (WelcomeItemModel item : originalWelcomeItems) {
                    if (item.getLocation().toLowerCase(Locale.ROOT).contains(searchText)) {
                        filteredWelcomeItems.add(item);
                    }
                }
            }

            // Notify the adapter that the data has changed
            adapter.notifyDataSetChanged();
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // Not needed for this implementation
        }
    }
}