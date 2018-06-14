package com.example.tsukune.datasecure.UserDB;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import com.example.tsukune.datasecure.Entity.User;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
    int getCount() throws ExecutionException, InterruptedException {
        int count = userDAO.getCount();
        return new getCountAsyncTask(userDAO).execute(count).get();
    }
    void addUser(User user) {
        new  insertAsyncTask(userDAO).execute(user);
    }
    void updateUser (User user) {
        userDAO.updateUser(user);
    }

    private static class getCountAsyncTask extends AsyncTask<Integer, Void, Integer> {
        private UserDAO mAsyncTaskDAO;

        getCountAsyncTask(UserDAO userDAO) {
            mAsyncTaskDAO = userDAO;
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            int count = mAsyncTaskDAO.getCount();
            return count;
        }
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