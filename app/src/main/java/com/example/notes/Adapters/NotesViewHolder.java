package com.example.notes.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.Models.Notes;
import com.example.notes.R;

import java.util.List;

public class NotesViewHolder extends RecyclerView.Adapter<NotesViewHolder.NotesViewHoler> {

    List<Notes> list;
    Context context;

    @NonNull
    @Override
    public NotesViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHoler holder, int position) {


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotesViewHoler extends RecyclerView.ViewHolder{

        CardView notes_container;
        TextView textView_title;
        TextView textView_notes;
        ImageView imageView_pin;

        public NotesViewHoler(@NonNull View itemView) {
            super(itemView);
            notes_container = itemView.findViewById(R.id.notes_container);
            textView_title = itemView.findViewById(R.id.textView_title);
            textView_notes = itemView.findViewById(R.id.textView_notes);
            imageView_pin = itemView.findViewById(R.id.imageView_pin);
        }
    }
}
