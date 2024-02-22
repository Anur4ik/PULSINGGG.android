package com.example.pulsinggg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ListView;

import com.example.pulsinggg.adapter.BtAdapter;
import com.example.pulsinggg.adapter.ListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Option_Blu extends AppCompatActivity {
    private ListView listView;
    private List<ListItem> list;
    private BtAdapter adapter;
    private BluetoothAdapter blueAdapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_blu);
        init();
    }

    private void init() {
        blueAdapt = BluetoothAdapter.getDefaultAdapter();
        list = new ArrayList<>(); // Инициализация списка здесь
        listView=findViewById(R.id.List);
        adapter = new BtAdapter(this, R.layout.bt_listitem, list);
        listView.setAdapter(adapter);
        getPairedDevices();
    }

    private void getPairedDevices() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Set<BluetoothDevice> pairedDevices = blueAdapt.getBondedDevices();

        if (pairedDevices.size() > 0) {
            list.clear();
            for (BluetoothDevice device : pairedDevices) {
                ListItem item = new ListItem();
                item.setBtName(device.getName());
                item.setBtMac(device.getAddress()); // MAC address
                list.add(item);
            }
            adapter.notifyDataSetChanged();
        }
    }
}