package com.multithread.mosiah.projectcs246;

import android.os.CountDownTimer;
import android.os.SystemClock;

import java.io.Serializable;
import java.sql.Time;
import java.util.Timer;

/**
 * The Task class is used to define a Task object, which contains a name, duration, and a number of
 * repetitions or iterations.
 *
 * Created on 6/9/2017.
 * @author Peyton Dunnaway
 */

public class Task implements Serializable {

    //options or field variables
    private String taskName;
    private int iteration;
    private int originalIteration;
    private long duration;
    private int hour;
    private int minute;


    /**
     * Default Task constructor which fills task fields of name, iteration, and duration with
     * default values of "New Task", 1 iteration, and 30min duration.
     * @author Peyton Dunnaway
     */
    Task() {
        taskName = "New Task";
        iteration = 1;
        originalIteration = 1;
        duration = 1800000; //default time for 30min
        hour = 0;
        minute = 0;
    }

    /**
     * Non-default Task constructor for when desired values are known at Task creation time.
     * @author Peyton Dunnaway
     * @param _name string variable to name a Task object
     * @param _iteration int variable to set number of repetitions the Task will go through
     * @param _duration int variable to set length of time a Task will last on the timer
     */
    Task(String _name, int _iteration, int _originalIteration, long _duration, int _hour, int _minute) {
        taskName = _name;
        iteration = _iteration;
        originalIteration = _originalIteration;
        duration = _duration;
        hour = _hour;
        minute = _minute;
    }

    /**
     * Returns string value of a Task's name
     * @author Peyton Dunnaway
     * @return taskName
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Returns int value of Task's number of iterations
     * @author Peyton Dunnaway
     * @return iteration
     */
    public int getIteration() {
        return iteration;
    }

    public int getOriginalIteration() {return originalIteration; }

    /**
     * Returns long value of Task's duration time
     * @author Peyton Dunnaway
     * @return duration
     */
    public long getDuration() { return duration; }

    public int getHour() { return hour; }

    public int getMinute() { return minute; }

    /**
     * Sets Task's name to string value passed in
     * @author Peyton Dunnaway
     * @param taskName passed from Edit Text widget called taskName
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * Sets Task's number of iterations to int value passed in.
     * @author Peyton Dunnaway
     * @param iteration int value to set Task's iteration count with
     */
    public void setIteration(int iteration) {
        if (iteration >= 0)
            this.iteration = iteration;
        else
            this.iteration = 0;
    }

    public void setOriginalIteration(int originalIteration) {

            this.originalIteration = originalIteration;

    }

    /**
     * Sets Task's duration to long value passed in
     * @author Peyton Dunnaway
     * @param duration long value to set Task's duration with
     */
    public void setDuration(long duration) { this.duration = duration; }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
