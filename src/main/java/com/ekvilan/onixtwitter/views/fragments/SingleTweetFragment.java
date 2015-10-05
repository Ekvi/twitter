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
import com.ekvilan.onixtwitter.models.Tweet;
import com.ekvilan.onixtwitter.utils.DownloadImageTask;


public class SingleTweetFragment extends Fragment {
    private ImageView avatar;
    private ImageView image;
    private TextView tvName;
    private TextView tvScreenName;
    private TextView tvDate;
    private TextView tvMessage;

    private ImageView switcher;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_single_tweet, container, false);

        initToolBar(view);
        initView(view);
        addListeners();

        TweetsController controller = TweetsController.getInstance();
        setUpData(controller.getTweets().get(0));

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

    private void initView(View view) {
        tvName = (TextView) view.findViewById(R.id.name);
        tvScreenName = (TextView) view.findViewById(R.id.screenName);
        tvDate = (TextView) view.findViewById(R.id.date);
        tvMessage = (TextView) view.findViewById(R.id.message);
        avatar = (ImageView) view.findViewById(R.id.avatar);
        image = (ImageView) view.findViewById(R.id.image);
    }

    private void setUpData(Tweet tweet) {
        new DownloadImageTask(avatar).execute(tweet.getUserImage());
        setUpImage(tweet);

        tvName.setText(tweet.getName());
        tvScreenName.setText("@" + tweet.getScreenName());
        tvDate.setText(tweet.getDate().toString().substring(0, 3));
        tvMessage.setText(tweet.getMessage());
    }

    private void setUpImage(Tweet tweet) {
        if(!tweet.getImageUrl().isEmpty()) {
            new DownloadImageTask(image).execute(tweet.getImageUrl());
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            image.setVisibility(View.VISIBLE);
        } else {
            image.setVisibility(View.GONE);
        }
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
}
