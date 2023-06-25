package com.example.mobileapp;

import java.util.List;

public class JsonResponse {
    private String status;
    private List<FerrySchedule> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<FerrySchedule> getData() {
        return data;
    }

    public void setData(List<FerrySchedule> data) {
        this.data = data;
    }
}
