package com.multithread.mosiah.projectcs246;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Timer extends AppCompatActivity {

    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timer = new CountDownTimer(task.getDuration(), 1000) {
            public void onTick(long remaining) {
                System.out.printf("seconds remaining: " + remaining / 1000);
            }

            public void onFinish() {
                System.out.printf("done!");
            }
        }.start();
    }
}
