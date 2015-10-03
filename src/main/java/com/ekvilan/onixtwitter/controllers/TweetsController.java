package com.ekvilan.onixtwitter.controllers;


import com.ekvilan.onixtwitter.models.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetsController {
    private static TweetsController instance;

    private List<Tweet> tweets = new ArrayList<>();

    private TweetsController() {}

    public static TweetsController getInstance() {
        if(instance == null) {
            instance = new TweetsController();
        }
        return instance;
    }

    public void saveTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }
}
