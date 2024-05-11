package com.example.exenote.DB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "note")
public class Note {
    @PrimaryKey(autoGenerate = true)
    int nid;
    @ColumnInfo(name = "note_title")
    public String noteTitle;
    @ColumnInfo(name = "note_description")
    public String noteDescription;

    @Ignore
    public Note() {

    }

    public Note(String noteTitle, String noteDescription) {
        this.nid = 0;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }
}
