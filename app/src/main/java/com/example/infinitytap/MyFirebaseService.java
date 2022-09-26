package com.example.infinitytap;


import android.os.Bundle;

import android.util.Log;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.pushnotification.NotificationInfo;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.clevertap.android.sdk.pushnotification.fcm.CTFcmMessageHandler;


import java.util.Map;


public class MyFirebaseService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage message){
        try
        {
            if (message.getData().size() > 0)
            {
                Bundle extras = new Bundle();
                for (Map.Entry<String, String> entry : message.getData().entrySet())
                {
                    extras.putString(entry.getKey(), entry.getValue());
                }

                NotificationInfo info = CleverTapAPI.getNotificationInfo(extras);

                if (info.fromCleverTap)
                {
                    new CTFcmMessageHandler().createNotification(getApplicationContext(), message);    //method for rendering push templates as well as standard push notifications
                    //CleverTapAPI.createNotification(getApplicationContext(), extras);   method for rendering standard push notifications
                }
                else
                {
                    // not from CleverTap handle yourself or pass to another provider
                }
            }
        }
        catch (Throwable t)
        {
            Log.d("MYFCMLIST", "Error parsing FCM message", t);
        }
    }


    @Override
    public void onNewToken(String token)
    {
        CleverTapAPI.getDefaultInstance(this).pushFcmRegistrationId(token,true);
    }
}