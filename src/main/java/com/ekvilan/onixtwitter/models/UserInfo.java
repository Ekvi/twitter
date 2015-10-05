package com.ekvilan.onixtwitter.models;


public class UserInfo {
    private String userName;
    private String screenName;
    private String profileBackgroundImage;
    private String userIcon;
    private int tweets;
    private int followers;
    private int friends;
    private int favourite;

    public UserInfo(String userName, String screenName, String profileBackgroundImage,
                    String userIcon, int tweets, int followers, int friends, int favourite) {
        this.userName = userName;
        this.screenName = screenName;
        this.profileBackgroundImage = profileBackgroundImage;
        this.userIcon = userIcon;
        this.tweets = tweets;
        this.followers = followers;
        this.friends = friends;
        this.favourite = favourite;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getProfileBackgroundImage() {
        return profileBackgroundImage;
    }

    public void setProfileBackgroundImage(String profileBackgroundImage) {
        this.profileBackgroundImage = profileBackgroundImage;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public int getTweets() {
        return tweets;
    }

    public void setTweets(int tweets) {
        this.tweets = tweets;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFriends() {
        return friends;
    }

    public void setFriends(int friends) {
        this.friends = friends;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }
}
