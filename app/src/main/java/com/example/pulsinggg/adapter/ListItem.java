package com.example.pulsinggg.adapter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.util.Set;

public class ListItem {
    private  String btName;
    private  String btMac;


    public void setBtName(String btName) {
        this.btName = btName;
    }

    public void setBtMac(String btMac) {
        this.btMac = btMac;
    }

    public String getBtName() {
        return btName;
    }

    public String getBtMac() {
        return btMac;
    }



}
