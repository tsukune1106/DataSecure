package com.example.tsukune.datasecure.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo (name = "Username")
    private String username;

    @ColumnInfo(name = "Main_Password")
    private String mainPassword;

    @ColumnInfo(name = "Password_Storage")
    private String passwordStorage;

    @ColumnInfo(name = "File_Storage")
    private String fileStorage;

    @ColumnInfo(name = "PS_EncryptionKey")
    private String ps_encryptionKey;

    @ColumnInfo(name = "FS_EncryptionKey")
    private String fs_encryptionKey;

    public User(String username, String mainPassword, String passwordStorage, String fileStorage, String ps_encryptionKey, String fs_encryptionKey) {
        this.username = username;
        this.mainPassword = mainPassword;
        this.passwordStorage = passwordStorage;
        this.fileStorage = fileStorage;
        this.ps_encryptionKey = ps_encryptionKey;
        this.fs_encryptionKey = fs_encryptionKey;
    }

    // <editor-fold defaultstate="collapsed" desc="Get, Set">
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPs_encryptionKey() {
        return ps_encryptionKey;
    }

    public void setPs_encryptionKey(String ps_encryptionKey) {
        this.ps_encryptionKey = ps_encryptionKey;
    }

    public String getFs_encryptionKey() {
        return fs_encryptionKey;
    }

    public void setFs_encryptionKey(String fs_encryptionKey) {
        this.fs_encryptionKey = fs_encryptionKey;
    }

    // </editor-fold>
}
