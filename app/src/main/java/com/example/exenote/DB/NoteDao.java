package com.example.exenote.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface NoteDao {
    @Insert
    void insertNote(Note note);
    @Update
    void updateNote(Note note);
    @Delete
    void deleteNote(Note note);
    @Query("SELECT * FROM note")
    LiveData<List<Note>> getAll();
    @Query("SELECT * FROM note WHERE nid = :noteId")
    Note getNoteById(int noteId);
}
