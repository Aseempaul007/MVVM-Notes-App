 package com.example.notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.notes.Adapters.NotesViewHolder;
import com.example.notes.Database.MainDao;
import com.example.notes.Database.RoomDB;
import com.example.notes.Models.Notes;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

 public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NotesViewHolder notesListAdapter;
    RoomDB database;
    List<Notes> notesList = new ArrayList<Notes>();
     ExtendedFloatingActionButton fab;

    SearchView searchView;

    Notes selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_home);
        fab = findViewById(R.id.fab_add);
        searchView = findViewById(R.id.searchView);

        database = RoomDB.getInstance(this);

        notesList = database.mainDao().getAll();

        updateRecycler(notesList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(MainActivity.this,NotesTakerActivity.class);
                startActivityForResult(i,101);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterSearch(newText);
                return true;
            }
        });
    }

     private void filterSearch(String newText) {

        List<Notes> filteredList = new ArrayList<>();
        for(Notes currentNote : notesList){
            if(currentNote.getTitle().toString().toLowerCase().contains(newText.toLowerCase())
            || currentNote.getDescription().toString().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(currentNote);

            }
        }
         notesListAdapter.filterList(filteredList);
     }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

         if(requestCode==101 && resultCode== Activity.RESULT_OK){
             Notes newNote = (Notes)data.getSerializableExtra("note");
             database.mainDao().insert(newNote);
             notesList.clear();
             notesList.addAll(database.mainDao().getAll());
             notesListAdapter.notifyDataSetChanged();
         }

         if(requestCode==102 && resultCode== Activity.RESULT_OK){
             Notes newNote = (Notes)data.getSerializableExtra("note");
             database.mainDao().update(newNote.getID(),newNote.getTitle(),newNote.getDescription());
             notesList.clear();
             notesList.addAll(database.mainDao().getAll());
             notesListAdapter.notifyDataSetChanged();
         }



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

             Intent intent = new Intent(MainActivity.this,NotesTakerActivity.class);
             intent.putExtra("old_data",notes);
             startActivityForResult(intent,102);
         }

         @Override
         public void onLongClick(Notes notes, CardView cardView) {

             selectedNote=notes;
             showPopup(cardView);

         }
     };

     private void showPopup(CardView cardView) {
         PopupMenu popupMenu = new PopupMenu(this,cardView);
         popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
             @Override
             public boolean onMenuItemClick(MenuItem menuItem) {
                 switch (menuItem.getItemId()){
                     case R.id.pin: if(selectedNote.isPinned()){
                         database.mainDao().setPin(selectedNote.getID(),false);
                         Toast.makeText(MainActivity.this, "Unpinned", Toast.LENGTH_SHORT).show();
                     }else{
                         database.mainDao().setPin(selectedNote.getID(),true);
                         Toast.makeText(MainActivity.this, "Pinned", Toast.LENGTH_SHORT).show();
                     }

                     notesList.clear();
                     notesList.addAll(database.mainDao().getAll());
                     notesListAdapter.notifyDataSetChanged();
                     return true;
                 }
                 return false;
             }
         });
         popupMenu.inflate(R.menu.popup_menu);
         popupMenu.show();
     }

 }