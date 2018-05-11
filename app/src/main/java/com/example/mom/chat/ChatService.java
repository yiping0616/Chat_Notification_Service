package com.example.mom.chat;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ChatService extends Service {  //綁定型Service,Service與Activity聯繫較為密切
    //聯繫關係靠實作IBinder介面的綁定器維持
    //Activity結束,所綁定的Service也會被系統結束

    //IBinder是java interface,需要時做9個方法
    //android.os.Binder已經實作了IBinder,繼承Binder就屬於IBinder家族,不用override那麼多方法
    //ChatBinder是繼承Binder的內部類別
    ChatBinder mBinder = new ChatBinder();
    public class ChatBinder extends Binder{
        public ChatService getService(){    //ChatActivity透過onBind()取得Binder物件,ChatActivity會透過getService()取得ChatService
            return ChatService.this;
        }
    }

    public ChatService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void sendMessage(String message){
        Log.d("chatService" , "sendMessage:"+message);
    }
    public void deleteMessage(String message){
        Log.d("chatService" , "deleteMessage");
    }
}
