package com.example.navigation.ui.home;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private boolean deadline;
    private String date;
    private String time;
    private boolean done;

    public Task(String name, boolean deadline, String date, String time) {
        this.name = name;
        this.deadline = deadline;
        this.date = date;
        this.time = time;
        done = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isDeadline() {
        return deadline;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public boolean isDone() {
        return done;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeadline(boolean deadline) {
        this.deadline = deadline;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
