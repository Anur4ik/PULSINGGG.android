package com.example.pulsinggg.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

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
            connectThreed.getReceiveThread().sendMesenger(message.getBytes());
            showUIToast("Message sent: " + message); // Відправити повідомлення через активне з'єднання
        }else{
            showUIToast("Текст не відправлено: " + message);
        }}

    private void showUIToast(final String message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Не працює
    // Метод для читання отриманого повідомлення через з'єднання Bluetooth
    public String readMasenger(){
        if (connectThreed != null) {// Перевірити, чи існує активне з'єднання та потік для отримання даних
            showUIToast("СЮДААА ПРОЧИтав: ");
            return connectThreed.getReceiveThread().message;// Повернути отримане повідомлення через активне з'єднання

        }
        showUIToast("СУКАААА НЕ ПРОЧИТАВ ");
        return "ТИ ЛОХ";//  Повернути null у випадку відсутності активного з'єднання або прочитання повідомлення

    }
    }

