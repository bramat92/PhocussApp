package com.multithread.mosiah.projectcs246;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Mosiah on 6/27/2017.
 */


 public class AdapterTask extends ArrayAdapter<Task> {
       private Context context;
        private ArrayList<Task> myTasks;

        //Constructor
        public AdapterTask(Context context, int resource, ArrayList<Task> objects) {
            super(context, resource, objects);
            this.context = context;
            this.myTasks = objects;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            //get the propety we are displaying
            //String task = myTasks.get(position);
            //get the inflater and inflate the XML layout for each item
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.list_row, null);

            TextView titleTitle = (TextView) view.findViewById(R.id.titleTask);
            TextView repeat = (TextView)view.findViewById(R.id.repetitionsId);
            ImageView playButton = (ImageView) view.findViewById(R.id.playButton);
            ImageView trashButton = (ImageView) view.findViewById(R.id.trashButton);
            //Write code for the repetitions
            //TextView iterate = (TextView)findViewById(R.id.iterateId);
            //String durations = "Duration: " + task.getDuration();
            //String iterations = "Iteration: " + task.getIteration();
            //if (myTasks != null) {

            //magical piece of code written by mosiah
            final Task theTask = myTasks.get(position);
            int rep = theTask.getIteration();
            String reps = String.valueOf(rep);
            titleTitle.setText(theTask.getTaskName());
            repeat.setText("Repetitions: " + reps);


            //}

            //durate.setText(task.getDuration());
            //iterate.setText(task.getIteration());
            playButton.setImageDrawable(context.getResources().getDrawable(R.drawable.play));
            trashButton.setImageDrawable(context.getResources().getDrawable(R.drawable.trash));

            return view;
        }

    }

