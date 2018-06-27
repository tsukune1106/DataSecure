package com.example.tsukune.datasecure.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Password_Storage {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "PS_Name")
    private String ps_Name;

    @ColumnInfo(name = "PS_Password")
    private String ps_Password;

    public Password_Storage(String ps_Name, String ps_Password) {
        this.ps_Name = ps_Name;
        this.ps_Password = ps_Password;
    }

    @Ignore
    public Password_Storage(int id, String ps_Name, String ps_Password) {
        this.id = id;
        this.ps_Name = ps_Name;
        this.ps_Password = ps_Password;
    }

    // <editor-fold defaultstate="collapsed" desc="Get, Set">
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPs_Name() {
        return ps_Name;
    }

    public void setPs_Name(String ps_Name) {
        this.ps_Name = ps_Name;
    }

    public String getPs_Password() {
        return ps_Password;
    }

    public void setPs_Password(String ps_Password) {
        this.ps_Password = ps_Password;
    }
    // </editor-fold>
}
