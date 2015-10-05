package com.ekvilan.onixtwitter.views.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekvilan.onixtwitter.R;

public class HomeContainerFragment extends BaseContainerFragment {
    private boolean isViewInited;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("test", "tab 1 oncreateview");
        return inflater.inflate(R.layout.fragment_container, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("test", "tab 1 container on activity created");
        if (!isViewInited) {
            isViewInited = true;
            initView();
        }
    }

    private void initView() {
        Log.d("test", "tab 1 init view");
        replaceFragment(new HomeFragment(), true);
    }
}
