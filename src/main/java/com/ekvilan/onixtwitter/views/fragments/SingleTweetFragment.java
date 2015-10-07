package com.ekvilan.onixtwitter.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ekvilan.onixtwitter.R;
import com.ekvilan.onixtwitter.controllers.TweetsController;
import com.ekvilan.onixtwitter.models.Tweet;
import com.ekvilan.onixtwitter.utils.DateUtils;
import com.ekvilan.onixtwitter.utils.DownloadImageTask;


public class SingleTweetFragment extends Fragment {
    private static final String POSITION = "POSITION";
    private ImageView avatar;
    private ImageView image;
    private TextView tvName;
    private TextView tvScreenName;
    private TextView tvDate;
    private TextView tvMessage;
    private ImageView btnUp;
    private ImageView btnDown;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_single_tweet, container, false);

        initView(view);
        addListeners();

        TweetsController controller = TweetsController.getInstance();
        showTweet(controller.getTweets().get(getArguments().getInt(POSITION)));

        return view;
    }

    private void initView(View view) {
        tvName = (TextView) view.findViewById(R.id.name);
        tvScreenName = (TextView) view.findViewById(R.id.screenName);
        tvDate = (TextView) view.findViewById(R.id.date);
        tvMessage = (TextView) view.findViewById(R.id.message);
        avatar = (ImageView) view.findViewById(R.id.avatar);
        image = (ImageView) view.findViewById(R.id.image);
        btnUp = (ImageView) view.findViewById(R.id.btnUp);
        btnDown = (ImageView) view.findViewById(R.id.btnDown);
    }

    private void showTweet(Tweet tweet) {
        new DownloadImageTask(avatar).execute(tweet.getUserImage());
        setUpImage(tweet);

        tvName.setText(tweet.getName());
        tvScreenName.setText("@" + tweet.getScreenName());
        tvDate.setText(DateUtils.formatDate(getActivity(), tweet.getDate()));
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
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TweetContainerFragment parent = (TweetContainerFragment) getParentFragment();
                showNextTweet(parent, parent.getViewPager().getCurrentItem() - 1);
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TweetContainerFragment parent = (TweetContainerFragment) getParentFragment();
                showNextTweet(parent, parent.getViewPager().getCurrentItem() + 1);
            }
        });
    }

    public static SingleTweetFragment newInstance(int position) {
        SingleTweetFragment fragment = new SingleTweetFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void showNextTweet(TweetContainerFragment parent, int position) {
        parent.getViewPager().setCurrentItem(position);
    }
}
