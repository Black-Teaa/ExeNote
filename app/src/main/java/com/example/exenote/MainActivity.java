package com.example.exenote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import com.example.exenote.DB.Note;
import com.example.exenote.Fragments.RecordingFragment;
import com.example.exenote.Utils.ButtonClikacble;

public class MainActivity extends AppCompatActivity implements ButtonClikacble {
    private static final String TAG = "MainActivity_TAG";
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void onButtonClick(String btnText) {
        Fragment recordFragment = new RecordingFragment();
        navigateToFragment(recordFragment);

    }

    public void navigateToFragment(Fragment fragment){
        fragmentManager.beginTransaction()
                .replace(R.id.container,fragment)
                .addToBackStack(null)
                .commit();
    }
}