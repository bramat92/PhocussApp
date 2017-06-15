package com.multithread.mosiah.projectcs246;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button button;

    private List<Task> myTaskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences taskList = getSharedPreferences("taskList", MODE_PRIVATE);
        Gson gson = new Gson();

        String json = taskList.getString("MyObject", null); // myTaskList - my list of Tasks objects.
        Type taskListType = new TypeToken<ArrayList<Task>>(){}.getType();

        myTaskList = gson.fromJson(json, taskListType);
        // Task t = myTaskList.get(1);

       // Task[] taskArray = gson.fromJson(jjson, Task[].class);

        Log.d("MainActivity", myTaskList.get(1).getTaskName());

        Toast.makeText(getApplication(), myTaskList.get(1).getTaskName(), Toast.LENGTH_LONG).show();





        button = (Button) findViewById(R.id.addTask);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, EditTask.class));


                Intent intent = new Intent(MainActivity.this, EditTask.class);
                intent.putExtra("activityOne", "set a goal");
                startActivity(intent);
            }
        });

        Toast.makeText(getApplication(), "OnCreate called!", Toast.LENGTH_LONG).show();
    }
}
