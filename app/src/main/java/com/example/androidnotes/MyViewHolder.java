package com.example.androidnotes;
import android.widget.TextView;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
public class MyViewHolder extends RecyclerView.ViewHolder{

    public TextView noteTitle;
    TextView dateTime;
    TextView notePart;

    MyViewHolder(View view){
        super(view);
        noteTitle = view.findViewById(R.id.noteTitle);
        dateTime = view.findViewById(R.id.dateTime);
        notePart = view.findViewById(R.id.notePart);
    }
}
