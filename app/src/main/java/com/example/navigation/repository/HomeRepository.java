package com.example.navigation.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.navigation.API.NameAPI;
import com.example.navigation.API.NameResponse;
import com.example.navigation.API.ServiceGenerator;
import com.example.navigation.DAO.TaskDAO;
import com.example.navigation.DAO.TaskDatabase;
import com.example.navigation.ui.home.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class HomeRepository {

    private static HomeRepository instance;
    //background firebase
    private DatabaseReference myRef;
    private BackgroundLiveData backgroundLiveData;

    private final TaskDAO taskDao;
    private final LiveData<List<Task>> allTasks;
    private LiveData<List<Task>> tasks;
    private LiveData<List<Task>> deadlines;
    private MutableLiveData<String> day;
    private final ExecutorService executorService;
    private final MutableLiveData<String> nameDay;


    private HomeRepository(Application application){
        TaskDatabase database = TaskDatabase.getInstance(application);

        taskDao = database.taskDao();

        tasks = new MutableLiveData<>();
        deadlines = new MutableLiveData<>();
        day = new MutableLiveData<>();
        nameDay = new MutableLiveData<>();

        executorService = Executors.newFixedThreadPool(2);

        SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
        Date dateToday = new Date();
        String dateNow = formatter.format(dateToday);
        day.setValue(dateNow);


        allTasks = taskDao.getAllTasks();
        tasks = taskDao.getAllTasks(day.getValue());
        deadlines = taskDao.getDeadlines();
    }

    public static synchronized HomeRepository getInstance(Application application){
        if(instance == null)
            instance = new HomeRepository(application);

        return instance;
    }

    public void init(String userId) {
        myRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        backgroundLiveData = new BackgroundLiveData(myRef);
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

    public LiveData<String> getNameDayBack() {
        return nameDay;
    }

    public void getNameDay(int month, int day) {
        NameAPI nameApi = ServiceGenerator.getNameApi();
        String monthToSend = String.valueOf(month);
        String dayToSend = String.valueOf(day);
        String country = "dk";
        Call<NameResponse> call = nameApi.getName(country, monthToSend, dayToSend);
        call.enqueue(new Callback<NameResponse>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<NameResponse> call, Response<NameResponse> response) {
                if (response.isSuccessful()) {
                    nameDay.setValue(response.body().getNameDay());
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<NameResponse> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
            }
        });
    }

    public void saveBackground(String background) {
        myRef.setValue(new Background(background));
    }

    public BackgroundLiveData getBackground() {
        return backgroundLiveData;
    }
}
