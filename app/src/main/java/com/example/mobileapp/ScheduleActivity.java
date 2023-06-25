package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        listView = findViewById(R.id.listView);
        fetchFerrySchedules();
    }

    private void fetchFerrySchedules() {
        FerryScheduleClient.fetchFerrySchedule(this, new FerryScheduleClient.FerryScheduleCallback() {
            @Override
            public void onSuccess(List<FerrySchedule> ferrySchedules) {
                FerryScheduleAdapter adapter = new FerryScheduleAdapter(ScheduleActivity.this, ferrySchedules);
                listView.setAdapter(adapter);
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(ScheduleActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}