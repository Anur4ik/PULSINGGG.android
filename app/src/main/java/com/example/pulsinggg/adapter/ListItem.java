package com.example.pulsinggg.adapter;

import android.bluetooth.BluetoothDevice;

public class ListItem {
    private BluetoothDevice btDevice; // Пристрій Bluetooth
    // Метод для отримання пристрою Bluetooth
    public BluetoothDevice getBtDevice() {
        return btDevice;
    }
    // Метод для встановлення пристрою Bluetooth
    public void setBtDevice(BluetoothDevice btDevice) {
        this.btDevice = btDevice;
    }

    private  String itemType=BtAdapter.DEF_ITEM_TYPE;// Тип елемента списку, за замовчуванням
    // Метод для отримання типу елемента списку
    public String getItemType() {
        return itemType;
    }

    // Метод для встановлення типу елемента списку
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }


}
