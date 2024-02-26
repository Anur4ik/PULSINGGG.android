package com.example.pulsinggg.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ReceiveThread extends Thread {
    private BluetoothSocket socket; // Об'єкт BluetoothSocket для зв'язку
    private InputStream inputS; // InputStream для читання даних
    private OutputStream outputS; // OutputStream для запису даних
    private byte[] rBuffer; // Байтовий масив буфера для отриманих даних

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
    public String readMesenger() {
        rBuffer = new byte[10]; // Ініціалізувати буфер розміром 10
        while (true) {
            try {
                int size = inputS.read(rBuffer); // Прочитати дані в буфер
                String message = new String(rBuffer, 0, size); // Конвертувати байти в рядок
                Log.d("MyLog", "Message: " + message); // Зареєструвати отримане повідомлення
                return message; // Повернути отримане повідомлення
            } catch (IOException e) {
                // Обробити IOException, якщо виникає помилка під час читання
                break;
            }
        }
        return null; // Повернути null, якщо повідомлення не отримано
    }

    // Перевизначити метод run класу Thread
    @Override
    public void run() {
        readMesenger(); // Викликати метод readMesenger при старті потоку
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








