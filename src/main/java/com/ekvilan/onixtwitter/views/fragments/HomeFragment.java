package com.ekvilan.onixtwitter.views.fragments;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ekvilan.onixtwitter.R;
import com.ekvilan.onixtwitter.controllers.TweetsController;
import com.ekvilan.onixtwitter.models.Tweet;
import com.ekvilan.onixtwitter.views.adapters.TweetsAdapter;

import java.util.List;

public class HomeFragment extends Fragment {
    private TweetsController controller = TweetsController.getInstance();

    private RecyclerView recyclerView;
    private ImageView switcher;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //List<Tweet> tweets = controller.getTweets();

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initView(view);
        addListeners();
        setUpTweetsList(controller.getTweets());
        //recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        /*recyclerView.setAdapter(new TweetsAdapter(getActivity(), tweets));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));*/


        return view;
        //return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        switcher = (ImageView)view.findViewById(R.id.switcher);
    }

    private void setUpTweetsList(List<Tweet> tweets) {
        recyclerView.setAdapter(new TweetsAdapter(getActivity(), tweets));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void addListeners() {
        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /*private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.d("my", "exception");
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            Log.d("my", "result " + result);
            Log.d("my", "bmImage " + bmImage);
            bmImage.setImageBitmap(result);
        }
    }*/
}
