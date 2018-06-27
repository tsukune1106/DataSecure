package com.example.tsukune.datasecure.PasswordStorageDB;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.example.tsukune.datasecure.Entity.Password_Storage;
import java.util.List;

@Dao
public interface PasswordStorageDAO {

    @Query ("SELECT * FROM Password_Storage ORDER BY PS_Name ASC")
    LiveData<List<Password_Storage>> getAllPS();

    @Query ("SELECT * FROM Password_Storage WHERE PS_Name = :ps_name")
    Password_Storage findByPSName (String ps_name);

    @Query("DELETE FROM Password_Storage WHERE id = :id")
    void deletePSbyID (int id);

    @Insert
    void addPS(Password_Storage ps);

    @Update
    void updatePS (Password_Storage ps);

    @Delete
    void deletePS (Password_Storage ps);
}
