package com.example.pulsinggg.adapter;

import android.bluetooth.BluetoothDevice;

public class ListItem {
    private BluetoothDevice btDevice;

    public BluetoothDevice getBtDevice() {
        return btDevice;
    }

    public void setBtDevice(BluetoothDevice btDevice) {
        this.btDevice = btDevice;
    }

    private  String itemType=BtAdapter.DEF_ITEM_TYPE;

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }





}
