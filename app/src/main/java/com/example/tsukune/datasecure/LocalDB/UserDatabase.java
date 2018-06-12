package com.example.tsukune.datasecure.LocalDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.example.tsukune.datasecure.Entity.User;

@Database(entities = User.class, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDAO userDao();

    public static UserDatabase uInstance;

    public static UserDatabase getInstance(Context context) {

        if (uInstance == null) {
            uInstance = Room.databaseBuilder(context, UserDatabase.class, "User_DB").build();
        }
        return uInstance;
    }
}
