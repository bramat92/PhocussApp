package com.multithread.mosiah.projectcs246;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EditTask extends AppCompatActivity {

    private TextView myTextView;
    private Button button;
    private List<Task> myTaskList = new ArrayList<>();
    private List<Task> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        //list of tasks
        final SharedPreferences taskList = getSharedPreferences("taskList", MODE_PRIVATE);
        String json = taskList.getString("MyObjects", null); // myTaskList - my list of Tasks objects.
        Type taskListType = new TypeToken<ArrayList<Task>>(){}.getType();
        final Gson gson = new Gson();
        if (json != null) {

            myTaskList = gson.fromJson(json, taskListType);
        }

        button = (Button) findViewById(R.id.Save);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Task task = new Task("workout", 5, 200);
                // Task task2= new Task("study",5,200);
                // Task task3= new Task("doze off",5,200);

                myTaskList.add(task);
                // myTaskList.add(task2);
                // myTaskList.add(task3);

                //storing object to shared preferences in json form.

                SharedPreferences.Editor prefsEditor = taskList.edit();

                String json = gson.toJson(myTaskList); // myTaskList - my list of Tasks objects.
                prefsEditor.putString("MyObjects", json);
                prefsEditor.commit();





                //show the String json, which will be saved in shared preferences.
                Log.d("MyObjects",json); //log the json
                //Toast.makeText(getApplication(), json, Toast.LENGTH_LONG).show();

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
/*
                SharedPreferences taskList = getSharedPreferences("taskList", MODE_PRIVATE);
                //SharedPreferences.Editor prefsEditor = taskList.edit();
                Gson gson = new Gson();

                String jjson = taskList.getString("MyObjectss", null); // myTaskList - my list of Tasks objects.
                // Type taskListType = new TypeToken<ArrayList<Task>>(){}.getType();

                //myTaskList = gson.fromJson(jjson, taskListType);
                // Task t = myTaskList.get(1);

                Task[] taskArray = gson.fromJson(jjson, Task[].class);

                Log.d("MyObjectss",taskArray[0].getTaskName()); */

                // Toast.makeText(getApplication(), jjson, Toast.LENGTH_LONG).show();
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
