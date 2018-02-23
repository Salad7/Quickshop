package com.example.msalad.quickshopping;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.msalad.quickshopping.Database.CartItem;
import com.example.msalad.quickshopping.Database.CartListOfItems;
import com.example.msalad.quickshopping.Database.InventoryItem;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager;
    public ViewPager mViewPager;
    public TabLayout tabLayout;
    public PagerAdapter pagerAdapter;
    public ImageView cart_iv;
    Toolbar toolbar;
    Fragment fragment;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavView;
    ActionBarDrawerToggle toggle;
    private HashMap<String, InventoryItem> sampleData; //Our hashmap with sample data on items
    private HashMap<String, InventoryItem> storeB_Data; //Our hashmap with items in store B
    private CartListOfItems cartListOfItems;
    private TextView cart_count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cartListOfItems = new CartListOfItems();
        fragmentManager = getSupportFragmentManager();
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        pagerAdapter = new PagerAdapter(fragmentManager);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        toolbar = findViewById(R.id.toolbar);
        cart_iv = findViewById(R.id.cart);
        cart_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,CartActivity.class);
                i.putExtra("cart",cartListOfItems);
                startActivity(i);
            }
        });
         cart_count = findViewById(R.id.cart_count);
         cart_count.setText("0");
        setUpTablayout();
        setupNavigationDrawer();
        loadDB();


    }


    public void loadDB(){
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////// Code that builds stores with their items lists and builds carts /////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        sampleData = new HashMap<>(); //Make 1 hashmap per store (each has that store's items)
        storeB_Data = new HashMap<>(); //Hashmap that has items of store B

        //Add new item to HashMap of items
        sampleData.put("026229212703", new InventoryItem(1.99, "Notebook", "http://www.ryman.co.uk/media/catalog/product/0/3/0399030007.jpg", "Others","026229212703"));
        sampleData.put("026229345613", new InventoryItem(2.99, "Toy Car", "http://www.ryman.co.uk/media/catalog/product/0/3/0399030007.jpg", "Others","026229345613"));


        //This data will be populated into instance objects on each scan and "add to cart" /////////////////////////////
        //LAST INPUT (in this case, 1) IS THE QUANTITY. SHOULD BE SET TO WHATEVER IS ASSIGNED FROM THE DIALOGUE BOX
        CartItem notebook = new CartItem(sampleData.get("026229212703").getPrice(), sampleData.get("026229212703").getName(), sampleData.get("026229212703").getImage(), sampleData.get("026229212703").getSalesTaxGroup(), sampleData.get("026229212703").getItemKey(), 1);
        CartItem toyCar = new CartItem(sampleData.get("026229345613").getPrice(), sampleData.get("026229345613").getName(), sampleData.get("026229345613").getImage(), sampleData.get("026229345613").getSalesTaxGroup(), sampleData.get("026229345613").getItemKey(), 1);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        CartListOfItems cart = new CartListOfItems();
        cart.addToCart(notebook);
        cart.addToCart(toyCar);
    }
    public void setupNavigationDrawer(){
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mNavView = (NavigationView) findViewById(R.id.navigation);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavView.setNavigationItemSelectedListener(MainActivity.this);
        toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
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
                tabLayout.getTabAt(2).setIcon(R.drawable.list);
                tabLayout.getTabAt(2).setText("History");

            }
            if(i == 2){
                tabLayout.getTabAt(1).setIcon(R.drawable.feature);
                tabLayout.getTabAt(1).setText("Featured");

            }
        }
        mViewPager.setCurrentItem(0);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.change_stores) {
            Toast.makeText(this,"Changing stores",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this,SelectStoreActivity.class);
            startActivity(i);
        }
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
            if(i == 2){
                fragment = new FragmentHistory();
                Bundle args = new Bundle();
                fragment.setArguments(args);
            }
            if(i == 1){

                fragment = new FragmentFeature();
                Bundle args = new Bundle();
                fragment.setArguments(args);
            }
            return fragment;
        }


        @Override
        public int getCount() {
            return 3;
        }
    }

    public void addItemToCart(CartItem cartItem){
        cartListOfItems.addToCart(cartItem);
        int cartCount = Integer.parseInt(cart_count.getText().toString());
        cartCount+=1;
        cart_count.setText(cartCount+"");
    }

    public CartListOfItems getCart(){
        return cartListOfItems;
    }


}
