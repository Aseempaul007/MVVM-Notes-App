package com.example.notes.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notes.Models.Notes;

@Database(entities = Notes.class, exportSchema = false,version = 1)
public abstract class RoomDB extends RoomDatabase {

    private static RoomDB database;
    private static String DATABASE_NAME = "NotesApp";

    public static synchronized RoomDB getInstance(Context context){
        if(database==null){
            database = Room.databaseBuilder(
                    context.getApplicationContext(),
                    RoomDB.class,
                    DATABASE_NAME
            )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract MainDao mainDao();

}
