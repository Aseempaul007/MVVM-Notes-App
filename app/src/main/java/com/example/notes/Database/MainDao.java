package com.example.notes.Database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

import com.example.notes.Models.Notes;

import java.util.List;

@Dao
public interface MainDao {

    @Insert(onConflict = REPLACE)
    void insert(Notes note);

    @Query("SELECT * FROM notes ORDER BY ID DESC")
    List<Notes> getAll();

    
}
