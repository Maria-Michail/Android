package com.example.navigation.ui.calendar;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.navigation.repository.HomeRepository;

public class CalendarViewModel extends AndroidViewModel {

    private HomeRepository repository;


    public CalendarViewModel(Application application) {
        super(application);
        repository = HomeRepository.getInstance(application);
    }


    public void goToDay(final String date) {
        repository.goToDay(date);
    }

    LiveData<String> getNameDayBack() {
        return repository.getNameDayBack();
    }

    public void getNameDay(int month, int day){
        repository.getNameDay(month, day);
    }
}