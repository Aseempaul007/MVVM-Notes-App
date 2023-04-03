package com.example.notes.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.Models.Notes;
import com.example.notes.NotesClick;
import com.example.notes.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesViewHolder extends RecyclerView.Adapter<NotesViewHolder.NotesViewHoler> {

    List<Notes> list;
    Context context;
    NotesClick listener;

    public NotesViewHolder(List<Notes> list, Context context, NotesClick listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHoler(
                LayoutInflater.from(context).inflate(
                        R.layout.notes_list,parent,false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHoler holder, int position) {

        holder.textView_title.setText(list.get(position).getTitle());
        holder.textView_title.setSelected(true);

        holder.textView_notes.setText(list.get(position).getDescription());

        holder.textView_date.setText(list.get(position).getDate());
        holder.textView_date.setSelected(true);

        if(list.get(position).isPinned()){
            holder.imageView_pin.setImageResource(R.drawable.ic_pin_2);
        }else{
            holder.imageView_pin.setImageResource(0);
        }

        holder.notes_container.setCardBackgroundColor(
                holder.itemView.getResources().getColor(getRandomColor(),null)
        );
        holder.notes_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });

        holder.notes_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(list.get(holder.getAdapterPosition()),holder.notes_container);
                return true;
            }
        });
    }

    private int getRandomColor(){
        List<Integer> colorCode = new ArrayList<Integer>();
        Random random = new Random();

        colorCode.add(R.color.Color1);
        colorCode.add(R.color.Color2);
        colorCode.add(R.color.Color3);
        colorCode.add(R.color.Color4);
        colorCode.add(R.color.Color5);

        int color_code = random.nextInt(colorCode.size());
        return colorCode.get(color_code);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void filterList(List<Notes> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }



    public class NotesViewHoler extends RecyclerView.ViewHolder{

        CardView notes_container;
        TextView textView_title;
        TextView textView_notes;
        TextView textView_date;
        ImageView imageView_pin;

        public NotesViewHoler(@NonNull View itemView) {
            super(itemView);
            notes_container = itemView.findViewById(R.id.notes_container);
            textView_title = itemView.findViewById(R.id.textView_title);
            textView_notes = itemView.findViewById(R.id.textView_notes);
            imageView_pin = itemView.findViewById(R.id.imageView_pin);
            textView_date = itemView.findViewById(R.id.textView_date);
        }
    }
}
