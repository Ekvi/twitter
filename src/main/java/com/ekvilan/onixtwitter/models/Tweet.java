package com.ekvilan.onixtwitter.models;


import java.util.Date;

public class Tweet {
    private String name;
    private String screenName;
    private String userImage;
    private String message;
    private String imageUrl;
    private Date date;

    public Tweet(String name, String screenName,
                 String userImage, String message, String imageUrl, Date date) {
        this.name = name;
        this.screenName = screenName;
        this.userImage = userImage;
        this.message = message;
        this.imageUrl = imageUrl;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
