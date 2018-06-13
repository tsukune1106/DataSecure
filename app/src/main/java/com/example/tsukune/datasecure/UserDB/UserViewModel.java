package com.example.tsukune.datasecure.UserDB;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.tsukune.datasecure.Entity.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private LiveData<List<User>> mUser;
    private UserRepository userRepository;

    public UserViewModel(Application application) {
        super(application);
        this.userRepository = new UserRepository(application);
        this.mUser = userRepository.getAllUser();
    }

    public LiveData<List<User>> getAllUser() {
        return mUser;
    }

    public void addUser(User user) {
        userRepository.addUser(user);
    }
}
