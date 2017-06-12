package com.multithread.mosiah.projectcs246;

import java.sql.Time;
import java.util.Timer;

/**
 * Created by Peyton on 6/9/2017.
 */

public class Task {
    //options or field variables
    private String taskName;
    private Timer timer;
    private int iteration;
    private Time duration;

    //default constructor
    Task() {
        taskName = "New Task";
    }

    public String getTaskName() {
        return taskName;
    }

    public int getIteration() {
        return iteration;
    }

    public Time getDuration() {
        return duration;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }
}
