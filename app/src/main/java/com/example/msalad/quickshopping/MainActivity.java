package com.example.msalad.quickshopping;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

public class MainActivity extends FragmentActivity {

    FragmentManager fragmentManager;
    public ViewPager mViewPager;
    public TabLayout tabLayout;
    public PagerAdapter pagerAdapter;
    public ImageView cart_iv;
    Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        pagerAdapter = new PagerAdapter(fragmentManager);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        cart_iv = findViewById(R.id.cart);
        cart_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,CartActivity.class);
                startActivity(i);
            }
        });
        setUpTablayout();


    }


    public void setUpTablayout(){
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
                tabLayout.getTabAt(0).setIcon(R.drawable.code);
                tabLayout.getTabAt(0).setText("Scan");
            }
            if(i == 1){
                tabLayout.getTabAt(1).setIcon(R.drawable.list);
                tabLayout.getTabAt(1).setText("History");

            }
            if(i == 2){
                tabLayout.getTabAt(2).setIcon(R.drawable.feature);
                tabLayout.getTabAt(2).setText("Featured");

            }
//            if(i == 3){
//                tabLayout.getTabAt(3).setIcon(R.drawable.account);
//                tabLayout.getTabAt(3).setText("Account");
//            }
        }
        mViewPager.setCurrentItem(0);
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {


        PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if(i == 0){
                fragment = new FragmentScan();
                Bundle args = new Bundle();
                fragment.setArguments(args);

            }
            if(i == 1){
                fragment = new FragmentHistory();
                Bundle args = new Bundle();
                fragment.setArguments(args);
            }
            if(i == 2){

                fragment = new FragmentFeature();
                Bundle args = new Bundle();
                fragment.setArguments(args);
            }
//            if(i == 3){
//                fragment = new FragmentAccount();
//                Bundle args = new Bundle();
//                fragment.setArguments(args);
//            }
            return fragment;
        }


        @Override
        public int getCount() {
            return 3;
        }


    }
}
