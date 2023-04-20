package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notes.Database.RoomDB;
import com.example.notes.Models.Notes;
import com.example.notes.databinding.ActivityNotesTakerBinding;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesTakerActivity extends AppCompatActivity {

    ActivityNotesTakerBinding notesBinding;

    Notes note;

    boolean isOldNote = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notesBinding = ActivityNotesTakerBinding.inflate(getLayoutInflater());
        setContentView(notesBinding.getRoot());

        note = new Notes();
        try {
        note = (Notes)getIntent().getSerializableExtra("old_data");
        notesBinding.editTextTitle.setText(note.getTitle());
        notesBinding.editTextNotes.setText(note.getDescription());

        isOldNote = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        notesBinding.imageViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isOldNote){
                    note = new Notes();
                }
                String title = notesBinding.editTextTitle.getText().toString();
                String description = notesBinding.editTextNotes.getText().toString();
                String creDate;

                if(description.isEmpty() || title.isEmpty()){
                    Toast.makeText(
                            NotesTakerActivity.this,
                            "Please provide Title and Description",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                creDate = simpleDateFormat.format(date);




                note.setTitle(title);
                note.setDescription(description);
                note.setDate(creDate);

                Intent i = new Intent();
                i.putExtra("note",note);
                setResult(Activity.RESULT_OK,i);
                finish();
            }
        });

        notesBinding.imageViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NotesTakerActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}