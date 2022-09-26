package com.example.infinitytap;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.clevertap.android.sdk.*;
import com.clevertap.android.sdk.interfaces.OnInitCleverTapIDListener;
import com.clevertap.android.sdk.pushnotification.PushConstants;
import com.clevertap.android.sdk.pushnotification.PushNotificationHandler;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.clevertap.android.sdk.interfaces.NotificationHandler;
import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler;

import java.util.Objects;

public class MainActivity extends AppCompatActivity
{
    CleverTapAPI clevertapDefaultInstance;
    FirebaseAnalytics mFirebaseAnalytics;
    Button wv_button,dl_btn;

    @SuppressLint("WrongThread")
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        clevertapDefaultInstance.setNotificationHandler(new PushTemplateNotificationHandler());
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);

        CleverTapAPI.enableXiaomiPushOn(PushConstants.ALL_DEVICES);    //Method for enabling Xiaomi push

        clevertapDefaultInstance.getCleverTapID(new OnInitCleverTapIDListener() {
            @Override
            public void onInitCleverTapID(final String cleverTapID) {
                // Callback on main thread
                System.out.println("CleverTap_ID: "+cleverTapID);
            }
        });

        wv_button=findViewById(R.id.wv_btn);
        dl_btn=findViewById(R.id.dl);

        wv_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                goToWV();
            }
        });

        dl_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                goToDL();
            }
        });

        clevertapDefaultInstance.createNotificationChannel(getApplicationContext(),"infinity","infinity tap","Your Channel Description", NotificationManager.IMPORTANCE_MAX,true);   //Channel ID

        //Methods for Realtime Uninstall implementation
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setUserProperty("ct_objectId", Objects.requireNonNull(CleverTapAPI.getDefaultInstance(this)).getCleverTapID());
    }

    private void goToWV()
    {
        Intent i=new Intent(MainActivity.this,WVActivity.class);
        startActivity(i);
    }

    private void goToDL()
    {
        Intent i=new Intent(MainActivity.this,DLActivity.class);
        startActivity(i);
    }
}