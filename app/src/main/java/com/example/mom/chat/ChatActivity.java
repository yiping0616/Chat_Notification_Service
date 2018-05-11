package com.example.mom.chat;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ChatActivity extends AppCompatActivity implements ServiceConnection {

    ChatService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = new Intent(this , ChatService.class);
        //第二個參數:ServiceConnection物件,ChatActivity implement ServiceConnection
        //第三個參數:int flags
        //BIND_AUTO_CREATE 綁定並產生服務,若系統中沒有這個Service物件,會自動產生執行onCreate(),但不會執行onStartCommand()
        bindService(intent , this , BIND_AUTO_CREATE);

    }

    //當綁定成功時,會自動執行onServiceConnected(),傳入IBinder service
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        ChatService.ChatBinder binder = (ChatService.ChatBinder) service;
        mService = binder.getService();  //取得ChatService
        Log.d("CHAT" , "onServiceConnected: ");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mService = null;
        Log.d("CHAT" , "onServiceDisconnected: ");
    }

    //結束ChatActivity時,脫離已綁定的服務,會自動呼叫Service的onUnbind(),onDestroy()結束Service
    @Override
    protected void onStop() {
        super.onStop();
        unbindService(this);
    }
}
