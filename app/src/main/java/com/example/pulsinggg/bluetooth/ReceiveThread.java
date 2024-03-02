package com.example.pulsinggg.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ReceiveThread extends Thread{
        private Context context;
    private BluetoothSocket socket; // Об'єкт BluetoothSocket для зв'язку
    private InputStream inputS; // InputStream для читання даних
    private OutputStream outputS; // OutputStream для запису даних
    private byte[] rBuffer; // Байтовий масив буфера для отриманих даних
    String message ;

    // Конструктор для ініціалізації ReceiveThread з BluetoothSocket
    public ReceiveThread(BluetoothSocket socket) {
        this.socket = socket;
        try {
            inputS = socket.getInputStream(); // Отримати InputStream з сокету
        } catch (IOException e) {
            // Обробити IOException, якщо не вдається отримати InputStream
        }
        try {
            outputS = socket.getOutputStream(); // Отримати OutputStream з сокету
        } catch (IOException e) {
            // Обробити IOException, якщо не вдається отримати OutputStream
        }
    }

    // Метод для читання повідомлень з InputStream Bluetooth-сокету


    // Перевизначити метод run класу Thread
    @Override
    public void run() {
        rBuffer = new byte[10]; // Ініціалізувати буфер розміром 10
        while (true) {
            try {
                int size = inputS.read(rBuffer); // Прочитати дані в буфер
                message = new String(rBuffer, 0, size); // Конвертувати байти в рядок
                Log.d("MyLog", "Message: " + message); // Зареєструвати отримане повідомлення

            } catch (IOException e) {
                break;
            }
        }

         // Викликати метод readMesenger при старті потоку
    }

    // Метод для відправлення повідомлень через OutputStream Bluetooth-сокету
    public void sendMesenger(byte[] byteArray) {
        try {
            outputS.write(byteArray); // Записати байтовий масив у OutputStream
        } catch (IOException e) {
            // Обробити IOException, якщо виникає помилка під час запису
        }
    }
}








