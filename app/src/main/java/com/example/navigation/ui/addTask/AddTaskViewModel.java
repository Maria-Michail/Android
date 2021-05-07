package com.example.navigation.ui.addTask;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.navigation.repository.HomeRepository;
import com.example.navigation.ui.home.Task;

public class AddTaskViewModel extends AndroidViewModel {

    private HomeRepository repository;

    public AddTaskViewModel(Application application) {
        super(application);
        repository = HomeRepository.getInstance(application);
    }

    public void insert(final Task task) {
        repository.insert(task);
    }
}
