package com.example.tsukune.datasecure.UserDB;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import com.example.tsukune.datasecure.Entity.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserViewModel extends AndroidViewModel {

    private LiveData<List<User>> mUser;
    private UserRepository userRepository;
    private UserRepository.UpdateUserLogin updateUserLogin;

    public UserViewModel(Application application) {
        super(application);
        this.userRepository = new UserRepository(application);
        this.mUser = userRepository.getAllUser();
    }

    public LiveData<List<User>> getAllUser() {
        return mUser;
    }

    public int getCount() throws ExecutionException, InterruptedException {
        return userRepository.getCount();
    }

    public void addUser(User user) {
        userRepository.addUser(user);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    public void updateUserLogin(UserRepository.UpdateUserLogin updateUserLogin) {
        userRepository.updateUserLogin(updateUserLogin);
    }

    public void updatePS(UserRepository.Update_PS_FS update_ps_fs) {
        userRepository.updatePS(update_ps_fs);
    }

    public void updateFS(UserRepository.Update_PS_FS update_ps_fs) {
        userRepository.updateFS(update_ps_fs);
    }
}
