package com.example.navigation.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.navigation.repository.Background;
import com.example.navigation.repository.HomeRepository;
import com.example.navigation.repository.UserRepository;

import java.util.List;

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


    public LiveData<Background> getBackground() {
        return repository.getBackground();
    }

    public LiveData<String> getDay() {
        return repository.getDay();
    }
    public LiveData<List<Task>> getTasks(){
        return repository.getTasks();
    }

    public void deleteTask(Task task) {
        repository.removeTask(task);
    }

    public void updateTask(Task task) {
        repository.updateTask(task);
    }
}