package com.example.pulsinggg;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final int ENABLE_REQUEST = 15;
    private BluetoothAdapter blueAdapt;
    ConstraintLayout lay;
    ImageButton Button_heart;
    ProgressBar ProgressBar2;
    ImageView Blutooth1;
    TextView Pulse_data;
    TextView data;
    TextView Time;
    String now_number = "0";
    private static final String KEY_NUMBER = "number_key";
    private static final String KEY_DATA = "data_key";
    private static final String KEY_TIME = "time_key";
    public static final String SHARED_PREFS_FILE = "MyAppSharedPrefs";
    // Массив для хранения предыдущих чисел пульса
    public static ArrayList<String> previousNumbersList = new ArrayList<>();

    private final ActivityResultLauncher<Intent> bluetoothEnableLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Bluetooth включен успешно
                    Toast.makeText(this, "Bluetooth включен", Toast.LENGTH_SHORT).show();
                } else {
                    // Пользователь отменил включение Bluetooth
                    Toast.makeText(this, "Включение Bluetooth отменено", Toast.LENGTH_SHORT).show();
                }
                // Обновляем иконку Bluetooth
                setBtIcon();
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lay = findViewById(R.id.lay);
        Button_heart = findViewById(R.id.Button_heart);
        Pulse_data = findViewById(R.id.Pulse_data);
        data = findViewById(R.id.data);
        Time = findViewById(R.id.Time);
        Blutooth1 = findViewById(R.id.Blutooth1);
        ProgressBar2 = findViewById(R.id.ProgressBar2);

        // Инициализация массива для хранения предыдущих чисел пульса
        previousNumbersList = new ArrayList<>();

        // Инициализация BluetoothAdapter
        blueAdapt = BluetoothAdapter.getDefaultAdapter();

        // Загрузка сохраненных значений из SharedPreferences
        SharedPreferences sharedPrefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        now_number = sharedPrefs.getString(KEY_NUMBER, "0");
        Pulse_data.setText(now_number);
        data.setText(sharedPrefs.getString(KEY_DATA, "--.--.----"));
        Time.setText(sharedPrefs.getString(KEY_TIME, "--:--"));

        // Загрузка истории чисел пульса из SharedPreferences
        String previousNumbersString = sharedPrefs.getString("previous_numbers", "");
        if (!previousNumbersString.isEmpty()) {
            String[] previousNumbersArray = previousNumbersString.split(",");
            previousNumbersList.addAll(Arrays.asList(previousNumbersArray));
        }
        setBtIcon();
    }

    // Метод для установки иконки Bluetooth в зависимости от состояния
    private void setBtIcon() {
        if (blueAdapt.isEnabled()) {
            Blutooth1.setImageResource(R.drawable.bt_on);
        } else {
            Blutooth1.setImageResource(R.drawable.bt_dith);
        }
    }

    public void Blutoth_Menu(View view){
        Intent open = new Intent(MainActivity.this,Option_Blu.class);
        startActivity(open);
    }
    public void Blutooth_Click(View view) {
        if (blueAdapt == null) {
            // Устройство не поддерживает Bluetooth
            Toast.makeText(this, "Bluetooth не поддерживается на этом устройстве", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!blueAdapt.isEnabled()) {
            // Если Bluetooth выключен, включаем его
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            bluetoothEnableLauncher.launch(enableBtIntent);
        } else {
            // Если Bluetooth включен, выключаем его
            blueAdapt.disable();
            // Устанавливаем соответствующую иконку
            Blutooth1.setImageResource(R.drawable.bt_dith);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        // Очистка массива предыдущих чисел перед уничтожением активности
        previousNumbersList.clear();
    }

    public void Press_heart(View view) {
        final ColorStateList originalColor = Button_heart.getImageTintList();
        Button_heart.setImageTintList(ColorStateList.valueOf(Color.BLACK));
        ProgressBar2.setVisibility(View.VISIBLE);
        Pulse_data = findViewById(R.id.Pulse_data);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Date currentDate = new Date();
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                String dateText = dateFormat.format(currentDate);
                DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String timeText = timeFormat.format(currentDate);
                Random random = new Random();
                int randomNumber = random.nextInt(120);
                ProgressBar2.setVisibility(View.INVISIBLE);
                Button_heart.setImageTintList(originalColor);
                data.setText(dateText);
                Time.setText(timeText);
                now_number = String.valueOf(randomNumber);
                Pulse_data.setText(now_number);

                // Добавляем текущее число пульса в массив предыдущих чисел
                previousNumbersList.add(now_number);
            }
        }, 1000);
    }

    public void NextPage(View v) {
        // Сохраняем текущие значения перед переходом на следующую страницу
        SharedPreferences sharedPrefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(KEY_NUMBER, now_number);
        editor.putString(KEY_DATA, data.getText().toString());
        editor.putString(KEY_TIME, Time.getText().toString());
        editor.putString("previous_numbers", TextUtils.join(",", previousNumbersList)); // Сохраняем историю чисел пульса

        editor.apply();

        // Передаем массив предыдущих чисел в ScrollingActivity через Intent
        Intent intent = new Intent(this, ScrollingActivity.class);
        intent.putStringArrayListExtra("previous_numbers", previousNumbersList);
        startActivity(intent);
    }
}

