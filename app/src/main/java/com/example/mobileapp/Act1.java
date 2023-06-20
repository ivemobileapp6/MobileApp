package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Act1 extends AppCompatActivity {

    private Spinner routesSpinner;
    private Spinner travelerTypeSpinner;
    private Spinner dayTypeSpinner;
    private Button calculateFareButton;
    private TextView fareResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act1);

        routesSpinner = findViewById(R.id.routesSpinner);
        travelerTypeSpinner = findViewById(R.id.travelerTypeSpinner);
        dayTypeSpinner = findViewById(R.id.dayTypeSpinner);
        calculateFareButton = findViewById(R.id.calculateFareButton);
        fareResultTextView = findViewById(R.id.fareResultTextView);

        ArrayAdapter<CharSequence> routesAdapter = ArrayAdapter.createFromResource(this, R.array.routes, android.R.layout.simple_spinner_item);
        routesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        routesSpinner.setAdapter(routesAdapter);

        ArrayAdapter<CharSequence> travelerTypeAdapter = ArrayAdapter.createFromResource(this, R.array.traveler_types, android.R.layout.simple_spinner_item);
        travelerTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        travelerTypeSpinner.setAdapter(travelerTypeAdapter);

        ArrayAdapter<CharSequence> dayTypeAdapter = ArrayAdapter.createFromResource(this, R.array.day_types, android.R.layout.simple_spinner_item);
        dayTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayTypeSpinner.setAdapter(dayTypeAdapter);

        calculateFareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateFare();
            }
        });
    }

    private void calculateFare() {
        int routeIndex = routesSpinner.getSelectedItemPosition();
        int travelerTypeIndex = travelerTypeSpinner.getSelectedItemPosition();
        int dayTypeIndex = dayTypeSpinner.getSelectedItemPosition();

        double[][][] fares = {
                {
                        {5.0, 4.0, 6.5, 5.6},
                        {2.9, 2.8, 3.9, 3.7},
                        {2.9, 2.8, 3.9, 3.7},
                        {2.9, 2.8, 3.9, 3.7}
                },
                {
                        {5.0, 6.5},
                        {2.9, 3.9},
                        {2.9, 3.9},
                        {2.9, 3.9}
                }
        };

        double fare = 0.0;
        if (routeIndex == 0) {
            fare = fares[0][travelerTypeIndex][dayTypeIndex * 2];
        } else if (routeIndex == 1) {
            fare = fares[1][travelerTypeIndex][dayTypeIndex];
        }

        fareResultTextView.setText(String.format("Fare: $%.1f", fare));
    }
}