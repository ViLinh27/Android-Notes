package com.example.androidnotes;

import java.io.Serializable;

public class Data_Holder implements Serializable {
    private final String titleData;
    private final String noteData;

    Data_Holder(String titleData, String noteData){
        this.titleData = titleData;
        this.noteData = noteData;
    }

    String getTitleData() {return titleData;}
    String getNoteData() {return noteData;}
}
