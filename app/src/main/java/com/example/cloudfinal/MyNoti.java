package com.example.cloudfinal;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class MyNoti extends Activity {

    public void mynoti(String str){
        //获取NotificationManager实例
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //通知的点击跳转的
        Intent intent =new Intent(MyNoti.this, MainActivity.class);
        //创建PendingIntent对象
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, 0);
        Notification notification = null;

        notification=new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("通知")
                .setContentText(str)
                .setContentIntent(pendingIntent)        //setContentIntent定义点击通知跳转
                .build();
        notifyManager.notify(1, notification);
    }

}
