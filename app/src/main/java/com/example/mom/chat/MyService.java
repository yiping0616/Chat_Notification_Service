package com.example.mom.chat;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {    //獨立運作的Service , 不需要與Activity有聯繫
    public MyService() {
    }

    //onBind()需要進一步設計才能使用,否則onBind()被執行時會一律被拋出例外,造成執行錯誤
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService" , "onStartCommand");
        Log.d("MyService" , "下載檔案中...");
        //Handler的postDelayed() 可延後一段時間後執行特定的執行緒工作 , 3秒後會執行Runnable()裡的run()
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("MyService" , "下載完成");
            }
        }, 3000);
        Log.d("MyService" , "onStartCommand將結束");
        return START_NOT_STICKY;
        //回傳的三種模式：
        //START_NOT_STICKY：Service結束時並不會再重新啟動它
        //START_STICKY：Service結束時，會在自動重啟它，但Intent會被清除為null
        //START_REDELIVER_INTENT：與START_STICKY類似，但舊的Intent會被保留在傳遞到onStartCommand中
    }
}
