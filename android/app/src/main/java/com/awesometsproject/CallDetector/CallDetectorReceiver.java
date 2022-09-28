package com.awesometsproject.CallDetector;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.facebook.react.HeadlessJsTaskService;

import java.util.Date;
import java.util.List;

//public class CallDetectorService extends HeadlessJsTaskService {
//
//  @Override
//  protected @Nullable HeadlessJsTaskConfig getTaskConfig(Intent intent) {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//      createChannel();
//      Notification notification = new NotificationCompat.Builder(getApplicationContext(), "demo")
//              .setContentTitle("Headless Work")
//              .setTicker("runn")
//              .setSmallIcon(R.mipmap.ic_launcher)
//              .setOngoing(true)
//              .build();
//      startForeground(1, notification);
//    }
//
//    Bundle extras = intent.getExtras();
//    if (extras != null) {
//      return new HeadlessJsTaskConfig(
//          "CallDetector",
//          Arguments.fromBundle(extras),
//          5000, // timeout for the task
//          true // optional: defines whether or not  the task is allowed in foreground. Default is false
//        );
//    }
//    return null;
//  }
//
//  @RequiresApi(Build.VERSION_CODES.O)
//  private void createChannel() {
//    String description = "test channel";
//    int importance = NotificationManager.IMPORTANCE_DEFAULT;
//    NotificationChannel channel = new NotificationChannel("demo", "test", importance);
//    channel.setDescription(description);
//    NotificationManager notificationManager =
//            (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
//
//    notificationManager.createNotificationChannel(channel);
//
//  }
//}
public class CallDetectorReceiver extends BroadcastReceiver {
  private static int lastState = TelephonyManager.CALL_STATE_IDLE;
  private static Date callStartTime;
  private static boolean isIncoming;
  private static String savedNumber;

  @Override
  public void onReceive(final Context context, Intent intent) {
    TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    telephony.listen(new PhoneStateListener() {
      @Override
      public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
        Log.v("incomingNumber>> " , incomingNumber);

        if (!isAppOnForeground((context))) {
          /**
           We will start our service and send extra info about
           network connections
           **/
          Intent serviceIntent = new Intent(context, CallDetectorService.class);
          serviceIntent.putExtra("phoneNumber", incomingNumber.toString());
          context.startService(serviceIntent);
          HeadlessJsTaskService.acquireWakeLockNow(context);
        }
      }
    }, PhoneStateListener.LISTEN_CALL_STATE);


//    String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
//    String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
//    int state = 0;
//    if(stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
//      state = TelephonyManager.CALL_STATE_IDLE;
//    }
//    else if(stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
//      state = TelephonyManager.CALL_STATE_OFFHOOK;
//    }
//    else if(stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
//      state = TelephonyManager.CALL_STATE_RINGING;
//    }
//
//    onCallStateChanged(context, state, number);

  }

  private boolean isAppOnForeground(Context context) {
    /**
     We need to check if app is in foreground otherwise the app will crash.
     http://stackoverflow.com/questions/8489993/check-android-application-is-in-foreground-or-not
     **/
    ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    List<ActivityManager.RunningAppProcessInfo> appProcesses =
            activityManager.getRunningAppProcesses();
    if (appProcesses == null) {
      return false;
    }
    final String packageName = context.getPackageName();
    for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
      if (appProcess.importance ==
              ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND &&
              appProcess.processName.equals(packageName)) {
        return true;
      }
    }
    return false;
  }

  protected void onIncomingCallStarted(Context ctx, String number, Date start){}
  protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end){}

  public void onCallStateChanged(Context context, int state, String number) {
    if(lastState == state)
    {

      return;
    }
    switch (state) {
      case TelephonyManager.CALL_STATE_RINGING:
        isIncoming = true;
        callStartTime = new Date();
        savedNumber = number;
        onIncomingCallStarted(context, number, callStartTime);
        break;

      case TelephonyManager.CALL_STATE_OFFHOOK:
        if (isIncoming)
        {
          onIncomingCallEnded(context,savedNumber,callStartTime,new Date());
        }

      case TelephonyManager.CALL_STATE_IDLE:
        if(isIncoming)
        {
          onIncomingCallEnded(context, savedNumber, callStartTime, new Date());
        }
    }
    lastState = state;
  }
}