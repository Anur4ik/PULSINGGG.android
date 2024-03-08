package com.example.pulsinggg.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.pulsinggg.adapter.BtConst;

public class Connection {
    private SharedPreferences pref; // Об'єкт SharedPreferences для збереження даних
    private BluetoothAdapter btadapt; // Адаптер Bluetooth
    private BluetoothDevice device; // Пристрій Bluetooth
    private ConnectThreed connectThreed;// Потік для з'єднання Bluetooth
    // Конструктор класу Connection, ініціалізує контекст додатка та інші необхідні змінні
    public Connection(Context context) {

        pref=context.getSharedPreferences(BtConst.MY_PREF,Context.MODE_PRIVATE);
        btadapt=BluetoothAdapter.getDefaultAdapter();
    }
    // Метод для встановлення з'єднання Bluetooth
    public void connect() {
        String mac = pref.getString(BtConst.MAC_KEY, "");
        if (!btadapt.isEnabled() || mac.isEmpty()) return;
        device = btadapt.getRemoteDevice(mac);
        if (device == null) return;
        connectThreed = new ConnectThreed(btadapt, device); // Используйте поле класса connectThreed
        connectThreed.start();
    }
    // Метод для відправлення повідомлення через з'єднання Bluetooth
    public void sendMasenger(String message){
        if (connectThreed != null) {

            connectThreed.getReceiveThread().sendMesenger(message.getBytes());
        }
        }
    public String readMasenger() {
        // Перевірка на null перед викликом методу getReceiveThread()
        if (connectThreed != null) {
            return connectThreed.getReceiveThread().message;
        } else {
            // Обробка ситуації, коли connectThreed нульовий
            return "Лох";
        }
    }

    }

