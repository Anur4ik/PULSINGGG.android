package com.example.pulsinggg.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;

public class ConnectThreed extends Thread {
    private Context context;// Контекст додатка
    private BluetoothAdapter btadapt;// Адаптер Bluetooth
    private BluetoothDevice device;// Пристрій Bluetooth
    private BluetoothSocket socket; // Сокет Bluetooth
    private ReceiveThread receiveThread;// Потік для отримання даних
    public static final String UUID = "00001101-0000-1000-8000-00805F9B34FB";// UUID для з'єднання Bluetooth
// Конструктор для ініціалізації об'єкту ConnectThreed з контекстом, адаптером та пристроєм Bluetooth
    public ConnectThreed(Context context, BluetoothAdapter btadapt, BluetoothDevice device) {
        this.context = context;
        this.btadapt = btadapt;
        this.device = device;
        try {
            socket = device.createRfcommSocketToServiceRecord(java.util.UUID.fromString(UUID));// Створення Bluetooth сокету за UUID
        } catch (IOException e) {
        }
    }
    // Метод run, який буде виконуватись при запуску потоку
    public void run() {
        btadapt.cancelDiscovery();// Відміна пошуку нових пристроїв Bluetooth
        try {
            socket.connect();// Встановлення з'єднання через сокет
            receiveThread=new ReceiveThread(socket);// Створення потоку для отримання даних
            receiveThread.start(); // Запуск потоку для отримання даних
            Log.d("Mylog", " Conect");
            showUIToast("Подключение успешно выполнено");
        } catch (IOException e) {
            Log.d("Mylog", "Not Conect");
            showUIToast("Подключение не выполнено");
            closeConnection();// Закрити з'єднання
        }


    }   private void showUIToast(final String message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Метод для закриття з'єднання Bluetooth
    public void closeConnection() {
        try {
            socket.close();// Закрити сокет
        } catch (IOException y) {
        }
    }
    // Метод, який повертає потік для отримання даних
    public ReceiveThread getReceiveThread() {
        return receiveThread; // Повернути потік для отримання даних
    }
}





























