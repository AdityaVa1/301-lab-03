package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

// 1. Implement the EditCityFragment's listener interface
public class MainActivity extends AppCompatActivity implements
        AddCityFragment.AddCityDialogListener,
        EditCityFragment.EditCityDialogListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter; // Using your custom adapter name

    // This is the callback method from AddCityFragment
    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    // 2. This is the new callback method from EditCityFragment
    @Override
    public void onCityEdited() {
        // The data in the list has already been changed,
        // so we just need to tell the adapter to refresh its views.
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = { "Edmonton", "Vancouver", "Toronto" };
        String[] provinces = { "AB", "BC", "ON" };
        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        // Your existing FAB for adding cities remains the same
        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            new AddCityFragment().show(getSupportFragmentManager(), "Add City");
        });

        // 3. Add an OnItemClickListener to the ListView to handle edits
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            // Get the city object that was tapped
            City cityToEdit = dataList.get(position);

            // Create an instance of the EditCityFragment and pass the city to it
            EditCityFragment editFragment = EditCityFragment.newInstance(cityToEdit);
            editFragment.show(getSupportFragmentManager(), "EDIT_CITY");
        });
    }
}