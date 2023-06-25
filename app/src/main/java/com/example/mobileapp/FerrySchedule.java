package com.example.mobileapp;

public class FerrySchedule {
    private String route;
    private String departTime;
    private String arriveTime;

    public String getRoute() { return route; }
    public void setRoute(String route) { this.route = route; }

    public String getDepartTime() { return departTime; }
    public void setDepartTime(String departTime) { this.departTime = departTime; }

    public String getArriveTime() { return arriveTime; }
    public void setArriveTime(String arriveTime) { this.arriveTime = arriveTime; }
}