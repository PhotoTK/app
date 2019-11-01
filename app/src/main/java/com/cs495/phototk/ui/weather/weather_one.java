package com.cs495.phototk.ui.weather;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cs495.phototk.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class weather_one extends Fragment {

    static weather_one instance;

    public static weather_one getInstance() {
        if(instance == null)
            instance = new weather_one();
        return instance;
    }

    public weather_one() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_one, container, false);
    }

}
