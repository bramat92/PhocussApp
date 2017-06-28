package com.multithread.mosiah.projectcs246;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Timer extends AppCompatActivity {
    TextView tvTaskName;
    TextView tvTimer;
    Button bStart;
    Button bStop;
    CountDownTimer timer;
    long remainingTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra("taskObject");
        String taskTitle = task.getTaskName();

        //set textview to the name of the task
        tvTaskName = (TextView) findViewById(R.id.taskDetailId);
        tvTaskName.setText(task.getTaskName());


        //set textview to the duration of the task
        tvTimer = (TextView) findViewById(R.id.textView);
        int seconds = (int) (task.getDuration() / 1000) % 60;
        int minutes = (int) ((task.getDuration() / (1000 * 60)) % 60);
        int hours = (int) ((task.getDuration() / (1000 * 60 * 60)) % 24);
        tvTimer.setText("" + String.format("%02d:%02d:%02d", hours, minutes, seconds));

        bStart = (Button) findViewById(R.id.start);

        bStop = (Button) findViewById(R.id.stop);
        remainingTime = task.getDuration();

    }

    public void startOnClick(View view) {
        timer = new CountDownTimer(remainingTime,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String timeH = String.valueOf(millisUntilFinished/10000);
                String time = String.valueOf(millisUntilFinished/ 1000);
                String timeMill = String.valueOf(millisUntilFinished/100);

                if (remainingTime != 0) {


                    int seconds = (int) (millisUntilFinished / 1000) % 60;
                    int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                    int hours = (int) ((millisUntilFinished / (1000 * 60 * 60)) % 24);
                    //tvTimer.setText(hours + ":" + minutes + ":" + seconds);
                    tvTimer.setText("" + String.format("%02d:%02d:%02d", hours, minutes, seconds));
                /*tvTimer.setText(""+String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));*/
                    remainingTime = millisUntilFinished;
                }
                else if(remainingTime < 0){

                    int seconds = (int) (millisUntilFinished / 1000) % 60;
                    int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                    int hours = (int) ((millisUntilFinished / (1000 * 60 * 60)) % 24);

                    tvTimer.setText("" + String.format("%02d:%02d:%02d", hours, minutes, seconds));

                    remainingTime = millisUntilFinished;
                }

            }

            @Override
            public void onFinish() {
                tvTimer.setText("Done");

            }
        };
        timer.start();
    }

    public void pauseOnClick(View view) {
        if(timer != null) {
            timer.cancel();
            //tvTimer.setText("0");
        }

    }
}
