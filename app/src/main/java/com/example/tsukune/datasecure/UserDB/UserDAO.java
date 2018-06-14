package com.example.tsukune.datasecure.UserDB;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.example.tsukune.datasecure.Entity.User;
import java.util.List;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM User")
    LiveData<List<User>> getAllUser();

    @Query("SELECT COUNT(*) FROM User")
    int getCount();

    @Insert
    void addUser(User user);

    @Update
    void updateUser (User user);

    @Query("SELECT * FROM User LIMIT 1")
    User getAnyUser();

}
