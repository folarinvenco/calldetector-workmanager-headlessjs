package com.awesometsproject;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.concurrent.TimeUnit;

public class BackgroundWorkerManager  extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = "BackgroundWorkerManager";

    private Context mContext;
    private PeriodicWorkRequest workRequest;

    public BackgroundWorkerManager(@NonNull ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
        workRequest = new PeriodicWorkRequest.Builder(BackgroundWorker.class, 5, TimeUnit.SECONDS).build();
    }

    @ReactMethod
    public void startBackgroundWork(String uniqueWorkerName) {
        Log.d("startBackgroundWork", "called start background work");
        WorkManager.getInstance(mContext).enqueueUniquePeriodicWork(uniqueWorkerName, ExistingPeriodicWorkPolicy.KEEP, workRequest);
    }

    @ReactMethod
    public void stopBackgroundWork(String uniqueWorkerName) {
        WorkManager.getInstance(mContext).cancelUniqueWork(uniqueWorkerName);
    }

    @NonNull
    @Override
    public String getName() {
        return MODULE_NAME;
    }
}
