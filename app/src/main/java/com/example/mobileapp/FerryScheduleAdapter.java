package com.example.mobileapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FerryScheduleAdapter extends ArrayAdapter<FerrySchedule> {

    public FerryScheduleAdapter(Context context, List<FerrySchedule> ferrySchedules) {
        super(context, 0, ferrySchedules);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FerrySchedule schedule = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ferry_schedule_list_item, parent, false);
        }

        TextView routeTextView = convertView.findViewById(R.id.routeTextView);
        TextView departTimeTextView = convertView.findViewById(R.id.departTimeTextView);
        TextView arriveTimeTextView = convertView.findViewById(R.id.arriveTimeTextView);

        routeTextView.setText(schedule.getRoute());
        departTimeTextView.setText("Depart: " + schedule.getDepartTime());
        arriveTimeTextView.setText("Arrive: " + schedule.getArriveTime());

        return convertView;
    }
}