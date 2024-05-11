package com.example.exenote.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.exenote.ViewModel.NoteViewModel;
import com.example.exenote.Utils.ButtonClikacble;
import com.example.exenote.DB.Note;
import com.example.exenote.Adapters.NoteListAdapter;

import com.example.exenote.R;
import com.example.exenote.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment_TAG";
    NoteViewModel noteViewModel;
    RecyclerView recyclerView;
    NoteListAdapter noteListAdapter;
    List<Note> noteList;
    Button btnAdd;
    ButtonClikacble buttonClickable;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noteList = new ArrayList<>();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof ButtonClikacble) {
            buttonClickable = (ButtonClikacble) context;
        }

    }

    @Override
    public void onDetach() {
        buttonClickable = null;
        compositeDisposable.dispose();
        super.onDetach();

    }

    @SuppressLint({"NotifyDataSetChanged", "CheckResult"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        noteViewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        btnAdd = view.findViewById(R.id.buttonAdd);

        btnAdd.setOnClickListener((View v)->{buttonClickable.onButtonClick(Utils.BTN_ADD);});

        noteViewModel.getAllNote().observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                Log.d(TAG, "Data: Changed" + notes);
                noteListAdapter = new NoteListAdapter(notes, requireActivity().getSupportFragmentManager(), noteViewModel);
                recyclerView.setAdapter(noteListAdapter);
                noteListAdapter.setData(notes);
            }
        });

//        Disposable disposable = noteViewModel.getAllNote().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<Note>>() {
//                    @Override
//                    public void accept(List<Note> noteList) throws Throwable {
//                        Log.d(TAG, "accept: Called");
//
//
//                    }
//                });
//
//        compositeDisposable.add(disposable);

//        noteViewModel.state.observe(getViewLifecycleOwner(), new Observer<State>() {
//
//            @Override
//            public void onChanged(State state) {
//                switch (state) {
//                    case COMPLETED -> {
//                        noteViewModel.state.observe(getViewLifecycleOwner(), value -> {
//
//                        });
//                    }
//                    case SUBSCRIBED -> {
//
//                    }
//                    case ERROR-> {
//
//                    }
//                }
//            }
//        });


        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getActivity().finish();
            }
        });

        return view;
    }


//    public void setDataToRecyclerView(List<Note> note) {
//        noteListAdapter = new NoteListAdapter(note, requireActivity().getSupportFragmentManager(), noteViewModel);
//        recyclerView.setAdapter(noteListAdapter);
//    }


}