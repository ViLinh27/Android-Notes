package com.example.androidnotes;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private static final String TAG = "NotesAdapter";
    private final List<Notes> notesList;
    private final MainActivity main_activity;

    NotesAdapter(List<Notes> nList, MainActivity ma){
        this.notesList = nList;
        main_activity = ma;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: MAKING NEW MyViewHolder");

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_list_row, parent, false);

        itemView.setOnClickListener(main_activity);
        itemView.setOnLongClickListener(main_activity);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: FILLING VIEW HOLDER Notes " + position);

        Notes notes = notesList.get(position);

        holder.noteTitle.setText(notes.getNoteTitle());
        holder.dateTime.setText(new Date().toString());
        holder.notePart.setText(notes.getNotePart());
    }

    @Override
    public int getItemCount() {return notesList.size();}
}
