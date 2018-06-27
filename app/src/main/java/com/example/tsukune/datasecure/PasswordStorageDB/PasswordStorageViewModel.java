package com.example.tsukune.datasecure.PasswordStorageDB;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import com.example.tsukune.datasecure.Entity.Password_Storage;
import java.util.List;

public class PasswordStorageViewModel extends AndroidViewModel {

    private PasswordStorageRepository psRepository;
    private LiveData<List<Password_Storage>> mPS;

    public PasswordStorageViewModel(Application application) {
        super(application);
        this.psRepository = new PasswordStorageRepository(application);
        this.mPS = psRepository.getAllPS();
    }

    public LiveData<List<Password_Storage>> getAllPS() {
        return mPS;
    }

    public void addPS(Password_Storage ps) { psRepository.addPS(ps); }

    public void updatePS(Password_Storage ps) { psRepository.updatePS(ps); }

    public void deletePS (Password_Storage ps) { psRepository.deletePS(ps); }

    public void deletePSbyID (int id) { psRepository.deletePSbyID(id); }
}
