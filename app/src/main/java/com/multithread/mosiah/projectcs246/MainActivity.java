package com.multithread.mosiah.projectcs246;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button button;
    ListView listView;
    ArrayList<Task> myTaskList = new ArrayList<>();
    ArrayList<String> myTaskListNames = new ArrayList<>();
    ArrayAdapter<String> display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Code for the add task
        button = (Button) findViewById(R.id.addTask);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, EditTask.class);
                intent.putExtra("activityOne", "Set a goal");
                startActivity(intent);
            }
        });

        //Retrieving the data
        SharedPreferences taskList = getSharedPreferences("taskList", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = taskList.getString("MyObjects", null); // myTaskList - my list of Tasks objects.
        Type taskListType = new TypeToken<ArrayList<Task>>() {
        }.getType();

        //use this to clear the listview
        //taskList.edit().remove("MyObjects").commit();
        if (json != null) {

            myTaskList = gson.fromJson(json, taskListType);

            //Calling the custom list view adapter
            ArrayAdapter<Task> taskArrayAdapter = new AdapterTask(this, R.layout.list_row, myTaskList);
            ListView listView = (ListView)findViewById(R.id.listViewId);
            listView.setAdapter(taskArrayAdapter);

            //Creating a listener for each task which then leads to the timer activity
            AdapterView.OnItemClickListener adapterViewListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Task task = myTaskList.get(position);
                    Intent intent = new Intent(MainActivity.this, Timer.class);
                    intent.putExtra("taskObject", task);
                    startActivity(intent);
                }
            };
            listView.setOnItemClickListener(adapterViewListener);




        }

    }



    //Code for the custom list view adapter


}