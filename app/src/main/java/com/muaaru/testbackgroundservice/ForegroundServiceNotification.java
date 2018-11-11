package com.muaaru.testbackgroundservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

// 通知
// https://developer.android.com/guide/topics/ui/notifiers/notifications
class ForegroundServiceNotification {

    static final int FOREGROUND_SERVICE_NOTIFICATION_ID = R.string.foreground_service_notification_id;

    private static final String CHANNEL_ID = "channel_id";
    private static final String CHANNEL_NAME = "channel_name";

    static void createNotificationChannel(@NonNull final Context context) {
        // 通知チャネルの作成と管理
        // https://developer.android.com/training/notify-user/channels

        // Android O 以降、すべての通知をチャンネルに割り当てる必要がある
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }

        final NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null || manager.getNotificationChannel(CHANNEL_ID) != null) {
            return; //すでに作成済みの場合など
        }

        // Foreground Service の通知の場合、IMPORTANCE_MIN は非推奨
        // https://developer.android.com/reference/android/app/NotificationManager.html#IMPORTANCE_MIN

        // Foreground Service の通知の場合、IMPORTANCE_HIGH 未満を指定しても、IMPORTANCE_HIGH として扱われている？
        final NotificationChannel notificationChannel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH);
        // 重要度ごとの違い
        // https://developer.android.com/training/notify-user/channels#importance

        //すでに指定したチャンネルが作成済みの場合、何も起きない
        manager.createNotificationChannel(notificationChannel);
    }

    static Notification createServiceNotification(@NonNull final Context context, @NonNull final PendingIntent pendingIntent) {
        return new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText("ForegroundService")
                .setContentIntent(pendingIntent)
                .build();
    }
}