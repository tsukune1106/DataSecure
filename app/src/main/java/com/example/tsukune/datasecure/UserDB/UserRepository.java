package com.example.tsukune.datasecure.UserDB;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import com.example.tsukune.datasecure.Entity.User;
import java.util.List;

public class  UserRepository {

    private UserDAO userDAO;
//    private static UserRepository uInstance;
    private UserDatabase userDB;
    private LiveData<List<User>> mUser;

    public UserRepository(Application application) {
        userDB = UserDatabase.getInstance(application);
        userDAO = userDB.userDao();
        mUser = userDAO.getAllUser();
    }


//    public static UserRepository getInstance (UserDAO userDAO) {
//        if (uInstance == null) {
//            uInstance = new UserRepository(userDAO);
//        }
//        return uInstance;
//    }

    public LiveData<List<User>> getAllUser(){
        return userDAO.getAllUser();
    }

    void addUser(User user) {
        new  insertAsyncTask(userDAO).execute(user);
    }
    void updateUser (User user) {
        userDAO.updateUser(user);
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDAO mAsyncTaskDAO;

        insertAsyncTask(UserDAO userDAO) {
            mAsyncTaskDAO = userDAO;
        }

        @Override
        protected Void doInBackground(User... users) {
            mAsyncTaskDAO.addUser(users[0]);
            return null;
        }
    }
}
