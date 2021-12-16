package com.antailbaxt3r.lop2021.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.antailbaxt3r.lop2021.R;
import com.antailbaxt3r.lop2021.activities.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFCMService extends FirebaseMessagingService {
    public MyFCMService() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            // Since the notification is received directly from
            // FCM, the title and the body can be fetched
            // directly as below.
            showNotification(
                remoteMessage.getNotification().getTitle(),
                remoteMessage.getNotification().getBody());
        }
    }

    public void showNotification(String title,
                                 String message) {
        Intent intent
            = new Intent(this, MainActivity.class);
        String channel_id = "notification_channel";

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent
            = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder
            = new NotificationCompat
            .Builder(getApplicationContext(),
            channel_id)
            .setSmallIcon(R.drawable.app_logo)
            .setAutoCancel(true)
            .setVibrate(new long[]{1000, 1000, 1000,
                1000, 1000})
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent);

        builder = builder.setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.app_logo);

        NotificationManager notificationManager
            = (NotificationManager) getSystemService(
            Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT
            >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel
                = new NotificationChannel(
                channel_id, "web_app",
                NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(
                notificationChannel);
        }

        notificationManager.notify(0, builder.build());
    }

}