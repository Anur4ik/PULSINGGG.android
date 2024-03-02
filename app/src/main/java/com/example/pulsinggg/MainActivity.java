package com.example.pulsinggg;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import android.Manifest;

import com.example.pulsinggg.adapter.BtConst;
import com.example.pulsinggg.bluetooth.Connection;
import com.example.pulsinggg.bluetooth.ReceiveThread;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private final int ENABLE_REQUEST = 15;
    private BluetoothAdapter blueAdapt;
    private Connection connection;
    private  ReceiveThread receiveThread;
    ConstraintLayout lay;
    ImageButton Button_heart;
    EditText numbe;
    ProgressBar ProgressBar2;
    ImageView Blutooth1;
    TextView Pulse_data;
    TextView data;
    TextView Time;
    TextView static1;
    String Number_Pulse = "0";

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

        checkBluetoothPermissions();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numbe=findViewById(R.id.Number);
        lay = findViewById(R.id.lay);
        Button_heart = findViewById(R.id.Button_heart);
        Pulse_data = findViewById(R.id.Pulse_data);
        data = findViewById(R.id.data);
        static1= findViewById(R.id.statis1);
        Time = findViewById(R.id.Time);
        Blutooth1 = findViewById(R.id.Blutooth1);
        ProgressBar2 = findViewById(R.id.ProgressBar2);
        lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection.sendMasenger("A");
                Log.d("Mylog", "Message sent: A");
            }
        });
        init();
        init_list();
        setBtIcon();
        init_p();

    }
    protected void onResume() {
        super.onResume();

        // Ensure that the ArrayLists are initialized before clearing them
        if (Pulse != null) {
            Pulse.clear();
        } else {
            Pulse = new ArrayList<>();
        }
        if (Data != null) {
            Data.clear();
        } else {
            Data = new ArrayList<>();
        }
        if (time != null) {
            time.clear();
        } else {
            time = new ArrayList<>();
        }
    }
    private SharedPreferences pref;
    private SharedPreferences DATE;
    private  void init(){
        blueAdapt = BluetoothAdapter.getDefaultAdapter();
        pref=getSharedPreferences(BtConst.MY_PREF,Context.MODE_PRIVATE);
        connection=new Connection(this);

    }
private void init_list(){
    Pulse = new ArrayList<>();
    Data = new ArrayList<>();
    time = new ArrayList<>();
}


    private void setBtIcon() {
        if (blueAdapt.isEnabled()) {
            Blutooth1.setImageResource(R.drawable.bt_on);
        } else {
            Blutooth1.setImageResource(R.drawable.bt_dith);
        }
    }

    private void checkBluetoothPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH)
                != PackageManager.PERMISSION_GRANTED) {
            // Разрешение не предоставлено, запросите его
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.BLUETOOTH},
                    REQUEST_ENABLE_BT);
        }
    }
    private ArrayList<String> Pulse;
    int Durabli;
    private ArrayList<String> Data;
    private ArrayList<String> time;
//астройка нажатия сердца
    public void Press_heart(View view) {
        view.setEnabled(false);
        connection.sendMasenger("1");//отправляю st
        final ColorStateList originalColor = Button_heart.getImageTintList();
        Button_heart.setImageTintList(ColorStateList.valueOf(Color.BLACK));
        ProgressBar2.setVisibility(View.VISIBLE);
        Pulse_data = findViewById(R.id.Pulse_data);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ListItem1 list=new ListItem1();
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
                String text=connection.readMasenger();
                if(!text.equals("ТИ ЛОХ"))
                { Number_Pulse = text;}
                else {
                    Number_Pulse = String.valueOf(randomNumber);
                }
                Pulse_data.setText(Number_Pulse);
                ////////////////////////////////
                Pulse.add(Number_Pulse);
                Data.add(dateText);
                time.add(timeText);
                ////////////////////////////////
                list.setPulses(Pulse);
                list.setData(Data);
                list.setB_Time(time);
                // Зберегти пульс, дату та час у SharedPreferences
                DATE = getSharedPreferences("my_pulse_data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = DATE.edit();
                editor.putString(Sava_Pulse.Pulse, Number_Pulse);
                editor.putString(Sava_Pulse.SData, dateText);
                editor.putString(Sava_Pulse.STime, timeText);
                editor.apply();
                view.setEnabled(true);
            }
        }, Durabli);
    }

    private void init_p() {
        DATE = getSharedPreferences("my_pulse_data", Context.MODE_PRIVATE);
        Pulse_data.setText(DATE.getString(Sava_Pulse.Pulse,""));
        data.setText(DATE.getString(Sava_Pulse.SData,""));
        Time.setText(DATE.getString(Sava_Pulse.STime,""));
    }

    //Тест при нажатия должен отправить текст
    public void test(View v){
        Durabli=Integer.parseInt(numbe.getText().toString());
        connection.sendMasenger("s1t");//отправляю st
    }
    public void test1(View v){
        String text=connection.readMasenger();
        static1.setText(text);
    }


    public void Blutooth_Click(View view) {
        if (blueAdapt == null) {
            Toast.makeText(this, "Bluetooth не підтримує", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!blueAdapt.isEnabled()) {
            // Если Bluetooth выключен, включаем его
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            bluetoothEnableLauncher.launch(enableBtIntent);
        } else {
            Toast.makeText(this,"Мені лінь вимикать", Toast.LENGTH_SHORT).show();
        }
    }

    public void Blutoth_Menu(View view) {
        if(blueAdapt.isEnabled()){
            Intent open = new Intent(MainActivity.this, Option_Blu.class);
            startActivity(open);

        }
        else {
            Toast.makeText(this, "Ввімкни Bluetooth NIGGER", Toast.LENGTH_SHORT).show();
        }}

    public void Bluetooth_Connection(View v){connection.connect();

    }
    //Перейти в скрол
    public void NextPage(View v) {
        Intent intent = new Intent(this,history_page.class);
        intent.putExtra("pulseData", Pulse);
        intent.putExtra("dateData", Data);
        intent.putExtra("timeData", time);
        startActivity(intent);
    }
}

