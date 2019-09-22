package com.example.cloudfinal;

/*
主畫面設定
*/

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Notification.Builder;

import static android.content.ContentValues.TAG;

/*
to declare and initialize Recyclerview and the actual data to be displayed in Android Gridlayout.
 */

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    GridLayoutManager mGridLayoutManager;
    List< menu > mFunctionList;
    menu mFunctionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("水族助手");


        // Initialize Android Recyclerview and GridlayoutManager.
        // By default, each item occupies 1 span.
        // spanCount--> int: The number of columns in the grid
        mRecyclerView = findViewById(R.id.recyclerview_main);
        mGridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        //  Initialize the actual data that will appear inside Android Gridlayout.
        mFunctionList = new ArrayList<>();
        mFunctionData = new menu("Temperature", R.drawable.temp120x120);
        mFunctionList.add(mFunctionData);
        mFunctionData = new menu("Turbid", R.drawable.turbid120x120);
        mFunctionList.add(mFunctionData);
        mFunctionData = new menu("Level", R.drawable.level120x120);
        mFunctionList.add(mFunctionData);
        mFunctionData = new menu("PH", R.drawable.ph150x150);
        mFunctionList.add(mFunctionData);
        mFunctionData = new menu("即時影像", R.drawable.camera150x150);
        mFunctionList.add(mFunctionData);

        MyAdapter myAdapter = new MyAdapter(MainActivity.this, mFunctionList);
        mRecyclerView.setAdapter(myAdapter);


        FirebaseInstanceId.getInstance().getInstanceId()
        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "getInstanceId failed", task.getException());
                    return;
                }

                // Get new Instance ID token
                String token = task.getResult().getToken();

                // Log and toast
                Log.e(TAG, token);
            }
        });

    }

}


