package com.example.tsukune.datasecure.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;
import com.example.tsukune.datasecure.Entity.User;

@Dao
public interface UserDAO {

    @Insert
    void addUser(User user);

    @Update
    void updateUser (User user);

}
