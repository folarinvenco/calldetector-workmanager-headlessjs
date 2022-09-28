package com.awesometsproject.CallDetector;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;
import javax.annotation.Nullable;

public class CallDetectorService extends HeadlessJsTaskService {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         super.onStartCommand(intent, flags, startId);
         return START_STICKY;
    }

    @Override
    protected @Nullable HeadlessJsTaskConfig getTaskConfig(Intent intent) {
        Bundle extras = intent.getExtras();
        Log.v("incomingCallIntent",   Arguments.fromBundle(extras).toString());

        return new HeadlessJsTaskConfig(
        "CallDetector",
        extras != null ? Arguments.fromBundle(extras) : null,
        5000, // timeout for the task
        false // optional: defines whether or not  the task is allowed in foreground. Default is false
        );


    }
}
