package com.example.tsukune.datasecure.PasswordStorageDB;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import com.example.tsukune.datasecure.Entity.Password_Storage;
import java.util.List;

public class PasswordStorageRepository {

    private PasswordStorageDAO psDAO;
    private PasswordStorageDatabase psDB;
    private LiveData<List<Password_Storage>> mPS;

    public PasswordStorageRepository(Application application) {
        psDB = PasswordStorageDatabase.getInstance(application);
        psDAO = psDB.psDAO();
        mPS = psDAO.getAllPS();
    }

    LiveData<List<Password_Storage>> getAllPS() { return mPS; }

    public void addPS(Password_Storage ps) { new insertAsyncTask(psDAO).execute(ps); }

    public void updatePS(Password_Storage ps) { new updateAsyncTask(psDAO).execute(ps); }

    public void deletePS (Password_Storage ps) { new deleteAsyncTask(psDAO).execute(ps); }

    public static class insertAsyncTask extends AsyncTask<Password_Storage, Void, Void> {

        private PasswordStorageDAO mAsyncTaskDAO;

        public insertAsyncTask(PasswordStorageDAO mAsyncTaskDAO) {
            this.mAsyncTaskDAO = mAsyncTaskDAO;
        }

        @Override
        protected Void doInBackground(Password_Storage... ps) {
            mAsyncTaskDAO.addPS(ps[0]);
            return null;
        }
    }

    public static class updateAsyncTask extends AsyncTask<Password_Storage, Void, Void> {

        private PasswordStorageDAO mAsyncTaskDAO;

        public updateAsyncTask(PasswordStorageDAO mAsyncTaskDAO) {
            this.mAsyncTaskDAO = mAsyncTaskDAO;
        }

        @Override
        protected Void doInBackground(Password_Storage... ps) {
            mAsyncTaskDAO.updatePS(ps[0]);
            return null;
        }
    }

    public static class deleteAsyncTask extends AsyncTask<Password_Storage, Void, Void> {

        private PasswordStorageDAO mAsyncTaskDAO;

        public deleteAsyncTask(PasswordStorageDAO mAsyncTaskDAO) {
            this.mAsyncTaskDAO = mAsyncTaskDAO;
        }

        @Override
        protected Void doInBackground(Password_Storage... ps) {
            mAsyncTaskDAO.deletePS(ps[0]);
            return null;
        }
    }
}
