package com.ekvilan.onixtwitter.views.fragments;


import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ekvilan.onixtwitter.R;
import com.ekvilan.onixtwitter.controllers.TweetsController;
import com.ekvilan.onixtwitter.models.Tweet;
import com.ekvilan.onixtwitter.views.adapters.TweetsAdapter;

import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private ImageView switcher;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initToolBar(view);
        initView(view);
        addListeners();

        TweetsController controller = TweetsController.getInstance();
        setUpTweetsList(controller.getTweets());

        return view;
    }

    private void initToolBar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.home_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView textView = (TextView) toolbar.findViewById(R.id.titleToolbar);
        textView.setText(getResources().getString(R.string.title_home));

        switcher = (ImageView)view.findViewById(R.id.switcher);
        Drawable image = ResourcesCompat.getDrawable(getResources(), R.drawable.switcher_off, null);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
            switcher.setBackgroundDrawable(image);
        }else{
            switcher.setBackground(image);
        }
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    private void setUpTweetsList(List<Tweet> tweets) {
        recyclerView.setAdapter(new TweetsAdapter(getActivity(), tweets, false));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void addListeners() {
        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleTweet();
            }
        });
    }

    private void showSingleTweet() {
        Fragment singleFragment = new SingleTweetFragment();
        HomeContainerFragment containerFragment = (HomeContainerFragment) getParentFragment();
        containerFragment.replaceFragment(singleFragment, true);
    }
}
