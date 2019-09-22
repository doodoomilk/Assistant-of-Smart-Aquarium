package com.example.cloudfinal;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Iterator;

import static android.content.ContentValues.TAG;

public class MyInstanceIDService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages
        // are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data
        // messages are the type
        // traditionally used with GCM. Notification messages are only received here in
        // onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated
        // notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages
        // containing both notification
        // and data payloads are treated as notification messages. The Firebase console always
        // sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
       // Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            //Log.d(TAG, "Message data payload: " + remoteMessage.getData());
           // mainActivity.Noti_temp(remoteMessage.getData());


            String value="";
            Iterator<String> iter = remoteMessage.getData().keySet().iterator();
            while(iter.hasNext()){
                String key=iter.next();
                value = remoteMessage.getData().get(key);
                Log.e("sns_message",value);
            }

            sendNotification(value);

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {


            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void sendNotification(String messageBody) {
        Intent intent = null;
        if (messageBody.equals("Water Level < 20!")){
            intent = new Intent(this, level.class);
        }
        else if (messageBody.equals("Water Turbidity > 10000!")){
            intent = new Intent(this, turbid.class);
        }
        else if (messageBody.equals("Water Temperature > 40!")){
            intent = new Intent(this, temperature.class);
        }
        else {
            intent = new Intent(this, MainActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Notification notification = null;

        notification=new Notification.Builder(this)
                .setSmallIcon(R.drawable.login)
                .setContentTitle("通知")
                .setPriority(Notification.PRIORITY_MAX)
                .setContentText(messageBody)
                .setContentIntent(pendingIntent)        //setContentIntent定义点击通知跳转
                .setAutoCancel(true)    //點擊後自動消失
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 ,notification);

    }

}