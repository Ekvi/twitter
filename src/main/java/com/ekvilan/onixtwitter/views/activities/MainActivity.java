package com.ekvilan.onixtwitter.views.activities;


import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.ekvilan.onixtwitter.R;
import com.ekvilan.onixtwitter.views.fragments.BaseContainerFragment;
import com.ekvilan.onixtwitter.views.fragments.HomeContainerFragment;
import com.ekvilan.onixtwitter.views.fragments.MapFragment;
import com.ekvilan.onixtwitter.views.fragments.MenuFragment;
import com.ekvilan.onixtwitter.views.fragments.ProfileFragment;


public class MainActivity  extends AppCompatActivity {
    private static final String TAB_HOME = "home";
    private static final String TAB_PROFILE= "profile";
    private static final String TAB_MAP = "map";
    private static final String TAB_MENU = "menu";

    private FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabs();
    }

    private void initTabs() {
        View homeView = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_home, null);
        View profileView = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_profile, null);
        View mapView = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_map, null);
        View menuView = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_menu, null);

        tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realTabContent);

        tabHost.addTab(tabHost.newTabSpec(TAB_HOME).setIndicator(homeView),HomeContainerFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec(TAB_PROFILE).setIndicator(profileView),ProfileFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec(TAB_MAP).setIndicator(mapView),MapFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec(TAB_MENU).setIndicator(menuView ),MenuFragment.class, null);
    }

    @Override
    public void onBackPressed() {
        boolean isPopFragment = false;
        String currentTabTag = tabHost.getCurrentTabTag();
        switch (currentTabTag) {
            case TAB_HOME:
                isPopFragment = ((BaseContainerFragment)
                        getSupportFragmentManager().findFragmentByTag(TAB_HOME)).popFragment();
                break;
            case TAB_PROFILE:
                isPopFragment = ((BaseContainerFragment)
                        getSupportFragmentManager().findFragmentByTag(TAB_PROFILE)).popFragment();
                break;
            case TAB_MAP:
                isPopFragment = ((BaseContainerFragment)
                        getSupportFragmentManager().findFragmentByTag(TAB_MAP)).popFragment();
                break;
            case TAB_MENU:
                isPopFragment = ((BaseContainerFragment)
                        getSupportFragmentManager().findFragmentByTag(TAB_MENU)).popFragment();
                break;
        }
        if (!isPopFragment) {
            finish();
        }
    }
}
