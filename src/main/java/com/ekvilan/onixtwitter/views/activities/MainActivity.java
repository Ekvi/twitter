package com.ekvilan.onixtwitter.views.activities;


import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ekvilan.onixtwitter.R;
import com.ekvilan.onixtwitter.views.fragments.HomeFragment;
import com.ekvilan.onixtwitter.views.fragments.MapFragment;
import com.ekvilan.onixtwitter.views.fragments.MenuFragment;
import com.ekvilan.onixtwitter.views.fragments.ProfileFragment;


public class MainActivity  extends AppCompatActivity {

    private FragmentTabHost mTabHost;
    private ImageView switcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switcher = (ImageView)findViewById(R.id.switcher);

        initToolBar();
        initTabs();
        addListeners();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView textView = (TextView) toolbar.findViewById(R.id.titleToolbar);
        textView.setText(getResources().getString(R.string.title_home));
    }

    private void initTabs() {
        View homeView = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_home, null);
        View profileView = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_profile, null);
        View mapView = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_map, null);
        View menuView = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_menu, null);

        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realTabContent);

        mTabHost.addTab(mTabHost.newTabSpec("home").setIndicator(homeView),HomeFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("profile").setIndicator(profileView),ProfileFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("map").setIndicator(mapView),MapFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("menu").setIndicator(menuView ),MenuFragment.class, null);
    }

    private void addListeners() {
        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showSingleTweet();
            }
        });
    }

  /*  private void showSingleTweet() {
        Fragment newFragment = new ExampleFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();
    }*/

   /* private View createView(Context context, int title, int icon) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
        ImageView iv = (ImageView) view.findViewById(R.id.imageView);
        iv.setImageResource(icon);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(title);
        return view;
    }*/

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
