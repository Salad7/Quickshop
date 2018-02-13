package com.example.msalad.quickshopping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by cci-loaner on 2/12/18.
 */

public class SelectStoreActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabLayout = (TabLayout) findViewById(R.id.stores_id);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(new FragmentStores(),"TAG").commit();


    }
}
