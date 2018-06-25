package com.example.tsukune.datasecure.UserDB;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Update;
import android.os.AsyncTask;
import com.example.tsukune.datasecure.Entity.User;

import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class  UserRepository {

    private UserDAO userDAO;
    private UserDatabase userDB;
    private LiveData<List<User>> mUser;

    public UserRepository(Application application) {
        userDB = UserDatabase.getInstance(application);
        userDAO = userDB.userDao();
        mUser = userDAO.getAllUser();
    }

    public static class UpdateUserLogin {
        private int id;
        private String username;
        private String password;

        public UpdateUserLogin(int id, String username, String password) {
            this.id = id;
            this.username = username;
            this.password = password;
        }
    }

    public static class Update_PS_FS {
        private int id;
        private String password, encryptionKey;

        public Update_PS_FS(int id, String password, String encryptionKey) {
            this.id = id;
            this.password = password;
            this.encryptionKey = encryptionKey;
        }
    }

    public LiveData<List<User>> getAllUser(){
        return userDAO.getAllUser();
    }

    public int getCount() throws ExecutionException, InterruptedException {
        return new getCountAsyncTask(userDAO).execute().get();
    }

    public void addUser(User user) {
        new insertAsyncTask(userDAO).execute(user);
    }

    public void updateUser (User user) {
        new updateAsyncTask(userDAO).execute(user);
    }

    public void updateUserLogin(UpdateUserLogin updateUserLogin) {
        new updateUserLogin_AsyncTask(userDAO).execute(updateUserLogin);
    }

    public void updatePS(Update_PS_FS update_ps_fs) {
        new updatePS_AsyncTask(userDAO).execute(update_ps_fs);
    }

    public void updateFS(Update_PS_FS update_ps_fs) {
        new updateFS_AsyncTask(userDAO).execute(update_ps_fs);
    }

    private static class getCountAsyncTask extends AsyncTask<Integer, Void, Integer> {
        private UserDAO mAsyncTaskDAO;

        getCountAsyncTask(UserDAO userDAO) {
            mAsyncTaskDAO = userDAO;
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            return mAsyncTaskDAO.getCount();
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

    private static class updateAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDAO mAsyncTaskDAO;

        public updateAsyncTask(UserDAO mAsyncTaskDAO) {
            this.mAsyncTaskDAO = mAsyncTaskDAO;
        }

        @Override
        protected Void doInBackground(User... users) {
            mAsyncTaskDAO.updateUser(users[0]);
            return null;
        }
    }

    private static class updateUserLogin_AsyncTask extends AsyncTask<UpdateUserLogin, Void, Void> {

        private UserDAO mAsyncTaskDAO;
        private int id;
        private String username, password, hashedPassword;

        public updateUserLogin_AsyncTask(UserDAO mAsyncTaskDAO) {
            this.mAsyncTaskDAO = mAsyncTaskDAO;
        }

        @Override
        protected Void doInBackground(UpdateUserLogin... params) {
            id = params[0].id;
            username = params[0].username;
            password = params[0].password;
            hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
            mAsyncTaskDAO.updateUserLogin(id, username, hashedPassword);
            return null;
        }
    }

    private static class updatePS_AsyncTask extends AsyncTask<Update_PS_FS, Void, Void> {

        private UserDAO mAsyncTaskDAO;
        private int id;
        private String hashed_PSPassword, ps_encryptionKey;

        public updatePS_AsyncTask(UserDAO mAsyncTaskDAO) {
            this.mAsyncTaskDAO = mAsyncTaskDAO;
        }

        @Override
        protected Void doInBackground(Update_PS_FS... params) {
            id = params[0].id;
            hashed_PSPassword = params[0].password;
            ps_encryptionKey = params[0].encryptionKey;
            mAsyncTaskDAO.updatePS(id, hashed_PSPassword, ps_encryptionKey);
            return null;
        }
    }

    private static class updateFS_AsyncTask extends AsyncTask<Update_PS_FS, Void, Void> {

        private UserDAO mAsyncTaskDAO;
        private int id;
        private String hashedPassword, fs_encryptionKey;

        public updateFS_AsyncTask(UserDAO mAsyncTaskDAO) {
            this.mAsyncTaskDAO = mAsyncTaskDAO;
        }

        @Override
        protected Void doInBackground(Update_PS_FS... params) {
            id = params[0].id;
            hashedPassword = params[0].password;
            fs_encryptionKey = params[0].encryptionKey;
            mAsyncTaskDAO.updateFS(id, hashedPassword, fs_encryptionKey);
            return null;
        }
    }
}
