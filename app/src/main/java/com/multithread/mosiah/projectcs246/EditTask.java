package com.multithread.mosiah.projectcs246;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EditTask extends AppCompatActivity {
    private EditText editText;
    private TextView myTextView;
    private Button saveButton;
    private Button cancelButton;
    private ArrayList<Task> myTaskList = new ArrayList<>();
    private ListView list;
    private Spinner hours;
    ArrayAdapter<CharSequence> myHours;
    private Spinner minutes;
    ArrayAdapter<CharSequence> myMinutes;
    private Spinner seconds;
    ArrayAdapter<CharSequence> mySeconds;
    private Spinner repetitions;
    ArrayAdapter<CharSequence> myRepetitions;
    private long hrs;
    private String hr;
    private long mins;
    private String min;
    private long secs;
    private String sec;
    private long iteration;
    private String ite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        //code for the spinners
        //Drop down for the hours
        hours = (Spinner)findViewById(R.id.Hours);
        myHours = ArrayAdapter.createFromResource(this, R.array.hours_array, android.R.layout.simple_spinner_item);
        myHours.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hours.setAdapter(myHours);


        //Dropdown for the minutes
        minutes = (Spinner)findViewById(R.id.Minutes);
        myMinutes = ArrayAdapter.createFromResource(this, R.array.minutes_array, android.R.layout.simple_spinner_item);
        myMinutes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minutes.setAdapter(myMinutes);


        //Dropdown for the seconds
        seconds = (Spinner)findViewById(R.id.Seconds);
        mySeconds = ArrayAdapter.createFromResource(this, R.array.seconds_array, android.R.layout.simple_spinner_item);
        mySeconds.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seconds.setAdapter(mySeconds);


        //Dropdown for the repetitions
        repetitions = (Spinner)findViewById(R.id.Repetition);
        myRepetitions = ArrayAdapter.createFromResource(this, R.array.hours_array, android.R.layout.simple_spinner_item);
        myRepetitions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repetitions.setAdapter(myRepetitions);




        //Mosiah's code
        final SharedPreferences taskList = getSharedPreferences("taskList", MODE_PRIVATE);
        String json = taskList.getString("MyObjects", null); // myTaskList - my list of Tasks objects.
        Type taskListType = new TypeToken<ArrayList<Task>>(){}.getType();
        final Gson gson = new Gson();

        if (json != null) {

            myTaskList = gson.fromJson(json, taskListType);
        }

        saveButton = (Button) findViewById(R.id.Save);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Task task = new Task();
                //retrieves task name
                editText = (EditText) findViewById(R.id.taskName);
                task.setTaskName(editText.getText().toString());

                //set iterations from drop-down widget
                 Spinner repetitions = (Spinner) findViewById(R.id.Repetition);
                 task.setIteration(repetitions.getSelectedItemPosition());

                //set duration from 3 drop-down widgets
                Spinner hours = (Spinner) findViewById(R.id.Hours);
                 int hour = hours.getSelectedItemPosition();
                Spinner minutes = (Spinner) findViewById(R.id.Minutes);
                int minute = minutes.getSelectedItemPosition();
                Spinner seconds = (Spinner) findViewById(R.id.Seconds);
                int second = seconds.getSelectedItemPosition();

                long time = ((hour * 60 * 60) + (minute * 60) + second) * 1000; //reduced to milliseconds
                task.setDuration(time);


                myTaskList.add(task);
                //storing object to shared preferences in json form.
                SharedPreferences.Editor prefsEditor = taskList.edit();
                String json = gson.toJson(myTaskList); // myTaskList - my list of Tasks objects.
                prefsEditor.putString("MyObjects", json);
                prefsEditor.commit();

                Toast.makeText(getApplication(), json, Toast.LENGTH_LONG).show();

                //this brings us back to main activity
                Intent intent = new Intent(EditTask.this, MainActivity.class);
                intent.putExtra("activityOne", "set a goal");
                startActivity(intent);


            }
        });

        //Takes you back to main activity
        cancelButton = (Button) findViewById(R.id.Cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //this brings us back to main activity
                Intent intent = new Intent(EditTask.this, MainActivity.class);
                intent.putExtra("activityOne", "set a goal");
                startActivity(intent);
            }
        });



        myTextView = (TextView) findViewById(R.id.textView);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String myString = extras.getString("activityOne");
            myTextView.setText(myString);
        }

    } // end of onCreate()

    @Override
    protected void onStop() {
        super.onStop();



    }

}
