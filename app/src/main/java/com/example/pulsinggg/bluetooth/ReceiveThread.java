package com.example.pulsinggg.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ReceiveThread extends Thread{
    private BluetoothSocket socket;
    private InputStream inputS;
    private OutputStream outputS;
    private byte[] rBuffer;
    public ReceiveThread(BluetoothSocket socket){
        this.socket=socket;
        try{
            inputS=socket.getInputStream();
        }catch (IOException e){

        }
        try{
            outputS=socket.getOutputStream();
        }catch (IOException e){

        }
    }
//ПРИНИМАЕМ С АРДУИНО
public String readMesenger () {
    rBuffer = new byte[10];
    while (true) {
        try {
            int size = inputS.read(rBuffer);
            String message = new String(rBuffer, 0, size);
            Log.d("MyLog", "Message: " + message);
            return message;
        } catch (IOException e) {
            break;
        }
    }
    return null;
}
    @Override
    public void run() {
        readMesenger();
    }

//Отправка ардуино
public void sendMesenger(byte[] byteArray){
    try{
        outputS.write(byteArray);
    }catch (IOException e){

    }

}










}
