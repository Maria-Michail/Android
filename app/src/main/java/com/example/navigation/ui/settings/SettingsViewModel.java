package com.example.navigation.ui.settings;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.navigation.repository.HomeRepository;

public class SettingsViewModel extends AndroidViewModel {
    private HomeRepository repository;

    public SettingsViewModel(Application application) {
        super(application);
        repository = HomeRepository.getInstance(application);
    }

    public void saveBackground(String background) {
        repository.saveBackground(background);
    }
}
