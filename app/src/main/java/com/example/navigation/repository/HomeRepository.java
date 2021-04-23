package com.example.navigation.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.navigation.DAO.TaskDAO;
import com.example.navigation.DAO.TaskDatabase;
import com.example.navigation.ui.home.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeRepository {

    private static HomeRepository instance;
    private final TaskDAO taskDao;
    private final LiveData<List<Task>> allTasks;
    private LiveData<List<Task>> tasks;
    private LiveData<List<Task>> deadlines;
    private MutableLiveData<String> day;
    private final ExecutorService executorService;

    private HomeRepository(Application application){
        TaskDatabase database = TaskDatabase.getInstance(application);
        taskDao = database.taskDao();

        tasks = new MutableLiveData<>();
        deadlines = new MutableLiveData<>();
        day = new MutableLiveData<>();

        executorService = Executors.newFixedThreadPool(2);

        SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
        Date dateToday = new Date();
        String dateNow = formatter.format(dateToday);
        day.setValue(dateNow);


        allTasks = taskDao.getAllTasks();
        tasks = taskDao.getAllTasks(day.getValue());
        deadlines = taskDao.getDeadlines();
    }

    public static HomeRepository getInstance(Application application){
        if(instance == null)
            instance = new HomeRepository(application);

        return instance;
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public LiveData<String> getDay() {
        return day;
    }

    public void goToDay(String date){
        day.setValue(date);
        tasks = taskDao.getAllTasks(day.getValue());
    }

    public LiveData<List<Task>> getTasks() {
        return tasks;
    }

    public LiveData<List<Task>> getDeadlines() {
        return deadlines;
    }

    public void insert(Task task) {
        executorService.execute(() -> taskDao.insert(task));
    }

    public void removeTask(Task task) {
        executorService.execute(() -> taskDao.delete(task));
    }


    public void updateTask(Task task) {
        executorService.execute(() -> taskDao.update(task));
    }
}
