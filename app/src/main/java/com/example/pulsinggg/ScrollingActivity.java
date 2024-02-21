package com.example.pulsinggg;

import static com.example.pulsinggg.MainActivity.SHARED_PREFS_FILE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;

public class ScrollingActivity extends MainActivity {
    TextView Datee;
    ArrayList<String> previousNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        Datee = findViewById(R.id.Datee);
        previousNumbers = getIntent().getStringArrayListExtra("previous_numbers");
        if (previousNumbers != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String number : previousNumbers) {
                stringBuilder.append(number).append("\n");
            }
            Datee.setText(stringBuilder.toString());
        }
    }

    // Обработчик клика для кнопки Clear
    public void clearPreviousNumbers(View view) {
        // Очищаем массив предыдущих чисел
        previousNumbers.clear();

        // Очищаем текстовое поле
        Datee.setText("ДИМОН И ЛЬОХА ЛОШАРИИИИИ ");

        // Создаем новый Intent для возвращения обновленного массива в MainActivity
        Intent resultIntent = new Intent();
        resultIntent.putStringArrayListExtra("cleared_numbers", previousNumbers);
        setResult(Activity.RESULT_OK, resultIntent);
        finish(); // Завершаем активность ScrollingActivity
    }
}