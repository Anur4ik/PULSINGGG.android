package com.example.pulsinggg;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.BLUETOOTH_SCAN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pulsinggg.adapter.BtAdapter;
import com.example.pulsinggg.adapter.ListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Option_Blu extends AppCompatActivity {
    private final int BT_REQUEST_PERM = 111;
    private ListView listView;
    private List<ListItem> list;
    private BtAdapter adapter;
    private BluetoothAdapter blueAdapt;
    private boolean isBtPermissionGranted = false;
    private boolean isDiscovery = false;
    private TextView ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_blu);
        ab = findViewById(R.id.textView3);
        init();
        getBtPermission();

    }

    public void resume_main(View v) {
        if (isDiscovery) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            blueAdapt.cancelDiscovery();
        isDiscovery=false;
        }
        else {
        finish();
        }
    }

    public boolean search(View v) {
        if(isDiscovery)return true;
        ab.setText(R.string.Discovery);
        list.clear();
        ListItem itemTitle = new ListItem();
        itemTitle.setItemType(BtAdapter.TITLE_ITEM_TYPE);
        list.add(itemTitle);
        adapter.notifyDataSetChanged();
        blueAdapt.startDiscovery();
        isDiscovery=true;

        return true;
    }

    private void init() {
        blueAdapt = BluetoothAdapter.getDefaultAdapter();
        list = new ArrayList<>(); // Инициализация списка здесь
        listView = findViewById(R.id.List);
        adapter = new BtAdapter(this, R.layout.bt_listitem, list);
        listView.setAdapter(adapter);
        getPairedDevices();
        onItemClickLisener();
    }
private void onItemClickLisener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             ListItem item=(ListItem) parent.getItemAtPosition(position);
             if(item.getItemType().equals(BtAdapter.DISCOVERY_ITEM_TYPE)) item.getBtDevice().createBond();

            }
        });
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
                item.setBtDevice(device); // MAC address
                list.add(item);
            }
            adapter.notifyDataSetChanged();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == BT_REQUEST_PERM) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isBtPermissionGranted = true;
            } else {
                Toast.makeText(this, "Нет разрешения", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void getBtPermission() {
        String[] permissions = {ACCESS_FINE_LOCATION, BLUETOOTH_SCAN};
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, BT_REQUEST_PERM);
        } else {
            isBtPermissionGranted = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter f1 = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        IntentFilter f2 = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        IntentFilter f3 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(bReciever, f1);
        registerReceiver(bReciever, f2);
        registerReceiver(bReciever, f3);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(bReciever);
    }

    private final BroadcastReceiver bReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                ListItem item=new ListItem();
                item.setBtDevice(device);
                item.setItemType(BtAdapter.DISCOVERY_ITEM_TYPE);
                list.add(item);
                adapter.notifyDataSetChanged();
                //Toast.makeText(context, "Знайдено невий пристрій " + device.getName(), Toast.LENGTH_SHORT).show();
            } else if (
                    BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(intent.getAction())) {
                isDiscovery=false;
                getPairedDevices();
                ab.setText("");

            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(intent.getAction())) {
                BluetoothDevice device=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(device.getBondState()==BluetoothDevice.BOND_BONDED){getPairedDevices();}
                
            }
        }
    };


























}