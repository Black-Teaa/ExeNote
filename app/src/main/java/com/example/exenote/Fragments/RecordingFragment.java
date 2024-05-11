package com.example.exenote.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.exenote.CreateNoteListener;
import com.example.exenote.DB.Note;
import com.example.exenote.DeleteNoteListener;
import com.example.exenote.NoteViewModel;
import com.example.exenote.R;
import com.example.exenote.UpdateNoteListener;

public class RecordingFragment extends Fragment implements CreateNoteListener, UpdateNoteListener, DeleteNoteListener {
    private static final String TAG = "RecordingFragment_TAG";
    NoteViewModel noteViewModel;
    EditText editTitle, editDescription;
    Button btnSave,btnUpdate;
    CreateNoteListener noteListener;
    UpdateNoteListener updateNoteListener;
    DeleteNoteListener deleteNoteListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof CreateNoteListener && context instanceof UpdateNoteListener  && context instanceof  DeleteNoteListener) {
            noteListener = (CreateNoteListener) context;
            updateNoteListener = (UpdateNoteListener) context;
            deleteNoteListener = (DeleteNoteListener) context;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recording, container, false);

        btnSave = view.findViewById(R.id.btnSave);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        editTitle = view.findViewById(R.id.editTitle);
        editDescription = view.findViewById(R.id.editDescription);

        noteViewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);

        Bundle bundle=this.getArguments();
        if (bundle!=null) {
            int id = bundle.getInt("id");

            noteViewModel.getNoteById(id).observe(getViewLifecycleOwner(), new Observer<Note>() {
                @Override
                public void onChanged(Note note) {
                    if (note != null) {
                        editTitle.setText(note.getNoteTitle());
                        editDescription.setText(note.getNoteDescription());

                        btnUpdate.setVisibility(View.VISIBLE);
                        btnSave.setVisibility(View.GONE);


                        btnUpdate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MainFragment mainFragment = new MainFragment();
                                String updatedTitle = editTitle.getText().toString();
                                String updatedDescription = editDescription.getText().toString();

                                Note updatedNote = new Note(updatedTitle, updatedDescription);
                                insertNewNote(updatedNote);
                                deleteNote(note);
//                                updatedNote(updatedNote);

                                if (getActivity() != null) {
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.container, mainFragment)
                                            .commit();
                                }


                            }
                        });

                    } else {
                        Log.d(TAG, "Bundle: null" + bundle);

                    }
                }
            });
            // В этом коде я получаю из Bundle id заметки, и по этому id мне нужно получить поля title and description
            //показать кнопку изменить
        }



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainFragment mainFragment = new MainFragment();
                String title = editTitle.getText().toString();
                String description = editDescription.getText().toString();

                Note nt1 = new Note(title, description);
                insertNewNote(nt1);

                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, mainFragment)
                            .commit();
                }
            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                MainFragment mainFragment = new MainFragment();
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, mainFragment)
                            .commit();
                }
            }
        });
    }


    @Override
    public void insertNewNote(Note note) {
        noteViewModel.insertNote(note);
    }
    @Override
    public void updatedNote(Note note) {
        Log.d(TAG, "NewNoteUpdated" + note.getNoteTitle());
        noteViewModel.updateNote(note);
        Log.d(TAG, "NewNoteDataUpdated" + note.getNoteTitle());
    }


    @Override
    public void deleteNote(Note note) {
        noteViewModel.deleteNote(note);
    }
}