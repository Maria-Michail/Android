package com.example.navigation.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.navigation.ui.home.Task;

import java.util.List;

@Dao
public interface TaskDAO {

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);


    @Delete
    void delete(Task task);


    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT * FROM task_table WHERE date LIKE :day ORDER BY time ASC")
    LiveData<List<Task>> getAllTasks(String day);

    @Query("SELECT * FROM task_table WHERE deadline = 1 & done = 0 ORDER BY date ASC")
    LiveData<List<Task>> getDeadlines();
}