package com.example.mobileapp;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;

public class FerryScheduleClient {
    private static final String SCHEDULE_URL = "https://fileprojectwork.ivemobileapp6.repl.co/ferrytable";

    public interface FerryScheduleCallback {
        void onSuccess(List<FerrySchedule> ferrySchedules);
        void onError(String errorMessage);
    }

    public static void fetchFerrySchedule(Context context, FerryScheduleCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SCHEDULE_URL,
                response -> {
                    Gson gson = new Gson();
                    List<FerrySchedule> ferrySchedules = gson.fromJson(response, new TypeToken<List<FerrySchedule>>(){}.getType());
                    callback.onSuccess(ferrySchedules);
                },
                error -> callback.onError("Failed to fetch data"));

        queue.add(stringRequest);
    }
}