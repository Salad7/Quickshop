package com.example.msalad.quickshopping;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.msalad.quickshopping.Database.CartItem;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

/**
 * Created by msalad on 1/16/2018.
 */

public class FragmentFeature extends Fragment {

    ArrayList<Item> customGridItems;
    GridAdapter gridAdapter;
    GridView grid;
    Item item;
    FloatingSearchView floatingSearchView;
    MainActivity mainActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feature,container,false);
        grid = v.findViewById(R.id.grid_items);
        floatingSearchView = v.findViewById(R.id.floating_search_view);
        floatingSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        loadGridItems();


        return v;
    }

    public void loadGridItems(){
        item = new Item();
        customGridItems = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            item.setTitle("Expensive Jacket");
            item.setPrice(122);
            customGridItems.add(item);
        }
        gridAdapter = new GridAdapter(customGridItems,getContext());
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle b = new Bundle();



            }
        });
        grid.setAdapter(gridAdapter);


    }

    @Override
    public void onStart() {
        super.onStart();
    }



    public class GridAdapter extends BaseAdapter {
        ArrayList<Item> itemArrayAdapter;
        Context context;
        public GridAdapter(ArrayList<Item> items, Context ctx) {
            itemArrayAdapter = items;
            context = ctx;
        }

        @Override
        public int getCount() {
            return itemArrayAdapter.size();
        }

        @Override
        public Object getItem(int i) {
            return itemArrayAdapter.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_feature_item,viewGroup,false);
            final TextView name = convertView.findViewById(R.id.grid_title);
            final TextView price =  convertView.findViewById(R.id.item_price);
            //TextView cate = convertView.findViewById(R.id.grid_category);
            ImageView img =  convertView.findViewById(R.id.grid_img);
            Button addcart = convertView.findViewById(R.id.grid_add_cart);
            addcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    double itemPrice = Double.parseDouble(price.getText().toString().replace("$",""));
                    CartItem item = new CartItem(itemPrice,name.getText().toString(),"","","",1);
                    mainActivity.addItemToCart(item);
                }
            });
            price.setText(itemArrayAdapter.get(i).getPrice()+".00$");
            name.setText(itemArrayAdapter.get(i).getTitle());

            return convertView;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }
}
