package com.example.pulsinggg.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.pulsinggg.adapter.BtConst;

public class Connection {
    private Context context; // Контекст додатка
    private SharedPreferences pref; // Об'єкт SharedPreferences для збереження даних
    private BluetoothAdapter btadapt; // Адаптер Bluetooth
    private BluetoothDevice device; // Пристрій Bluetooth
    private ConnectThreed connectThreed; // Потік для з'єднання Bluetooth
    // Конструктор класу Connection, ініціалізує контекст додатка та інші необхідні змінні
    public Connection(Context context) {
        this.context = context;
        pref=context.getSharedPreferences(BtConst.MY_PREF,Context.MODE_PRIVATE);
        btadapt=BluetoothAdapter.getDefaultAdapter();
    }
    // Метод для встановлення з'єднання Bluetooth
    public void  connect()
    {
        String mac=pref.getString(BtConst.MAC_KEY,"");// Отримати MAC-адресу зі збережених налаштувань
        if(!btadapt.isEnabled()||mac.isEmpty())return;// Перевірити, чи Bluetooth включений і MAC-адреса не порожня
        device=btadapt.getRemoteDevice(mac);// Отримати пристрій Bluetooth за MAC-адресою
        if(device==null)return;// Перевірити, чи пристрій знайдено
        ConnectThreed connectThreed=new ConnectThreed(context,btadapt,device);// Створити новий потік для з'єднання
        connectThreed.start();// Запустити потік для встановлення з'єднання

    }
    // Метод для відправлення повідомлення через з'єднання Bluetooth
    public void sendMasenger(String message){
        if (connectThreed != null) {// Перевірити, чи існує активне з'єднання
            connectThreed.getReceiveThread().sendMesenger(message.getBytes());// Відправити повідомлення через активне з'єднання
        }}
    // Не працює
    // Метод для читання отриманого повідомлення через з'єднання Bluetooth
    public String readMasenger(){
        if (connectThreed != null && connectThreed.getReceiveThread() != null) {// Перевірити, чи існує активне з'єднання та потік для отримання даних
            return connectThreed.getReceiveThread().readMesenger();// Повернути отримане повідомлення через активне з'єднання
        }
        return "ТИ ЛОХ";//  Повернути null у випадку відсутності активного з'єднання або прочитання повідомлення
    }
    }

