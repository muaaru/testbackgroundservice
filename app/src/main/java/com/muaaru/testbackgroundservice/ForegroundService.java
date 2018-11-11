package com.muaaru.testbackgroundservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

// サービス
// https://developer.android.com/reference/android/app/Service
public class ForegroundService extends Service {

    public ForegroundService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("DEBUG", "onCreate");

        // アプリ起動時にチャンネル作成するほうがいい
        ForegroundServiceNotification.createNotificationChannel(getApplicationContext());
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("DEBUG", "onStartCommand");

        start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("DEBUG", "run");
                doSomething();
                stop();
            }
        }).start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("DEBUG", "onDestroy");
    }

    // フォラグランドサービスの開始
    private void start() {
        Log.i("DEBUG", "start");

        final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), MainActivity.REQUEST_CODE, intent, FLAG_UPDATE_CURRENT);

        // startForegroundServiceでサービス起動から5秒以内にstartForegroundして通知を表示しないとANRエラーになる
        final Notification notification = ForegroundServiceNotification.createServiceNotification(getApplicationContext(), pendingIntent);
        startForeground(ForegroundServiceNotification.FOREGROUND_SERVICE_NOTIFICATION_ID, notification);
    }

    // なんか処理する
    private void doSomething() {
        Log.i("DEBUG", "doSomething");
        try {
            Thread.sleep(1000 * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // フォラグランドサービスの停止
    private void stop() {
        stopForeground(true);
        stopSelf();
        Log.i("DEBUG", "stop");
    }

}
