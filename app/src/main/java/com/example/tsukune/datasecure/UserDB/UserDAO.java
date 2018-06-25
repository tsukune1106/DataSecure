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
//
//    @Query("SELECT * FROM User")
//    List<User> getUser();

    @Query("SELECT * FROM User")
    LiveData<List<User>> getAllUser();

    @Query("SELECT COUNT(*) FROM User")
    int getCount();

    @Insert
    void addUser(User user);

    @Update
    void updateUser (User user);

    @Query("UPDATE User SET Username = :username, Main_Password = :main_Password  WHERE id = :uid")
    void updateUserLogin(int uid, String username, String main_Password);

    @Query("UPDATE User SET Password_Storage = :ps_Password, PS_EncryptionKey= :ps_encryptionKey  WHERE id = :uid")
    void updatePS(int uid, String ps_Password, String ps_encryptionKey);

    @Query("UPDATE User SET File_Storage = :fs_Password, FS_EncryptionKey =:fs_encryptionKey WHERE id = :uid")
    void updateFS(int uid, String fs_Password, String fs_encryptionKey);
}
