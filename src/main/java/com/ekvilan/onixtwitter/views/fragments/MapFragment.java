package com.ekvilan.onixtwitter.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekvilan.onixtwitter.R;


public class MapFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d("my", "Map Fragment");
        return inflater.inflate(R.layout.fragment_map, container, false);
    }
}
