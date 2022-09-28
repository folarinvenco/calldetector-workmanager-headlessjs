package com.awesometsproject;


import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.awesometsproject.CallDetector.CallDetectorReceiver;

public class BackgroundWorker extends Worker {
    private final Context context;

    public BackgroundWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }
    @NonNull
    @Override
    public Result doWork() {
        Log.w("backgroundWorker", "Worker do work");
//        Bundle extras = bundleExtras();
        Intent intent = new Intent(this.context, CallDetectorReceiver.class);
        Bundle extras = intent.getExtras();
        if (extras != null) {
            intent.putExtras(intent.getExtras());
        }

        Log.w("backgroundWorker", "Worker do work 2");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
            this.context.startForegroundService(intent);
            this.context.startService(intent);

        } else {
            Log.v("backgroundWorker", "called service");
            this.context.startService(intent);
        }
        return Result.success();

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannel() {
        String description = "test channel";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("demo", "test", importance);
        channel.setDescription(description);
        NotificationManager notificationManager =
                (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

        notificationManager.createNotificationChannel(channel);

    }
}
