package com.ekvilan.onixtwitter.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ekvilan.onixtwitter.R;
import com.ekvilan.onixtwitter.controllers.TweetsController;
import com.ekvilan.onixtwitter.models.UserInfo;
import com.ekvilan.onixtwitter.utils.DownloadImageTask;



public class ProfileFragment extends Fragment {
    private TweetsController controller = TweetsController.getInstance();

    private ImageView profileBackground;
    private ImageView userIcon;
    private TextView name;
    private TextView screenName;
    private TextView tweets;
    private TextView followers;
    private TextView friends;
    private TextView favourites;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initToolBar(view);
        initView(view);

        setUpData(controller.getUserInfo());

        return view;
    }

    private void initToolBar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView textView = (TextView) toolbar.findViewById(R.id.titleToolbar);
        textView.setText(getResources().getString(R.string.title_profile));
    }

    private void initView(View view) {
        profileBackground = (ImageView) view.findViewById(R.id.profile_background);
        userIcon = (ImageView) view.findViewById(R.id.user_icon);
        name = (TextView) view.findViewById(R.id.userName);
        screenName = (TextView) view.findViewById(R.id.screenName);
        tweets = (TextView) view.findViewById(R.id.tweets);
        followers = (TextView) view.findViewById(R.id.followers);
        friends = (TextView) view.findViewById(R.id.friends);
        favourites = (TextView) view.findViewById(R.id.favourites);
    }

    private void setUpData(UserInfo userInfo) {
        new DownloadImageTask(profileBackground).execute(userInfo.getProfileBackgroundImage());
        profileBackground.setScaleType(ImageView.ScaleType.FIT_XY);
        new DownloadImageTask(userIcon).execute(userInfo.getUserIcon());

        name.setText(userInfo.getUserName());
        screenName.setText("@" + userInfo.getScreenName());

        tweets.setText(getActivity().getResources()
                .getString(R.string.tweets) + " " + userInfo.getTweets());
        followers.setText(getActivity().getResources()
                .getString(R.string.followers) + " " + userInfo.getFollowers());
        friends.setText(getActivity().getResources()
                .getString(R.string.friends) + " " + userInfo.getFriends());
        favourites.setText(getActivity().getResources()
                .getString(R.string.favourite) + " " + userInfo.getFavourite());
    }
}

