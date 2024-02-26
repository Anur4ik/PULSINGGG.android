package com.example.pulsinggg;

import java.util.ArrayList;
import java.util.List;

public class ListItem1 {
    private String pulse;
    private String date;
    private String time;
    private ArrayList<String> Pulses;
    private ArrayList<String> Data;
    private ArrayList<String> B_Time;

    public ArrayList<String> getB_Time() {
        return B_Time;
    }

    public void setB_Time(ArrayList<String> b_Time) {
        B_Time = b_Time;
    }

    public ArrayList<String> getPulses() {
        return Pulses;
    }

    public void setPulses(ArrayList<String> pulses) {
        Pulses = pulses;
    }

    public ArrayList<String> getData() {
        return Data;
    }

    public void setData(ArrayList<String> data) {
        Data = data;
    }


    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}