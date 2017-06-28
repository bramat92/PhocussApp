package com.multithread.mosiah.projectcs246;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private TextView textDetail;

    //Code for the details activity class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textDetail = (TextView)findViewById(R.id.taskDetailId);

        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra("taskObject");
        String taskTitle = task.getTaskName();

        textDetail.setText(taskTitle);

        getSupportActionBar().setTitle(taskTitle);

    }
}
