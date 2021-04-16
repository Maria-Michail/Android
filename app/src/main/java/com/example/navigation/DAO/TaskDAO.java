package com.example.navigation.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.navigation.ui.home.Task;

import java.util.List;

@Dao
public interface TaskDAO {

    @Insert
    void insert(Task task);


    @Delete
    void delete(Task task);


    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getAllTasks();
}