package com.example.msalad.quickshopping;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;


/**
 * Created by cci-loaner on 2/12/18.
 */

public class SelectStoreActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    TabLayout tabLayout;
    public StorePagerAdapter pagerAdapter;
    public ViewPager mViewPager;
    Fragment fragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_store);
        tabLayout = (TabLayout) findViewById(R.id.stores_id);
        fragmentManager = getSupportFragmentManager();
        setUpTablayout();
    }

    public void setUpTablayout(){
        pagerAdapter = new StorePagerAdapter(fragmentManager);
        mViewPager = (ViewPager) findViewById(R.id.choosestore_viewpager);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                final InputMethodManager imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mViewPager.getWindowToken(), 0);
            }

            @Override
            public void onPageScrolled(int position, float offset, int offsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++){
            if(i == 0){
                //tabLayout.getTabAt(0).setIcon(R.drawable.code);
                tabLayout.getTabAt(0).setText("Stores");
            }
            if(i == 1){
                //tabLayout.getTabAt(1).setIcon(R.drawable.list);
                tabLayout.getTabAt(1).setText("Restaurants");

            }
        }
        mViewPager.setCurrentItem(0);
    }

    public class StorePagerAdapter extends FragmentStatePagerAdapter {
        StorePagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int i) {
            if(i == 0){
                fragment = new FragmentStores();
                Bundle args = new Bundle();
                fragment.setArguments(args);

            }
            if(i == 1){
                fragment = new FragmentResturant();
                Bundle args = new Bundle();
                fragment.setArguments(args);
            }
            return fragment;
        }


        @Override
        public int getCount() {
            return 2;
        }
    }
}
