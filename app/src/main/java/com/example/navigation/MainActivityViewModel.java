package com.example.navigation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.MutableLiveData;

import com.example.navigation.repository.HomeRepository;
import com.example.navigation.repository.UserRepository;
import com.example.navigation.ui.home.HomeAdapter;
import com.google.firebase.auth.FirebaseUser;

public class MainActivityViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private final HomeRepository repository;

    public MainActivityViewModel(Application app){
        super(app);
        userRepository = UserRepository.getInstance(app);
        repository = HomeRepository.getInstance(app);
    }

    public void init() {
        String userId = userRepository.getCurrentUser().getValue().getUid();
        System.out.println("this is " + userId);
        repository.init(userId);
    }

    public LiveData<FirebaseUser> getCurrentUser(){
            return userRepository.getCurrentUser();
    }

    public void signOut() {
        userRepository.signOut();
    }
}