package com.multithread.mosiah.projectcs246;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by bernhardtramat on 7/11/17.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("We are in the receiver.", "Yay!");
        //fetch the extra string from the intent
        String getMyString = intent.getExtras().getString("extra");
        Log.e("what is the key? ", getMyString);
        String getMyNote = intent.getExtras().getString("notes");

        Intent serviceIntent = new Intent(context, RingtonePlayingService.class);

        //pass the extra string
        serviceIntent.putExtra("extra", getMyString);
        serviceIntent.putExtra("notes", getMyNote);

        context.startService(serviceIntent);
    }
}
