package com.example.exenote.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.exenote.DB.Note;
import com.example.exenote.Repository.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    public NoteRepository noteRepository;

    public NoteViewModel(@androidx.annotation.NonNull Application application) {
        super(application);

        noteRepository = new NoteRepository(application);
    }

    public LiveData<List<Note>> getAllNote() {
        return noteRepository.getAllNote();
    }

    public LiveData<Note> getNoteById(int noteId) {
        return noteRepository.getNoteById(noteId);
    }


    public MutableLiveData<Boolean> getIsLoading() {
        return noteRepository.getIsLoading();
    }

    public void insertNote(Note note) {
        noteRepository.insertNote(note);
    }
    public void updateNote(Note note) {
        noteRepository.updateNote(note);
    }

    public void deleteNote(Note note) {
        noteRepository.deleteNote(note);
    }

}
