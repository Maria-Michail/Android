package com.example.navigation.ui.deadlines;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.navigation.repository.HomeRepository;
import com.example.navigation.ui.home.Task;

import java.util.List;

public class DeadlinesViewModel extends AndroidViewModel {

    private HomeRepository repository;

    public DeadlinesViewModel(Application application) {
        super(application);
        repository = HomeRepository.getInstance(application);
    }

    public LiveData<List<Task>> getDeadlines(){
        return repository.getDeadlines();
    }
}