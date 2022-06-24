package com.example.androidnotes;

import android.content.Intent;
import android.util.JsonWriter;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Date;

public class Notes implements Serializable{
    private final String noteTitle;
    private final String dateTime;

    private final String notePart;

    Notes(String noteTitle, String notePart){
        this.noteTitle = noteTitle;
        this.dateTime = java.text.DateFormat.getDateTimeInstance().format(new Date());
        this.notePart = notePart;

    }

    //atrributes needed in note single:
    //title of note
    //date
    //first 80 chars of note

    public String getNoteTitle(){ return noteTitle;}

    public String getDateTime(){ return dateTime;}

    public String getNotePart(){ return notePart;}

    @NonNull
    @Override
    public String toString(){
        try{
            StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = new JsonWriter(sw);
            jsonWriter.setIndent("  ");
            jsonWriter.beginObject();
            jsonWriter.name("title").value(getNoteTitle());
            jsonWriter.name("content").value(getNotePart());
            jsonWriter.endObject();
            jsonWriter.close();
            return sw.toString();
        }catch(IOException e){
            e.printStackTrace();
        }

        //return noteTitle+""+dateTime+""+notePart;
        return "";
    }
}
