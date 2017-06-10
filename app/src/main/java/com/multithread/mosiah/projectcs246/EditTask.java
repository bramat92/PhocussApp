package com.multithread.mosiah.projectcs246;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EditTask extends AppCompatActivity {

    private TextView myTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        //we will need to create a task object.

    myTextView = (TextView) findViewById(R.id.textView);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String myString = extras.getString("activityOne");
            myTextView.setText(myString);
        }

    }


}
