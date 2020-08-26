package com.kgtb32.myedt.ui.about;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.kgtb32.myedt.R;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class AboutFragment extends Fragment {




    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        return root;
    }
}