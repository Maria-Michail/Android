package com.example.navigation.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.navigation.DAO.HomeDAO;
import com.example.navigation.repository.HomeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeViewModel extends AndroidViewModel {

    private HomeRepository repository;
    private MutableLiveData<String> home_date;
    private MutableLiveData<List<Task>> tasks; //maybe delete

    public HomeViewModel(Application application) {
        super(application);
        repository = HomeRepository.getInstance(application);
        home_date = new MutableLiveData<>();
        tasks = new MutableLiveData<>(); //maybe delete
        home_date.setValue("2021-09-04");
    }


    public LiveData<String> getDay() {
        return repository.getDay();
    }
    public LiveData<List<Task>> getTasks(){
        return repository.getTasks();
    }

    public void deleteTask(int position) {
        repository.removeTask(position);
    }
}