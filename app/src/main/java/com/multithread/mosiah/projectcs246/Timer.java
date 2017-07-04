package com.multithread.mosiah.projectcs246;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Timer extends AppCompatActivity {
    TextView tvTaskName;
    TextView tvTimer;
    Button bStart;
    Button bStop;
    CountDownTimer timer;
    long remainingTime;
    ArrayList<Task> myTaskList = null;
    int position;
    Task task;
    int count;
    Button seeProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //load from sharedpreferences.
        SharedPreferences taskList = getSharedPreferences("taskList", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = taskList.getString("MyObjects", null); // myTaskList - my list of Tasks objects.
        Type taskListType = new TypeToken<ArrayList<Task>>() {
        }.getType();

        //fill myTaskList from the json stored on shared prefs.
        if (json != null)
            myTaskList = gson.fromJson(json, taskListType);


        //grab the position(where the user clicked from the listview in main activity)
        Intent intent = getIntent();

        position = intent.getIntExtra("taskObject", 0);
        task = myTaskList.get(position);

        count = task.getIteration();


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

        seeProgress = (Button) findViewById(R.id.viewProgress);
    }

    public void progressOnClick(View view) {
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //needs Mosiah's code to save remaining time to sharedPreferences here before switching to progress activity
                //consider putting save code in separate function that we can just call here.
                Intent intent = new Intent(Timer.this, ProgressActivity.class);
                startActivity(intent);
            }
        });
    }

    public void startOnClick(View view) {

        if (task.getIteration() == 0)
            return;

        timer = new CountDownTimer(remainingTime,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String timeH = String.valueOf(millisUntilFinished/10000);
                String time = String.valueOf(millisUntilFinished/ 1000);
                String timeMill = String.valueOf(millisUntilFinished/100);

                if (remainingTime != 0 && task != null) {

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
                else
                    return;
            }

            @Override
            public void onFinish() {
                task.setIteration(task.getIteration() - 1);
                tvTimer.setText("Done, Iterations left: " + String.valueOf(task.getIteration()));

                if (task.getIteration() != 0)
                    remainingTime = task.getDuration();

                ToneGenerator tone = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, (int) (ToneGenerator.MAX_VOLUME * 0.85));
                if (task.getIteration() >= 0 && count > 0) {
                   tone.startTone(ToneGenerator.TONE_PROP_BEEP2);
                    --count;
                }

                SharedPreferences taskList = getSharedPreferences("taskList", MODE_PRIVATE);
                Gson gson = new Gson();
                String json = taskList.getString("MyObjects", null); // myTaskList - my list of Tasks objects.
                Type taskListType = new TypeToken<ArrayList<Task>>() {
                }.getType();

                //fill myTaskList from the json stored on shared prefs.
                if (json != null) {
                    myTaskList = gson.fromJson(json, taskListType);

                    if (task.getIteration() == 0 && position <= myTaskList.size()) {
                        myTaskList.remove(position);
                        position = myTaskList.size() + 2;
                        onStop();
                        Intent intent = new Intent(Timer.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(Timer.this, "Good job, you finished a goal!", Toast.LENGTH_LONG).show();
                    }
                    else if (position <= myTaskList.size())
                      myTaskList.set(position, task);

                    //storing object to shared preferences in json form.
                    SharedPreferences.Editor prefsEditor = taskList.edit();
                    String myjson = gson.toJson(myTaskList); // myTaskList - my list of Tasks objects.
                    prefsEditor.putString("MyObjects", myjson);
                    prefsEditor.commit();

                }

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

    @Override
    protected void onStop() {
        super.onStop();
    }
}
