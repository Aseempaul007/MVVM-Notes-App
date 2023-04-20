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
import com.example.notes.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

 public class MainActivity extends AppCompatActivity {

     ActivityMainBinding mainXml;
    NotesViewHolder notesListAdapter;
    RoomDB database;
    List<Notes> notesList = new ArrayList<Notes>();


    Notes selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainXml = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainXml.getRoot());

        database = RoomDB.getInstance(this);

        notesList = database.mainDao().getAll();

        updateRecycler(notesList);

        mainXml.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(MainActivity.this,NotesTakerActivity.class);
                startActivityForResult(i,101);
            }
        });

        mainXml.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        mainXml.recyclerHome.hasFixedSize();
        notesListAdapter = new NotesViewHolder(notes,MainActivity.this,notesClickListner);
         mainXml.recyclerHome.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
         mainXml.recyclerHome.setAdapter(notesListAdapter);
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
                     }else{
                         database.mainDao().setPin(selectedNote.getID(),true);
                     }

                     notesList.clear();
                     notesList.addAll(database.mainDao().getAll());
                     notesListAdapter.notifyDataSetChanged();
                     return true;

                     case R.id.delete: database.mainDao().delete(selectedNote);
                     notesList.remove(selectedNote);
                     notesListAdapter.notifyDataSetChanged();
                     return true;

                     default: return false;

                 }

             }
         });
         popupMenu.inflate(R.menu.popup_menu);
         popupMenu.show();
     }

 }