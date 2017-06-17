package com.multithread.mosiah.projectcs246;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    private Button button;
    private List<Task> myTaskList = new ArrayList<>();
    private List<Task> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        //bernie's code
        //Drop down for the hours
        Spinner hours = (Spinner)findViewById(R.id.Hours);
        ArrayAdapter<String> myHours = new ArrayAdapter<String>(EditTask.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.hours_array));
        myHours.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hours.setAdapter(myHours);

        //Drop down for the minutes
        Spinner minutes = (Spinner)findViewById(R.id.Minutes);
        ArrayAdapter<String> myMinutes = new ArrayAdapter<String>(EditTask.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.minutes_array));
        myMinutes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minutes.setAdapter(myMinutes);

        //Drop down for the seconds
        Spinner seconds = (Spinner)findViewById(R.id.Seconds);
        ArrayAdapter<String> mySeconds = new ArrayAdapter<String>(EditTask.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.seconds_array));
        mySeconds.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seconds.setAdapter(mySeconds);

        Spinner repetitions = (Spinner)findViewById(R.id.Repetition);
        ArrayAdapter<String> myReps = new ArrayAdapter<String>(EditTask.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.seconds_array));
        myReps.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repetitions.setAdapter(myReps);

        Toast.makeText(getApplication(), "OnClickListener created!", Toast.LENGTH_LONG).show();









        // these lines of code lets me call my shared preferences
        // and then to lload them and then store them in the myTaskList array list.
        // this allows me to make sure that I am not overwriting what is already
        // in my shared preferences. Instead, I am adding to it.
        final SharedPreferences taskList = getSharedPreferences("taskList", MODE_PRIVATE);
        String json = taskList.getString("MyObjects", null); // myTaskList - my list of Tasks objects.
        Type taskListType = new TypeToken<ArrayList<Task>>(){}.getType();
        final Gson gson = new Gson();

        if (json != null) {

            myTaskList = gson.fromJson(json, taskListType);
        }

        /*******************************************************
         * The Save button logic.
         */
        button = (Button) findViewById(R.id.Save);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Task task = new Task();
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






                //show the String json, which will be saved in shared preferences.
                Log.d("MyObjects",json); //log the json
                Toast.makeText(getApplication(), json, Toast.LENGTH_LONG).show();

                //this brings us back to main activity
                Intent intent = new Intent(EditTask.this, MainActivity.class);
                intent.putExtra("activityOne", "set a goal");
                startActivity(intent);


            }
        });

        button = (Button) findViewById(R.id.Cancel);
        button.setOnClickListener(new View.OnClickListener() {
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

    public void saveTask(Task task) {
        //Peyton's logic: Grabs data from drop-downs, updates it to task object, and adds object to list
        //Toast.makeText(getApplication(), "New Task Created!", Toast.LENGTH_LONG).show();





        //set duration from 3 drop-down widgets
      //  Spinner hours = (Spinner) findViewById(R.id.Hours);
       // int hour = (int) hours.getSelectedItem();
      /*  Spinner minutes = (Spinner) findViewById(R.id.Minutes);
        int minute = (int) minutes.getSelectedItem();
        Spinner seconds = (Spinner) findViewById(R.id.Seconds);
        int second = (int) seconds.getSelectedItem();

        long time = ((hour * 60 * 60) + (minute * 60) + second) * 1000; //reduced to milliseconds
        task.setDuration(time);*/

        //add the task object to the list


        //storing object to shared preferences in json form.
     //   SharedPreferences taskList = getSharedPreferences("taskList", MODE_PRIVATE);
     //   SharedPreferences.Editor prefsEditor = taskList.edit();
      //  Gson gson = new Gson();
     //   String json = gson.toJson(taskList); // myTaskList - my list of Tasks objects.
     //   prefsEditor.putString("MyObject", json);
     //   prefsEditor.commit();

        //show the String json, which will be saved in shared preferences.
       // Log.d("MyObjects",json); //log the json
        //Toast.makeText(getApplication(), json, Toast.LENGTH_LONG).show();

    }


}
