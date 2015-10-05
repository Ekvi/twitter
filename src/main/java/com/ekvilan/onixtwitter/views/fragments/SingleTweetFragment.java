package com.ekvilan.onixtwitter.views.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ekvilan.onixtwitter.R;
import com.ekvilan.onixtwitter.controllers.TweetsController;
import com.ekvilan.onixtwitter.models.Tweet;
import com.ekvilan.onixtwitter.views.adapters.TweetsAdapter;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class SingleTweetFragment extends Fragment {
    private TweetsController controller = TweetsController.getInstance();
    //private RecyclerView recyclerView;

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

        Log.d("my", "hello from single fragment");

        View view = inflater.inflate(R.layout.fragment_single_tweet, container, false);


        initToolBar(view);

        addListeners();


        tvName = (TextView) view.findViewById(R.id.name);
        tvScreenName = (TextView) view.findViewById(R.id.screenName);
        tvDate = (TextView) view.findViewById(R.id.date);
        tvMessage = (TextView) view.findViewById(R.id.message);
        avatar = (ImageView) view.findViewById(R.id.avatar);
        image = (ImageView) view.findViewById(R.id.image);

        Tweet tweet = controller.getTweets().get(1);

        //avatar.setImageBitmap();

        new ImageTask(avatar).execute(tweet.getUserImage());
        if(!tweet.getImageUrl().isEmpty()) {
            new ImageTask(image).execute(tweet.getImageUrl());
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            image.setVisibility(View.VISIBLE);
        } else {
            image.setVisibility(View.GONE);
        }
        /*new ImageTask(avatar).execute(tweet.getUserImage());
        if(!tweet.getImageUrl().isEmpty()) {
            new ImageTask(image).execute(tweet.getImageUrl());
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            image.setVisibility(View.VISIBLE);
        } else {
            image.setVisibility(View.GONE);
        }
        tvName.setText(tweet.getName());
        tvScreenName.setText("@" + tweet.getScreenName());
        tvDate.setText(tweet.getDate().toString().substring(0, 3));
        tvMessage.setText(tweet.getMessage());*/
        tvName.setText(tweet.getName());
        tvScreenName.setText("@" + tweet.getScreenName());
        tvDate.setText(tweet.getDate().toString().substring(0, 3));
        tvMessage.setText(tweet.getMessage());






        //initView(view);
        //addListeners();
        //setUpTweetsList(controller.getTweets());

        return view;
    }

    private void initToolBar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.home_toolbar);
        //Log.d("my", "toolbar " + toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView textView = (TextView) toolbar.findViewById(R.id.titleToolbar);
        textView.setText(getResources().getString(R.string.title_home));
        switcher = (ImageView) toolbar.findViewById(R.id.switcher);
        //switcher.setImageBitmap();

        Drawable image = ResourcesCompat.getDrawable(getResources(), R.drawable.switcher_on, null);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
            switcher.setBackgroundDrawable(image);
        }else{
            switcher.setBackground(image);
        }
    }

    /*private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    private void setUpTweetsList(List<Tweet> tweets) {
        recyclerView.setAdapter(new TweetsAdapter(getActivity(), tweets, true));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }*/

    private void addListeners() {
        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTweetsList();
            }
        });
    }

    private void showTweetsList() {
        /*Fragment singleTweetFragment = new SingleTweetFragment();
        //FragmentTransaction transaction = getFragmentManager().beginTransaction();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.realTabContent, singleTweetFragment);
        transaction.addToBackStack(null);
        transaction.commit();*/

        Fragment homeFragment = new HomeFragment();
        //((BaseContainerFragment)getParentFragment()).replaceFragment(fragment, true);
        HomeContainerFragment home = (HomeContainerFragment) getParentFragment();
        Log.d("my", "home parent " + home);
        home.replaceFragment(homeFragment, true);
        ///((HomeFragment) getParentFragment())
    }

    private class ImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView image;

        public ImageTask(ImageView image) {
            this.image = image;
        }

        protected Bitmap doInBackground(String... urls) {
            Bitmap mIcon11 = null;
            try {
                InputStream in = new URL(urls[0]).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("er", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            image.setImageBitmap(result);
        }
    }
}
