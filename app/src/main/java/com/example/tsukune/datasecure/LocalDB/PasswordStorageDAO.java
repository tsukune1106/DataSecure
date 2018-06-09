package com.example.tsukune.datasecure.LocalDB;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.example.tsukune.datasecure.Entity.Password_Storage;
import java.util.List;

public interface PasswordStorageDAO {

    @Query ("SELECT * FROM Password_Storage")
    List<Password_Storage> getUser();

    @Query ("SELECT * FROM Password_Storage WHERE PS_Name = :ps_name")
    Password_Storage findByName (String ps_name);

    @Insert
    void addPS(Password_Storage ps);

    @Update
    void updatePS (Password_Storage ps);

    @Delete
    void deletePS (Password_Storage ps);
}
