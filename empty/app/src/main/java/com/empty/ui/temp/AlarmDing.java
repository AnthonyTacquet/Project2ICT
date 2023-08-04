package com.empty.ui.temp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

public class AlarmDing extends BroadcastReceiver {
    private static Ringtone ringtone;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ringtone != null && ringtone.isPlaying()) {
            ringtone.stop();
        }
        Toast.makeText(context, "Alarm Stopped", Toast.LENGTH_LONG).show();
    }

    public static void startAlarm(Context context) {
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();
    }

    public static void stopAlarm() {
        if (ringtone != null && ringtone.isPlaying()) {
            ringtone.stop();
        }}
}