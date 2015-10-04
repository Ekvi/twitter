package com.ekvilan.onixtwitter.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekvilan.onixtwitter.R;


public class ProfileFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d("my", "Profile Fragment");
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}
