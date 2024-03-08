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

    private BluetoothSocket socket; // Об'єкт BluetoothSocket для зв'язку
    private InputStream inputS; // InputStream для читання даних
    private OutputStream outputS; // OutputStream для запису даних
    private byte[] rBuffer; // Байтовий масив буфера для отриманих даних
    public String message; ;

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
        rBuffer = new byte[1024]; // Инициализировать буфер размером 1024 (можно выбрать больший размер, если ожидается больше данных)
        StringBuilder messageBuilder = new StringBuilder(); // Инициализировать StringBuilder для накопления данных

        while (true) {
            try {
                int bytesRead = inputS.read(rBuffer); // Прочитать данные в буфер
                if (bytesRead != -1) {
                    String receivedData = new String(rBuffer, 0, bytesRead); // Преобразовать прочитанные байты в строку
                    messageBuilder.append(receivedData); // Добавить прочитанные данные к StringBuilder
                } else {
                    break; // Прекратить цикл, если достигнут конец потока
                }

                // Проверить, содержит ли StringBuilder завершающий символ (например, '\n'), который указывает на конец сообщения
                int endIndex = messageBuilder.indexOf("\n");
                if (endIndex != -1) {
                    message = messageBuilder.substring(0, endIndex); // Извлечь сообщение до завершающего символа
                    messageBuilder.delete(0, endIndex + 1); // Удалить из StringBuilder обработанные данные, включая завершающий символ

                    Log.d("MyLog", "Message: " + message); // Зарегистрировать полученное сообщение

                    // Здесь вы можете обработать полученное сообщение, например, отправить его в главный поток для отображения на экране
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
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








