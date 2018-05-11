package com.example.mom.chat;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.text.format.DateFormat;

public class HelloIntentService extends IntentService {
    //IntentService 比Service更容易使用的服務類別,處理批次型的工作,
    // 重複要求服務會自動以內建佇列(Queue)排序處理,不會有同時執行的問題

    public static final String PARAM_MSG = "message";

    public HelloIntentService(){
        super("HelloIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String msg = intent.getStringExtra(PARAM_MSG);
        SystemClock.sleep(3000);
        String debug = DateFormat.format(
                "hh:mm:ss",System.currentTimeMillis())
                + "\t" + msg;
        Log.d("HelloIntentService" , debug);
    }
}
