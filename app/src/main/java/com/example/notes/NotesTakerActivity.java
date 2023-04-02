package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notes.Database.RoomDB;
import com.example.notes.Models.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesTakerActivity extends AppCompatActivity {

    EditText editText_notes,editText_title;
    ImageView imageView_save;

    Notes note;

    boolean isOldNote = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);

        editText_notes = findViewById(R.id.editText_notes);
        editText_title = findViewById(R.id.editText_title);
        imageView_save = findViewById(R.id.imageView_save);


        note = new Notes();
        try {
        note = (Notes)getIntent().getSerializableExtra("old_data");
        editText_title.setText(note.getTitle());
        editText_notes.setText(note.getDescription());

        isOldNote = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isOldNote){
                    note = new Notes();
                }
                String title = editText_title.getText().toString();
                String description = editText_notes.getText().toString();
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
    }
}