package com.multithread.mosiah.projectcs246;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.Random;

public class ReminderActivity extends AppCompatActivity {

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private Button setButton;
    private Button cancelButton;
    private TextView alarmTextView;
    private EditText reminderNote;;


    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        this.context = this;

        alarmTextView = (TextView) findViewById(R.id.alarmTextId);

        final Intent myIntent = new Intent(this.context, AlarmReceiver.class);

        //Get the alarm manager service
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmTimePicker = (TimePicker)findViewById(R.id.timePickerId);
        setButton = (Button)findViewById(R.id.setButtonId);
        cancelButton = (Button)findViewById(R.id.cancelAlarmButtonId);

        final Calendar calendar = Calendar.getInstance();

        reminderNote = (EditText)findViewById(R.id.editReminderTextId);


        setButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                Toast.makeText(ReminderActivity.this, "Alarm On", Toast.LENGTH_LONG).show();

                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());

                int hour = alarmTimePicker.getHour();
                int minute = alarmTimePicker.getMinute();
                //myIntent.putExtra("extras", "yes");

                String minute_string = String.valueOf(minute);

                if (hour > 12) {
                    hour = hour - 12;
                }

                if (minute < 10) {
                    minute_string = "0" + String.valueOf(minute);
                }
                String note = reminderNote.getText().toString();
                SharedPreferences prefNotes = getSharedPreferences("reminders", MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = prefNotes.edit();
                prefsEditor.putString("MyReminders", note);
                prefsEditor.commit();

                setAlarmText("Reminder Alarm for " + prefNotes.getString("MyReminders", note) + " set to; " + hour + ":" + minute_string);

                //put in extra string into my intent
                myIntent.putExtra("extra", "alarm on");
                myIntent.putExtra("notes", note);

                pendingIntent = PendingIntent.getBroadcast(ReminderActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myIntent.putExtra("extra", "alarm off");

                if (pendingIntent != null)
                    alarmManager.cancel(pendingIntent);

                sendBroadcast(myIntent);

                setAlarmText("Alarm canceled");

                Toast.makeText(ReminderActivity.this, "Alarm Cancelled", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }


}
