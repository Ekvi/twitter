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
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.ekvilan.onixtwitter.R;
import com.ekvilan.onixtwitter.controllers.TweetsController;
import com.ekvilan.onixtwitter.models.Tweet;

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
    private ImageView rotateImage;
    private Dialog authDialog;
    private ProgressDialog progress;
    private WebView webView;

    private Twitter twitter;
    private RequestToken requestToken = null;
    private AccessToken accessToken;
    private String oauthUrl;
    private String oauthVerifier;
    //private String profileUrl;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        saveKeys();

        login = (ImageView) findViewById(R.id.login);
        //rotateImage = (ImageView) findViewById(R.id.rotateImage);

        pref = getPreferences(MODE_PRIVATE);
        twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(pref.getString("CONSUMER_KEY", ""), pref.getString("CONSUMER_SECRET", ""));


        addListeners();
    }


    private ProgressDialog createProgressDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        /*try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

        }*/
        dialog.show();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progress_dialog);
        // dialog.setMessage(Message);
        return dialog;
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
                            Toast.makeText(SplashActivity.this, "Sorry !, Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                authDialog.show();
                authDialog.setCancelable(true);
            }else{
                Toast.makeText(SplashActivity.this, "Sorry !, Network Error or Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class AccessTwitterAsyncTask extends AsyncTask<String, String, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /*progress = new ProgressDialog(SplashActivity.this);
            progress.setMessage("Fetching Data ...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();*/

            //rotate();
            createProgressDialog(SplashActivity.this);
        }

        @Override
        protected Boolean doInBackground(String... args) {
            List<Tweet> tweets = new ArrayList<>();
            try {
                accessToken = twitter.getOAuthAccessToken(requestToken, oauthVerifier);

                SharedPreferences.Editor edit = pref.edit();
                edit.putString("ACCESS_TOKEN", accessToken.getToken());
                edit.putString("ACCESS_TOKEN_SECRET", accessToken.getTokenSecret());

                Paging paging = new Paging();
                //paging.setCount(200);
                paging.setCount(20);

                //List<twitter4j.Status> statuses = twitter.getHomeTimeline(paging);
                User u = twitter.showUser(accessToken.getUserId());
                List<twitter4j.Status> statuses = twitter.getUserTimeline(u.getName(), paging);
                //List<twitter4j.Status> statuses = twitter.get;

                Log.d("my", statuses.size() + " Feeds");
                for (int i = 0; i < statuses.size(); i++) {
                    Log.d("my", "//////////////////////////////////////////////");
                    twitter4j.Status status = statuses.get(i);
                    //Log.d("Tweet Count " + (i + 1), status.getText() + "\n\n");
                    /*Log.d("my", "getInReplyToScreenName " + status.getInReplyToScreenName());
                    Log.d("my", "getCurrentUserRetweetId() " + status.getCurrentUserRetweetId());
                    Log.d("my", "getSource() " + status.getSource());*/
                    //Log.d("my", "getText() " + status.getText());
                    User user = status.getUser();
                    /*Log.d("my", "user " + friend.getName());
                    Log.d("my", "screenNAme " + friend.getScreenName());
                    Log.d("my", "image " + friend.getMiniProfileImageURL());
                    Log.d("my", "date " + status.getCreatedAt());*/

                    MediaEntity[] media = status.getMediaEntities();
                    String imageUrl = media.length > 0 ? media[0].getMediaURL() : "";


                    /*Log.d("my", "url = " + user.getProfileImageURL());
                    Log.d("my", "imageUrl  = " + imageUrl);*/
                    tweets.add(new Tweet(
                            user.getName(), user.getScreenName(), user.getProfileImageURL(),
                            status.getText(), imageUrl, status.getCreatedAt()));
                }



                /*profileUrl = user.getOriginalProfileImageURL();
                edit.putString("NAME", user.getName());
                edit.putString("IMAGE_URL", user.getOriginalProfileImageURL());
                edit.putString("DESCRIPTION", user.getDescription());
                edit.putString("LOCATION", user.getLocation());
                edit.putInt("FRIENDS_COUNT", user.getFriendsCount());
                edit.putInt("FOLLOWERS_COUNT", user.getFollowersCount());
                edit.putInt("FAVOURITES_COUNT", user.getFavouritesCount());

                edit.commit();*/

                //saveToSharedPrefs(tweets);
                TweetsController controller = TweetsController.getInstance();
                controller.saveTweets(tweets);


            } catch (TwitterException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return true;
        }
        @Override
        protected void onPostExecute(Boolean response) {
            Log.d("my", "onPostExecute() response " + response);
            /*if(response){
                progress.hide();
                Fragment profile = new ProfileFragment();
                FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, profile);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }*/
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        /*private void saveToSharedPrefs(List<Tweet> tweets) {
            *//*preferences = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(POSITION, lessons.getSelectedItemPosition());
            editor.commit();*//*
            Log.d("my", "saved tweets " + tweets.size());
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
            SharedPreferences.Editor prefsEditor = prefs.edit();

            Gson gson = new Gson();
            //String jsonTweets = gson.toJson(tweets);

            prefsEditor.putString("TWEETS", gson.toJson(tweets));
            prefsEditor.commit();
        }*/

    }
}
