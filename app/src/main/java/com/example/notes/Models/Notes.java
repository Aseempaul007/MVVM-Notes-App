package com.example.notes.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notes")
public class Notes implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int ID = 0;

    @ColumnInfo(name = "title")
    private String title = "";

    @ColumnInfo(name = "description")
    private String description = "";

    @ColumnInfo(name = "date")
    private String date = "";

    @ColumnInfo(name = "pinned")
    private boolean pinned = false;

    public Notes(int ID, String title, String description, String date, boolean pinned) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.date = date;
        this.pinned = pinned;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }
}
