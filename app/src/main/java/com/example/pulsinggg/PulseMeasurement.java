package com.example.pulsinggg;


import android.os.Parcel;
import android.os.Parcelable;

public class PulseMeasurement implements Parcelable {
    private String pulse;
    private String date;

    public PulseMeasurement(String pulse, String date) {
        this.pulse = pulse;
        this.date = date;
    }

    protected PulseMeasurement(Parcel in) {
        pulse = in.readString();
        date = in.readString();
    }

    public static final Creator<PulseMeasurement> CREATOR = new Creator<PulseMeasurement>() {
        @Override
        public PulseMeasurement createFromParcel(Parcel in) {
            return new PulseMeasurement(in);
        }

        @Override
        public PulseMeasurement[] newArray(int size) {
            return new PulseMeasurement[size];
        }
    };

    public String getPulse() {
        return pulse;
    }

    public String getDate() {
        return date;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pulse);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}