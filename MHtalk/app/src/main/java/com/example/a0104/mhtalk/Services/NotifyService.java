package com.example.a0104.mhtalk.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.ConditionVariable;
import android.os.IBinder;

import com.example.a0104.mhtalk.MainUIActivity;
import com.example.a0104.mhtalk.R;

import java.util.ArrayList;

public class NotifyService extends Service {

    private static int NOTIFY_ID = 0;
    private NotificationManager manager;
    private ConditionVariable conditionVariable;
    private ArrayList<String> list;
    private Intent in;
    private PendingIntent intent;

    public NotifyService() {
    }

    @Override
    public void onCreate() {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        list = new ArrayList<String>();
        in = new Intent(this, MainUIActivity.class);

        for(int i=0; i<15; i++)
        {
            list.add(i, String.valueOf(i));
        }

        Thread notifyThread = new Thread(null, mTask, "NotifyService");
        conditionVariable = new ConditionVariable(false);
        notifyThread.start();
    }

    private Runnable mTask = new Runnable() {
        @Override
        public void run() {
            while(list.size()!=0) {
                if (!(conditionVariable.block(5*1000))) {
                    showNotification();
                }
                conditionVariable.block(5*1000);
                NotifyService.this.stopSelf();
            }
        }
    };

    @Override
    public void onDestroy() {
        manager.cancelAll();
        conditionVariable.open();
    }

    public void showNotification() {
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainUIActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setTicker("Notification.Builder");
        mBuilder.setWhen(System.currentTimeMillis());
        mBuilder.setNumber(10);
        mBuilder.setContentTitle("Notification.Builder Title");
        mBuilder.setContentText("Notification.Builder Massage");
        mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setAutoCancel(true);
        nm.notify(111, mBuilder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
