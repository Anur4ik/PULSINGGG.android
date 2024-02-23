package com.example.pulsinggg.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.WindowManager;

import java.io.IOException;

public class ConnectThreed extends Thread {
    private Context context;
    private BluetoothAdapter btadapt;
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private ReceiveThread receiveThread;
    public static final String UUID = "00001101-0000-1000-8000-00805F9B34FB";

    public ConnectThreed(Context context, BluetoothAdapter btadapt, BluetoothDevice device) {
        this.context = context;
        this.btadapt = btadapt;
        this.device = device;
        try {
            socket = device.createRfcommSocketToServiceRecord(java.util.UUID.fromString(UUID));
        } catch (IOException e) {
        }
    }

    public void run() {
        btadapt.cancelDiscovery();
        try {
            socket.connect();

            receiveThread=new ReceiveThread(socket);
            receiveThread.start();
            Log.d("Mylog", " Conect");
        } catch (IOException e) {
            Log.d("Mylog", "Not Conect");
            closeConnection();
        }

    }



    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException y) {
        }
    }
    //НЕ РАБОТАЕТ
    public ReceiveThread getReceiveThread() {
        return receiveThread;
    }
}





























