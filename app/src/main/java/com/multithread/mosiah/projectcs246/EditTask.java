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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class EditTask extends AppCompatActivity {

    private TextView myTextView;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        //we will need to create a task object.

        //Drop down for the hours
        Spinner spinner1 = (Spinner)findViewById(R.id.Hours);
        ArrayAdapter<String> myHours = new ArrayAdapter<String>(EditTask.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.hours_array));
        myHours.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(myHours);

        //Drop down for the minutes
        Spinner spinner2 = (Spinner)findViewById(R.id.Minutes);
        ArrayAdapter<String> myMinutes = new ArrayAdapter<String>(EditTask.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.minutes_array));
        myMinutes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(myMinutes);

        //Drop down for the seconds
        Spinner spinner3 = (Spinner)findViewById(R.id.Seconds);
        ArrayAdapter<String> mySeconds = new ArrayAdapter<String>(EditTask.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.seconds_array));
        mySeconds.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(mySeconds);


        button = (Button) findViewById(R.id.Save);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Task task = new Task();
                Toast.makeText(getApplication(), "New Task Created!", Toast.LENGTH_LONG).show();

                //set task name from textview widget
                TextView name = (TextView) findViewById(R.id.taskName);
                task.setTaskName(name.toString());

                //set iterations from drop-down widget
                Spinner repetitions = (Spinner) findViewById(R.id.Repetition);
                task.setIteration((int)repetitions.getSelectedItem());

                //set duration from 3 drop-down widgets
                Spinner hours = (Spinner) findViewById(R.id.Hours);
                int hour = (int) hours.getSelectedItem();
                Spinner minutes = (Spinner) findViewById(R.id.Minutes);
                int minute = (int) minutes.getSelectedItem();
                Spinner seconds = (Spinner) findViewById(R.id.Seconds);
                int second = (int) seconds.getSelectedItem();

                long time = ((hour * 60 * 60) + (minute * 60) + second) * 1000; //reduced to milliseconds
                task.setDuration(time);

                //storing object to shared preferences in json form.
                SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(task); // myObject - instance of MyObject
                prefsEditor.putString("MyObject", json);
                prefsEditor.commit();


                //show the String json, which will be saved in shared preferences.
                Log.d("EditTask",json);
                Toast.makeText(getApplication(), json, Toast.LENGTH_LONG).show();


                Intent intent = new Intent(EditTask.this, MainActivity.class);
                //intent.putExtra("activityOne", "set a goal");
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


}
