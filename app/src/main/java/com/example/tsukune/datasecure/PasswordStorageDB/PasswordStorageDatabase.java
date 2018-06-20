package com.example.tsukune.datasecure.PasswordStorageDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.example.tsukune.datasecure.Entity.Password_Storage;

@Database(entities = Password_Storage.class, version = 1)
public abstract class PasswordStorageDatabase extends RoomDatabase {

    public abstract PasswordStorageDAO psDAO();

    public static PasswordStorageDatabase psInstance;

    public static PasswordStorageDatabase getInstance(Context context) {

        if (psInstance == null) {
            psInstance = Room.databaseBuilder(context, PasswordStorageDatabase.class, "Password_Storage_DB").build();
        }
        return psInstance;
    }
}
