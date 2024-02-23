package com.example.pulsinggg.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.pulsinggg.adapter.BtConst;

public class Connection {
    private Context context;
    private SharedPreferences pref;
    private BluetoothAdapter btadapt;
    private BluetoothDevice device;
    private  ConnectThreed connectThreed;

    public Connection(Context context) {
        this.context = context;
        pref=context.getSharedPreferences(BtConst.MY_PREF,Context.MODE_PRIVATE);
        btadapt=BluetoothAdapter.getDefaultAdapter();
    }
    public void  connect()
    {
        String mac=pref.getString(BtConst.MAC_KEY,"");
        if(!btadapt.isEnabled()||mac.isEmpty())return;
        device=btadapt.getRemoteDevice(mac);
        if(device==null)return;
        ConnectThreed connectThreed1=new ConnectThreed(context,btadapt,device);
        connectThreed1.start();

    }
    public void sendMasenger(String messeng){
        connectThreed.getReceiveThread().sendMesenger(messeng.getBytes());
    }
    //Не работает
    public String readMasenger(){
        return connectThreed.getReceiveThread().readMesenger();

    }

}
