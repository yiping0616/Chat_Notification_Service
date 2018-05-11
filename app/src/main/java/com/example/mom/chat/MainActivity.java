package com.example.mom.chat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeNotification(); //按下floating button 跳出通知
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**跳出通知 只能在 Android 8 以上 的設備中執行 , 因為NotificationChannel 是Android 8.0以後才有的
     * Android 8.0之前不用NotificationChannel
     * 要有正確的manager ,在不同版本也可以正常通知 , 將manager與channel拉到外面額外判斷
     **/
    private void makeNotification(){
        String channelId = "love";
        String channelName = "我的最愛";
        final int NOTIFICATION_ID = 8;
        //取得通知管理器與產生通知頻道
        NotificationManager manager = getNotificationManager(channelId, channelName);
        Intent intent = new Intent(this , ChatActivity.class);
        /**在桌面直接按notification後 , 按返回鍵會回到桌面 , 若要按返回鍵會回到MainActivity要加stackBuilder
        *使用PendingIntent.getActivity()產生PendingIntent物件
        *第二個參數：辨識碼 , 第三個參數：intent
        *第四個參數：UPDATE_CURRENT:沿用舊的PendingIntent,並更新附帶資料;ON_SHOT:這個PendingIntent只用一次;
        *          CANCEL_CURRENT:PendingIntent執行前會先將舊的結束;NO_CREATE:直接沿用舊的PendingIntent
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this , 0 , intent ,PendingIntent.FLAG_UPDATE_CURRENT);
         **/
        //使用者按下通知後,會先啟動ChatActivity,按返回鍵會回到所設定的父層級類別MainActivity
        //要在Manifest中先設定 , ChatActivity的ParentActivityName
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(ChatActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(
                0,PendingIntent.FLAG_UPDATE_CURRENT);
        //產生通知
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.accessibility)
                .setContentTitle("This is Title")
                .setContentText("This is Text")
                .setContentInfo("This is info")
                .setWhen(System.currentTimeMillis())
                .setChannelId(channelId)    //設定頻道
                .setContentIntent(pendingIntent);   //設定PendingIntent物件 到通知中
        //送出通知
        manager.notify(1, builder.build());
    }

    @NonNull
    private NotificationManager getNotificationManager(String channelId, String channelName) {
        //取得通知管理器, 向系統取得通知服務 再轉型為NotificationManager
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //產生通知頻道
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(
                    channelId,
                    channelName, NotificationManager.IMPORTANCE_HIGH);
            //產生頻道
            manager.createNotificationChannel(channel);
        }
        return manager;
    }

    /**測試IntentService - HelloIntentService
     *
     *產生兩個Intent,分別有TEST1,TEST2字串資料,並幾乎同時送出並啟動服務
     *系統收到後,會送給HelloIntentService,它會自動將兩個Intent放入佇列(Queue)中,並依序執行兩項工作,看Logcat檢查
     * 兩次執行startService幾乎同時,但服務執行不會同時,不管連續執行多少service,都會排入HelloIntentService的佇列,執行完才執行下一個
     * IntentService方便 省去避免同步執行服務的問題
    * */
    public void IntentService(View view){
        Intent hello = new Intent(this , HelloIntentService.class);
        hello.putExtra(HelloIntentService.PARAM_MSG , "TEST1");
        startService(hello);
        Intent hello2 = new Intent(this , HelloIntentService.class);
        hello2.putExtra(HelloIntentService.PARAM_MSG , "TEST2");
        startService(hello2);
    }
    //打開ChatActivity
    public void IntentChatActivity(View view){
        Intent intent = new Intent(this , ChatActivity.class);
        startActivity(intent);
    }
}
