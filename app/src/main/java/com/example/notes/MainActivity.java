 package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.example.notes.Adapters.NotesViewHolder;
import com.example.notes.Database.MainDao;
import com.example.notes.Database.RoomDB;
import com.example.notes.Models.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

 public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NotesViewHolder notesListAdapter;
    RoomDB database;
    List<Notes> notes = new ArrayList<Notes>();
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_home);
        fab = findViewById(R.id.fab_add);

        database = RoomDB.getInstance(this);

        notes = database.mainDao().getAll();

        updateRecycler(notes);
    }

     private void updateRecycler(List<Notes> notes) {

        recyclerView.hasFixedSize();
        notesListAdapter = new NotesViewHolder(notes,MainActivity.this,notesClickListner);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(notesListAdapter);
     }

     private final NotesClick notesClickListner= new NotesClick() {
         @Override
         public void onClick(Notes notes) {

         }

         @Override
         public void onLongClick(Notes notes, CardView cardView) {

         }
     };

 }