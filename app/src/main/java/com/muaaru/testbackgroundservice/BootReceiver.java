package com.muaaru.testbackgroundservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (context == null) {
            return;
        }
        if (intent == null || intent.getAction() == null) {
            return;
        }
        if (!intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            return;
        }

        Log.i("DEBUG", "ACTION_BOOT_COMPLETED");

        // バックグラウンド実行制限
        // https://developer.android.com/about/versions/oreo/background
        // NotificationManager.startServiceInForegroundを使用すると書かれているが、
        // 実際はContextCompat.startForegroundServiceを使用するらしい
        ContextCompat.startForegroundService(context, new Intent(context, ForegroundService.class));
        // minSdk が Android O 以降であれば ContextCompat ではなく Context の startForegroundService でいい
    }
}

