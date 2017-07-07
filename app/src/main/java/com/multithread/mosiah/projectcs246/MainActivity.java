package com.multithread.mosiah.projectcs246;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
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

/**
 * MainActivity class is the opening activity which displays a ListView of Task objects, along with
 * Progress Bars, showing how far along a user is from completing the task for the originally designated
 * number of iterations.
 * <p>Holding down a Task item will prompt a delete confirmation.</p>
 * <p>Clicking a Task item once will take user to the Timer activity for that Task.</p>
 *
 * @author Mosiah Querubin
 */
public class MainActivity extends AppCompatActivity {

    private Button button;
    ListView listView;
    ArrayList<Task> myTaskList = new ArrayList<>();
    ArrayAdapter<Task> taskArrayAdapter;

    /**
     * Takes care of loading SharedPreferences data and creating onClick listeners for all the
     * Buttons on the activity.
     * @author Mosiah Querubin
     * @param savedInstanceState variable used to create new activity
     */
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

            taskArrayAdapter = new AdapterTask(this, R.layout.list_row, myTaskList);
            ListView listView = (ListView)findViewById(R.id.listViewId);
            listView.setAdapter(taskArrayAdapter);

            //Creating a listener for each task which then leads to the timer activity
            AdapterView.OnItemClickListener adapterViewListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Task task = myTaskList.get(position);
                    Intent intent = new Intent(MainActivity.this, Timer.class);
                    intent.putExtra("taskObject", position);
                    startActivity(intent);
                }
            };
            listView.setOnItemClickListener(adapterViewListener);
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    removeItem(position);
                    return true;
                }
            });
        }
    }

    /**
     * Populate ListView and addTask button when the MainActivity starts
     * @author Mosiah Querubin, Bernhardt Ramat
     */
    @Override
    protected void onStart() {
        super.onStart();
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
            taskArrayAdapter = new AdapterTask(this, R.layout.list_row, myTaskList);
            ListView listView = (ListView) findViewById(R.id.listViewId);
            listView.setAdapter(taskArrayAdapter);

            //Creating a listener for each task which then leads to the timer activity
            AdapterView.OnItemClickListener adapterViewListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Task task = myTaskList.get(position);
                    Intent intent = new Intent(MainActivity.this, Timer.class);
                    intent.putExtra("taskObject", position);
                    startActivity(intent);
                }
            };
            listView.setOnItemClickListener(adapterViewListener);
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    removeItem(position);
                    return true;
                }
            });
        }
    }

    /**
     * Deletes a Task item from the ListView
     * @author Bernhardt Ramat
     * @param position where item to be deleted is in the list
     */
    protected void removeItem(int position) {
        final int deletePosition = position;
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Delete");
        alert.setMessage("Do you want to delete this item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myTaskList.remove(deletePosition);

                SharedPreferences taskList = getSharedPreferences("taskList", MODE_PRIVATE);
                Gson gson = new Gson();
                SharedPreferences.Editor prefsEditor = taskList.edit();
                String json = gson.toJson(myTaskList); // myTaskList - my list of Tasks objects.
                prefsEditor.putString("MyObjects", json);
                prefsEditor.commit();
                taskArrayAdapter.notifyDataSetChanged();
                taskArrayAdapter.notifyDataSetInvalidated();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }
}