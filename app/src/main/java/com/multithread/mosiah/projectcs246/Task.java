package com.multithread.mosiah.projectcs246;

import java.sql.Time;
import java.util.Timer;

/**
 * Created by Peyton on 6/9/2017.
 */

public class Task {
    //options or field variables
    private String taskName;
    private int iteration;
    private long duration;


    //non default constructor
    Task(String _name, int _iteration, int _duration ) {
        iteration = _iteration;
        taskName = _name;
        duration = _duration;

    }

    public String getTaskName() {
        return taskName;
    }

    public int getIteration() {
        return iteration;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }


}
