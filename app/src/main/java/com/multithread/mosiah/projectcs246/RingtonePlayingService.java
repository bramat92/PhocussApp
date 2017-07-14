package com.multithread.mosiah.projectcs246;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class RingtonePlayingService extends Service {

    MediaPlayer mediaSong;
    int startId;
    boolean isRunning;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //fetch the extra string
        String state = intent.getExtras().getString("extra");
        Log.e("Ringtone extra is ", state);


        String myNote = intent.getExtras().getString("notes");


        assert state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                Log.e("StartId is ", state);
                break;
            default:
                startId = 0;
                break;
        }

        if (!this.isRunning && startId == 1) {
            Log.e("There's no music ", "and you want to start");
            //Create an instance of the media player
            mediaSong = MediaPlayer.create(this, R.raw.gdfr);
            mediaSong.start();

            this.isRunning = true;
            this.startId = 0;

            //Notifications

            //set up an intent that goes to the reminder activity
            Intent alarmIntent = new Intent(this, ReminderActivity.class);
            //set up a pending intent
            PendingIntent pendingIntent2 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), alarmIntent, 0);
            //Make notification parameters
            Notification popup = new NotificationCompat.Builder(this)
                    .setContentTitle("Phocus Reminder!")
                    .setContentText("Do your " + myNote + " task")
                    .setSmallIcon(R.drawable.splash)

                    .setContentIntent(pendingIntent2)
                    .setAutoCancel(true)
                    .build();

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(0, popup);
        }
        else if (this.isRunning && startId == 0) {
            Log.e("There's music ", "and you want it to end");
            mediaSong.stop();
            //mediaSong.reset();
            this.isRunning = false;
            this.startId = 0;
        }
        else if (!this.isRunning && startId == 0) {
            Log.e("No music ", "you still pressed cancel");
            this.isRunning = false;
            this.startId = 0;
        }
        else if (this.isRunning && startId == 1) {
            this.isRunning = true;
            this.startId = 1;
        }
        else {

        }




        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.isRunning = false;
    }
}






