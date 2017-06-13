package com.multithread.mosiah.projectcs246;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
