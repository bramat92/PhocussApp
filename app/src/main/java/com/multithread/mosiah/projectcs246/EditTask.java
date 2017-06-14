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

public class EditTask extends AppCompatActivity {

    private TextView myTextView;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        //we will need to create a task object.


        button = (Button) findViewById(R.id.Save);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Task task = new Task("workout", 5, 200);

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
