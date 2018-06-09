package com.example.tsukune.datasecure.LocalDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.example.tsukune.datasecure.Entity.User;
import java.util.List;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM User")
    List<User> getUser();

    @Insert
    void addUser(User user);

    @Update
    void updateUser (User user);

}
