package com.ekvilan.onixtwitter.views.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ekvilan.onixtwitter.R;
import com.ekvilan.onixtwitter.models.Tweet;


import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int EMPTY_VIEW = 10;

    //private DateUtils dateUtils = new DateUtils();

    private LayoutInflater inflater;
    //private Context context;
    private List<Tweet> tweets;

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        inflater = LayoutInflater.from(context);
        this.tweets = tweets;
        //this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*if(viewType == EMPTY_VIEW) {
            return new EmptyViewHolder(inflater.inflate(R.layout.empty_row, parent, false));
        }*/

        return new TweetViewHolder(
                inflater.inflate(R.layout.tweet, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof TweetViewHolder) {
            TweetViewHolder tweetsHolder = (TweetViewHolder) viewHolder;
            Tweet tweet = tweets.get(position);

            tweetsHolder.tvName.setText(tweet.getName());
            tweetsHolder.tvScreenName.setText("@" + tweet.getScreenName());
            tweetsHolder.tvDate.setText(tweet.getDate().toString().substring(0, 3));
            tweetsHolder.tvMessage.setText(tweet.getMessage());

            new DownloadImageTask(tweetsHolder.avatar).execute(tweet.getUserImage());
            if(!tweet.getImageUrl().isEmpty()) {
                new DownloadImageTask(tweetsHolder.image).execute(tweet.getImageUrl());
                tweetsHolder.image.setScaleType(ImageView.ScaleType.FIT_XY);
                tweetsHolder.image.setVisibility(View.VISIBLE);
            } else {
                tweetsHolder.image.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return tweets.size() > 0 ? tweets.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (tweets.size() == 0) {
            return EMPTY_VIEW;
        }
        return super.getItemViewType(position);
    }

    /*private String formatDate(String date) {
        String newDate = dateUtils.formatToYesterdayOrToday(date);

        if(newDate.startsWith(DateUtils.TODAY)) {
            newDate = newDate.replace(
                    DateUtils.TODAY, context.getResources().getString(R.string.todayDate));
        } else if (newDate.startsWith(DateUtils.YESTERDAY)) {
            newDate = newDate.replace(
                    DateUtils.YESTERDAY, context.getResources().getString(R.string.yesterdayDate));
        }
        return newDate;
    }*/

    public class TweetViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private ImageView image;
        private TextView tvName;
        private TextView tvScreenName;
        private TextView tvDate;
        private TextView tvMessage;

        public TweetViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.name);
            tvScreenName = (TextView) itemView.findViewById(R.id.screenName);
            tvDate = (TextView) itemView.findViewById(R.id.date);
            tvMessage = (TextView) itemView.findViewById(R.id.message);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }

   /* public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }*/

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
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
            bmImage.setImageBitmap(result);
        }
    }
}
