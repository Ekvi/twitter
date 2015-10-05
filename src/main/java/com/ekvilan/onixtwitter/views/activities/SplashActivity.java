package com.ekvilan.onixtwitter.views.activities;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.ekvilan.onixtwitter.R;
import com.ekvilan.onixtwitter.controllers.TweetsController;
import com.ekvilan.onixtwitter.models.Tweet;
import com.ekvilan.onixtwitter.models.UserInfo;

import java.util.ArrayList;
import java.util.List;

import twitter4j.MediaEntity;
import twitter4j.Paging;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;


public class SplashActivity extends Activity {
    private static String CONSUMER_KEY = "nH90d2WQmSjMlZCehCLwVWim3";
    private static String CONSUMER_SECRET = "vnfiVUW8sufa3I3xqS1HRwYgiFgarRpDmp7urvZlfoKJCIUHdw";

    private SharedPreferences pref;

    private ImageView login;
    private Dialog authDialog;
    private WebView webView;

    private Twitter twitter;
    private RequestToken requestToken = null;
    private AccessToken accessToken;
    private String oauthUrl;
    private String oauthVerifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        saveKeys();

        login = (ImageView) findViewById(R.id.login);

        twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(
                pref.getString("CONSUMER_KEY", ""), pref.getString("CONSUMER_SECRET", ""));

        addListeners();
    }

    private void saveKeys() {
        pref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("CONSUMER_KEY", CONSUMER_KEY);
        edit.putString("CONSUMER_SECRET", CONSUMER_SECRET);
        edit.commit();
    }

    private void addListeners() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginAsyncTask().execute();
            }
        });
    }

    private class LoginAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {
            try {
                requestToken = twitter.getOAuthRequestToken();
                oauthUrl = requestToken.getAuthorizationURL();
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return oauthUrl;
        }
        @Override
        protected void onPostExecute(String oauth_url) {
            if(oauth_url != null){
                authDialog = new Dialog(SplashActivity.this);
                authDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                authDialog.setContentView(R.layout.auth_dialog);

                webView = (WebView)authDialog.findViewById(R.id.webView);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(oauth_url);
                webView.setWebViewClient(new WebViewClient() {
                    boolean authComplete = false;

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon){
                        super.onPageStarted(view, url, favicon);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);

                        if (url.contains("oauth_verifier") && !authComplete){
                            authComplete = true;
                            Uri uri = Uri.parse(url);
                            oauthVerifier = uri.getQueryParameter("oauth_verifier");
                            authDialog.dismiss();
                            new AccessTwitterAsyncTask().execute();
                        }else if(url.contains("denied")){
                            authDialog.dismiss();
                            Toast.makeText(SplashActivity.this,
                                    "Sorry !, Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                authDialog.show();
                authDialog.setCancelable(true);
            }else{
                Toast.makeText(SplashActivity.this,
                        "Sorry !, Network Error or Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class AccessTwitterAsyncTask extends AsyncTask<String, String, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            createProgressDialog(SplashActivity.this);
        }

        @Override
        protected Boolean doInBackground(String... args) {
            try {
                accessToken = twitter.getOAuthAccessToken(requestToken, oauthVerifier);

                SharedPreferences.Editor edit = pref.edit();
                edit.putString("ACCESS_TOKEN", accessToken.getToken());
                edit.putString("ACCESS_TOKEN_SECRET", accessToken.getTokenSecret());

                Paging paging = new Paging();
                //paging.setCount(200);
                paging.setCount(20);

                User owner = twitter.showUser(accessToken.getUserId());
                List<twitter4j.Status> statuses = twitter.getUserTimeline(owner.getName(), paging);

                List<Tweet> tweets = createTweetsList(statuses);
                UserInfo userInfo = new UserInfo(owner.getName(), owner.getScreenName(),
                        owner.getProfileBannerMobileRetinaURL(), owner.getProfileImageURL(),
                        owner.getStatusesCount(), owner.getFollowersCount(),
                        owner.getFriendsCount(), owner.getFavouritesCount());

                TweetsController controller = TweetsController.getInstance();
                controller.saveTweets(tweets);
                controller.saveUserInfo(userInfo);

            } catch (TwitterException e) {
                Log.e("er", e.getMessage());
                e.printStackTrace();
            }

            return true;
        }
        @Override
        protected void onPostExecute(Boolean response) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private List<Tweet> createTweetsList(List<twitter4j.Status> statuses) {
        List<Tweet> tweets = new ArrayList<>();

        for (int i = 0; i < statuses.size(); i++) {
            twitter4j.Status status = statuses.get(i);
            User user = status.getUser();

            MediaEntity[] media = status.getMediaEntities();
            String imageUrl = media.length > 0 ? media[0].getMediaURL() : "";

            tweets.add(new Tweet(
                    user.getName(), user.getScreenName(), user.getProfileImageURL(),
                    status.getText(), imageUrl, status.getCreatedAt()));
        }

        return tweets;
    }

    private ProgressDialog createProgressDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.show();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progress_dialog);
        return dialog;
    }
}
