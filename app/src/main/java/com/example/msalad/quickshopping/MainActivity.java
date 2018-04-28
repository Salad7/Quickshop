package com.example.msalad.quickshopping;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msalad.quickshopping.Database.CartItem;
import com.example.msalad.quickshopping.Database.CartListOfItems;
import com.example.msalad.quickshopping.Database.InventoryItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

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
    //private HashMap<String, InventoryItem> sampleData; //Our hashmap with sample data on items
    //private HashMap<String, InventoryItem> storeB_Data; //Our hashmap with items in store B
    private HashMap<String, InventoryItem> sampleData; //Our hashmap with sample data on items
    private CartListOfItems cartListOfItems;
    private ListView searchListView;
    private CustomSearchAdapter customSearchAdapter;
    private ArrayList<CartItem> listOfAllItems;
    private TextView cart_count;
    private TextView mTitle;
    private static final int REQUEST_CAMERA = 1;
    private Button viewItem;
    //private FloatingSearchView floatingSearchView;


    public void preCheck(){
        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.ref), Context.MODE_PRIVATE);
        if(sharedPref.contains("store")){
            Toast.makeText(this,"Store found!",Toast.LENGTH_SHORT).show();
            mTitle = findViewById(R.id.main_title);
            mTitle.setText(sharedPref.getString("store",""));
            init();
        }
        else{
            Toast.makeText(this,"Store not found! Redirecting",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this,SelectStoreActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
    }
    public void init(){
        cartListOfItems = new CartListOfItems();
        fragmentManager = getSupportFragmentManager();
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        pagerAdapter = new PagerAdapter(fragmentManager);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        toolbar = findViewById(R.id.toolbar);
        cart_iv = findViewById(R.id.cart);
        listOfAllItems = new ArrayList<>();
//        viewItem = findViewById(R.id.button10);
//        viewItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,ActivityViewItem.class);
//                startActivity(intent);
//            }
//        });
        //floatingSearchView = findViewById(R.id.floating_search_view);
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
        loadStore();
        //setupSearchView();
        try {
            if (getIntent().hasExtra("cart")) {
                cartListOfItems = (CartListOfItems) getIntent().getSerializableExtra("cart");
                cart_count.setText(cartListOfItems.getCart().size()+"");
                Log.d("onResume","Found cart items "+cartListOfItems.getCart().size());
            }
            else{
                Log.d("onResume","Data not passed back");

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preCheck();




        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        //requestPermission();
        if (currentapiVersion >= Build.VERSION_CODES.JELLY_BEAN) {
            if (checkPermission()) {
                Toast.makeText(getApplicationContext(), "Permission already granted", Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

//    private void setupSearchView(){
//        floatingSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
//            @Override
//            public void onFocus() {
//                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
//                LayoutInflater inflater = getLayoutInflater();
//                final View dialogView = inflater.inflate(R.layout.custom_search_view, null);
//                dialogBuilder.setView(dialogView);
//                dialogBuilder.setTitle("Tap to view an item");
//                Log.d("MainActivity","Hit setupSearchView");
//                searchListView = (ListView) dialogView.findViewById(R.id.search_items);
//                final FloatingSearchView floatingSearchView = (FloatingSearchView) dialogView.findViewById(R.id.floating_search_view_2);
//                customSearchAdapter = new CustomSearchAdapter(MainActivity.this,listOfAllItems,R.layout.custom_search_item);
//                searchListView.setAdapter(customSearchAdapter);
//                floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
//                    @Override
//                    public void onSearchTextChanged(String oldQuery, String newQuery) {
//                    Log.d("MainActivity","New query "+newQuery);
//                    customSearchAdapter.runQuery(newQuery);
//                    customSearchAdapter.notifyDataSetChanged();
//                    }
//                });
//                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        //pass
//                    }
//                });
//                AlertDialog b = dialogBuilder.create();
//                b.show();
//            }
//
//            @Override
//            public void onFocusCleared() {
//
//            }
//        });
//    }


    private boolean checkPermission() {
        return ( ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA ) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public void loadStore(){
        View header = mNavView.getHeaderView(0);
        TextView tv =  header.findViewById(R.id.nav_store);
        Log.d("MainActivity","Hit load store");
        tv.setText(mTitle.getText().toString());
        if(getIntent().hasExtra("store")){
           tv.setText(getIntent().getStringExtra("store"));
           mTitle.setText(getIntent().getStringExtra("store"));
        }
        else if(getIntent().hasExtra("restaurant")){
            tv.setText(getIntent().getStringExtra("restaurant"));
            mTitle.setText(getIntent().getStringExtra("restaurant"));

        }
    }



    public void loadDB(){
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////// Code that builds stores with their items lists and builds carts /////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        sampleData = new HashMap<>(); //Make 1 hashmap per store (each has that store's items)
        //storeB_Data = new HashMap<>(); //Hashmap that has items of store B

        //Add new item to HashMap of items
        sampleData.put("026229211706", new InventoryItem(1.99, "Notebook", "http://www.ryman.co.uk/media/catalog/product/0/3/0399030007.jpg", "Others","026229211706"));
        sampleData.put("096619756803", new InventoryItem(2.99, "Water Bottle", "http://www.ryman.co.uk/media/catalog/product/0/3/0399030007.jpg", "Others","096619756803"));
        sampleData.put("9781491962299", new InventoryItem(4.99, "Machine Learning", "http://www.ryman.co.uk/media/catalog/product/0/3/0399030007.jpg", "Others","9781491962299"));
        sampleData.put("1297432524354", new InventoryItem(4.99, "Item", "http://www.ryman.co.uk/media/catalog/product/0/3/0399030007.jpg", "Others","1297432524354"));
        sampleData.put("857263004111", new InventoryItem(79.99, "Trendy Jacket", "http://www.ryman.co.uk/media/catalog/product/0/3/0399030007.jpg", "Others","857263004111"));

        listOfAllItems.add(new CartItem(3.99,"Toy","http://www.pngmart.com/files/4/Plush-Toy-PNG-Transparent-Image.png","Others","1",1));
        listOfAllItems.add(new CartItem(3.99,"Toy","http://www.pngmart.com/files/4/Plush-Toy-PNG-Transparent-Image.png","Others","1",1));
        listOfAllItems.add(new CartItem(3.99,"Toy","http://www.pngmart.com/files/4/Plush-Toy-PNG-Transparent-Image.png","Others","1",1));
        listOfAllItems.add(new CartItem(3.99,"Toy","http://www.pngmart.com/files/4/Plush-Toy-PNG-Transparent-Image.png","Others","1",1));
        listOfAllItems.add(new CartItem(3.99,"Toy","http://www.pngmart.com/files/4/Plush-Toy-PNG-Transparent-Image.png","Others","1",1));
        listOfAllItems.add(new CartItem(49.99,"Hands On Machine Learning","https://m.media-amazon.com/images/S/aplus-media/vc/11714e04-b1a6-439d-9482-87e757822f94.jpg","Others","1",1));

        //This data will be populated into instance objects on each scan and "add to cart" /////////////////////////////
        //LAST INPUT (in this case, 1) IS THE QUANTITY. SHOULD BE SET TO WHATEVER IS ASSIGNED FROM THE DIALOGUE BOX
        //CartItem notebook = new CartItem(sampleData.get("026229212703").getPrice(), sampleData.get("026229212703").getName(), sampleData.get("026229212703").getImage(), sampleData.get("026229212703").getSalesTaxGroup(), sampleData.get("026229212703").getItemKey(), 1);
        //CartItem water = new CartItem(sampleData.get("096619756803").getPrice(), sampleData.get("096619756803").getName(), sampleData.get("096619756803").getImage(), sampleData.get("096619756803").getSalesTaxGroup(), sampleData.get("096619756803").getItemKey(), 1);
        //CartItem mints = new CartItem(sampleData.get("030242940017").getPrice(), sampleData.get("030242940017").getName(), sampleData.get("030242940017").getImage(), sampleData.get("030242940017").getSalesTaxGroup(), sampleData.get("030242940017").getItemKey(), 1);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //CartListOfItems cart = new CartListOfItems();
        //cart.addToCart(notebook);
        //cart.addToCart(toyCar);
    }

    public void findItem(String key, final ZXingScannerView scannerView, final ZXingScannerView.ResultHandler resultHandler){
        Log.d("MainActivity","Key is: "+key);
        if(sampleData.containsKey(key)) {
            final InventoryItem item = sampleData.get(key);
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.dialog_item_single, null);
            dialogBuilder.setView(dialogView);
            final TextView name = (TextView) dialogView.findViewById(R.id.dialog_name);
            final TextView price = (TextView) dialogView.findViewById(R.id.dialog_price);
            final ImageView addBtn = dialogView.findViewById(R.id.dialog_add);
            final EditText quantity = dialogView.findViewById(R.id.dialog_quantity);
            final ImageView cancelBtn = dialogView.findViewById(R.id.dialog_cancel);
            name.setText(item.getName());
            price.setText("$"+item.getPrice()+"");
            dialogBuilder.setTitle("Custom dialog");
            dialogBuilder.setMessage("Enter text below");
            final AlertDialog b = dialogBuilder.create();
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    b.dismiss();
                    scannerView.resumeCameraPreview(resultHandler);
                }
            });
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartItem cartItem =
                            new CartItem(item.getPrice(),
                                    item.getName(),
                                    item.getImage(),
                                    item.getSalesTaxGroup(),
                                    item.getItemKey(),
                                    Integer.parseInt(quantity.getText().toString()));
                    addItemToCart(cartItem);
                    scannerView.resumeCameraPreview(resultHandler);
                    b.dismiss();
                }
            });
            b.show();
        }
        else {
            try {
                String uniqueItems[] = key.split("-"); //Holds the list of unique items from the QR code (with quantities)
                String[][] itemsAndQuantities = new String[uniqueItems.length][2]; //Array of arrays. Each inner array will hold the UPC code in the 0 index and the quantity in the 1 index

                for (int i = 0; i < uniqueItems.length; i++) {
                    String itemUPC = uniqueItems[i].split("\\*")[0]; //First part of a string like "1235436256546*6"
                    String itemQuantity = uniqueItems[i].split("\\*")[1]; //Second part of a string like "1235436256546*6"
                    itemsAndQuantities[i][0] = itemUPC;
                    itemsAndQuantities[i][1] = itemQuantity;
                }

                for (int i = 0; i < itemsAndQuantities.length; i++) {
                    if (!sampleData.containsKey(itemsAndQuantities[i][0])) {
                        //Code for dialogue box saying "Item(s) not found"
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Scan Result");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                scannerView.resumeCameraPreview(resultHandler);
                            }
                        });
                        builder.setMessage("Could not find item");
                        AlertDialog alert1 = builder.create();
                        alert1.show();
                        break;
                    }
                }
                for(int i = 0; i < itemsAndQuantities.length; i++){
                    //ADDS ITEMS FROM QR CODE TO CART IN LOOP
                    final InventoryItem item = sampleData.get(itemsAndQuantities[i][0]);

                    CartItem cartItem =
                            new CartItem(item.getPrice(),
                                    item.getName(),
                                    item.getImage(),
                                    item.getSalesTaxGroup(),
                                    item.getItemKey(),
                                    Integer.parseInt(itemsAndQuantities[i][1]));
                    addItemToCart(cartItem);
                    if(i == itemsAndQuantities.length-1){
                        Intent intent = new Intent(this, CartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("cart",cartListOfItems);
                        startActivity(intent);
                        finish();
                    }
                }


            } catch(Exception e) {
                //Code for dialogue box saying "Item(s) not found"
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Scan Result");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scannerView.resumeCameraPreview(resultHandler);
                    }
                });
                builder.setMessage("Could not find item");
                AlertDialog alert1 = builder.create();
                alert1.show();
            }


        }

    }
    public void setupNavigationDrawer(){
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mNavView = (NavigationView) findViewById(R.id.navigation);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavView.setNavigationItemSelectedListener(MainActivity.this);
        toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(Color.parseColor("#ffffff"));
        mDrawerLayout.addDrawerListener(toggle);
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
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
        else if(id == R.id.feedback){

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
        cart_count.setText(cartListOfItems.getCart().size()+"");
    }

    public CartListOfItems getCart(){
        return cartListOfItems;
    }

    public class CustomSearchAdapter extends BaseAdapter {

        ArrayList<CartItem> cartItems;
        ArrayList<CartItem> originalItems;
        Context context;
        int layout;
        CustomSearchAdapter(Context ctx, ArrayList<CartItem> cartItems, int layout){
            context = ctx;
            this.cartItems = cartItems;
            setOriginalItems();
            this.layout = layout;
        }

        public void setOriginalItems(){
            this.originalItems = this.cartItems;
        }

        public void runQuery(String que){
            cartItems = new ArrayList<>();
            for(int i = 0; i < originalItems.size(); i++){
                Log.d("MainActivity","Does "+que+" exist in "+originalItems.get(i).getName());
                if(originalItems.get(i).getName().toLowerCase().contains(que.toLowerCase()) || que.equals("")){
                    cartItems.add(originalItems.get(i));
                }
            }
        }

        @Override
        public int getCount() {
            try {
                return cartItems.size();
            }
            catch (NullPointerException excep){
                excep.printStackTrace();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return cartItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View view = inflater.inflate(layout, parent, false);
            TextView name = view.findViewById(R.id.item_title);
            TextView price =  view.findViewById(R.id.rv_price);
//            ImageView delete =  view.findViewById(R.id.item_delete);
            ImageView img =  view.findViewById(R.id.item_img);
//            TextView quantity = view.findViewById(R.id.item_quantity);
            price.setText(cartItems.get(position).getPrice()+"");
            name.setText(cartItems.get(position).getName());
//            quantity.setText(cartItems.get(position).getQuantity()+"");
            Picasso.with(MainActivity.this).load(cartItems.get(position).getImage()).into(img);
//            delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(MainActivity.this,"Item deleted!",Toast.LENGTH_SHORT).show();
//                    cartItems.remove(position);
//                    notifyDataSetChanged();
//                }
//            });
        return view;
        }
    }


}
