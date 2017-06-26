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

        if (json != null) {

            myTaskList = gson.fromJson(json, taskListType);
            //Looping through the list and adding items into the myTaskListNames
            for (int i = 0; i < myTaskList.size(); i++)
                myTaskListNames.add(myTaskList.get(i).getTaskName());
            //Calling the custom list view adapter
            final ArrayAdapter<String> adapter = new taskArrayAdapter(this, 0, myTaskListNames);
            ListView listView = (ListView)findViewById(R.id.listViewId);
            listView.setAdapter(adapter);

            //delete button to be pasted into customListView
            ImageView deleteButton;
            deleteButton = (ImageView) findViewById(R.id.trashButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int i = 0;
                    while (i != adapter.getPosition(this.toString())) {
                        i++;
                    }
                    if (i == adapter.getPosition(this.toString())) {
                            //this should remove the passed task from the listView
                            adapter.remove(adapter.getItem(i));
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getApplication(), "Task Deleted!", Toast.LENGTH_LONG).show();
                    }
                }
            });

            //Creating a listener for each task
            AdapterView.OnItemClickListener adapterViewListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Task task = myTaskList.get(position);
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("title", task.getTaskName());
                    startActivity(intent);
                }
            };
            listView.setOnItemClickListener(adapterViewListener);
        }
    }



    //Code for the custom list view adapter
    class taskArrayAdapter extends ArrayAdapter<String> {
        private Context context;
        private ArrayList<String> myTasks;

        //Constructor
        public taskArrayAdapter(Context context, int resource, ArrayList<String> objects) {
            super(context, resource, objects);
            this.context = context;
            this.myTasks = objects;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            //get the property we are displaying
            String task = myTasks.get(position);
            //get the inflater and inflate the XML layout for each item
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.list_row, null);

            TextView titleTitle = (TextView) view.findViewById(R.id.titleTask);
            ImageView playButton = (ImageView) view.findViewById(R.id.playButton);
            ImageView trashButton = (ImageView) view.findViewById(R.id.trashButton);
            //Write code for the repetitions
            //TextView iterate = (TextView)findViewById(R.id.iterateId);
            //String durations = "Duration: " + task.getDuration();
            //String iterations = "Iteration: " + task.getIteration();

            titleTitle.setText(task);
            //durate.setText(task.getDuration());
            //iterate.setText(task.getIteration());
            playButton.setImageDrawable(context.getResources().getDrawable(R.drawable.play));
            trashButton.setImageDrawable(context.getResources().getDrawable(R.drawable.trash));
             return view;
        }
    }
}