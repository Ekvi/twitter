package com.ekvilan.onixtwitter.views.fragments;


import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ekvilan.onixtwitter.R;
import com.ekvilan.onixtwitter.controllers.TweetsController;
import com.ekvilan.onixtwitter.views.VerticalViewPager;
import com.ekvilan.onixtwitter.views.adapters.SingleTweetAdapter;

import java.util.ArrayList;
import java.util.List;

public class TweetContainerFragment extends Fragment  {
    private ImageView switcher;
    private VerticalViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tweets_container, container, false);

        initToolBar(view);
        addListeners();

        viewPager = (VerticalViewPager)view.findViewById(R.id.viewpager);
        SingleTweetAdapter adapter = new SingleTweetAdapter(getChildFragmentManager(), getFragments());
        viewPager.setAdapter(adapter);

        return view;
    }

    private void initToolBar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.home_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView textView = (TextView) toolbar.findViewById(R.id.titleToolbar);
        textView.setText(getResources().getString(R.string.title_home));
        switcher = (ImageView) toolbar.findViewById(R.id.switcher);

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.switcher_on, null);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
            switcher.setBackgroundDrawable(drawable);
        }else{
            switcher.setBackground(drawable);
        }
    }

    private List<Fragment> getFragments(){
        List<Fragment> fragments = new ArrayList<>();
        TweetsController controller = TweetsController.getInstance();

        for(int i = 0; i < controller.getTweets().size(); i++) {
            fragments.add(SingleTweetFragment.newInstance(i));
        }
        return fragments;
    }

    private void addListeners() {
        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTweetsList();
            }
        });
    }

    private void showTweetsList() {
        Fragment homeFragment = new HomeFragment();
        HomeContainerFragment containerFragment = (HomeContainerFragment) getParentFragment();
        containerFragment.replaceFragment(homeFragment, true);
    }

    public VerticalViewPager getViewPager() {
        return viewPager;
    }
}
