package com.example.pulsinggg;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pulsinggg.adapter.ListItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class history_page extends AppCompatActivity {

    private ListView listView;
    private MyListAdapter adapter;
    private ArrayList<String> Pulse;
    private ArrayList<String> Data;
    private ArrayList<String> time;
    private ArrayList<ListItem1> list1; // Move this declaration here

    // Имя файла SharedPreferences для сохранения данных списка
    private static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        // Получаем переданные данные из MainActivity
        Intent intent = getIntent();
        Pulse = intent.getStringArrayListExtra("pulseData");
        Data = intent.getStringArrayListExtra("dateData");
        time = intent.getStringArrayListExtra("timeData");

        listView = findViewById(R.id.List);
        list1 = new ArrayList<>(); // Initialize here

        // Загружаем сохраненные данные из SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int size = prefs.getInt("list_size", 0);
        for (int i = 0; i < size; i++) {
            String pulse = prefs.getString("pulse_" + i, "");
            String date = prefs.getString("date_" + i, "");
            String time = prefs.getString("time_" + i, "");
            ListItem1 item = new ListItem1();
            item.setPulse(pulse);
            item.setDate(date);
            item.setTime(time);
            list1.add(item);
        }

        // Проверяем, что списки данных не пустые
        if (Pulse != null && Data != null && time != null) {
            // Создаем ListItem1 для каждой записи и добавляем их в список,
            // если они еще не были добавлены
            for (int i = 0; i < Pulse.size(); i++) {
                ListItem1 newItem = new ListItem1();
                newItem.setPulse(Pulse.get(i));
                newItem.setDate(Data.get(i));
                newItem.setTime(time.get(i));

                // Проверяем, что элемент еще не добавлен
                if (!list1.contains(newItem)) {
                    list1.add(newItem);
                }
            }
        }

        // Инициализируем адаптер и устанавливаем его для ListView
        adapter = new MyListAdapter(this, R.layout.hs_list, list1);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Сохраняем данные списка в SharedPreferences при закрытии активности
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt("list_size", adapter.getCount());
        for (int i = 0; i < adapter.getCount(); i++) {
            ListItem1 item = adapter.getItem(i);
            editor.putString("pulse_" + i, item.getPulse());
            editor.putString("date_" + i, item.getDate());
            editor.putString("time_" + i, item.getTime());
        }
        editor.apply();
    }

    public void left_hist(View v) {
        finish();
    }

    public void clear(View v) {
        Pulse.clear();
        Data.clear();
        time.clear();
        list1.clear();

        // Обновляем адаптер, чтобы отобразить изменения на экране
        adapter.notifyDataSetChanged();
    }
}