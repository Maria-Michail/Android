package com.example.navigation.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.navigation.repository.Background;
import com.example.navigation.repository.HomeRepository;
import com.example.navigation.repository.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private final HomeRepository repository;

    public HomeViewModel(Application application) {
        super(application);
        repository = HomeRepository.getInstance(application);
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

    public void goToDay(final String date) {
        repository.goToDay(date);
    }
}