package com.example.tsukune.datasecure.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Main_Password")
    private String mainPassword;

    @ColumnInfo(name = "Password_Storage")
    private String passwordStorage;

    @ColumnInfo(name = "File_Storage")
    private String fileStorage;

    public User(String mainPassword, String passwordStorage, String fileStorage) {
        this.mainPassword = mainPassword;
        this.passwordStorage = passwordStorage;
        this.fileStorage = fileStorage;
    }

    // <editor-fold defaultstate="collapsed" desc="Get, Set">
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMainPassword() {
        return mainPassword;
    }

    public void setMainPassword(String mainPassword) {
        this.mainPassword = mainPassword;
    }

    public String getPasswordStorage() {
        return passwordStorage;
    }

    public void setPasswordStorage(String passwordStorage) {
        this.passwordStorage = passwordStorage;
    }

    public String getFileStorage() {
        return fileStorage;
    }

    public void setFileStorage(String fileStorage) {
        this.fileStorage = fileStorage;
    }
    // </editor-fold>
}
