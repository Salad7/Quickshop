package com.example.msalad.quickshopping;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.msalad.quickshopping.Database.HistoryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msalad on 1/16/2018.
 */

public class FragmentHistory extends Fragment {

    ListView listView;
    CustomItemAdapter customItemAdapter;
    ArrayList<Item> items;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history,container,false);
        listView = v.findViewById(R.id.history_list);
        items = new ArrayList<>();
        Item item = new Item();
        item.setPrice(344);
        item.setTitle("Expensive Jacket");

        items.add(item);
        items.add(item);
        items.add(item);
        customItemAdapter = new CustomItemAdapter(getContext(),R.layout.custom_history_item,items);
        listView.setAdapter(customItemAdapter);
        return v;
    }

    public class CustomItemAdapter extends ArrayAdapter<Item>{
        List<Item> items;
        Activity ctx;
        int res;
        LayoutInflater layoutInflater;
        LinearLayout linearLayout;
        public CustomItemAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Item> objects) {
            super(context, resource, objects);
            items = objects;
            ctx = (Activity) context;
            res = resource;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater=ctx.getLayoutInflater();
            View view = inflater.inflate(res, parent, false);
            //this code gets references to objects in the listview_row.xml file
            TextView price = view.findViewById(R.id.history_price);
            TextView date = view.findViewById(R.id.history_date);
            HorizontalScrollView items = view.findViewById(R.id.history_items);
            ImageView delete = view.findViewById(R.id.history_delete);
            Button share =  view.findViewById(R.id.history_share);
            Button viewCart =  view.findViewById(R.id.history_cart);
            //linearLayout=(LinearLayout) view.findViewById(R.id.history_container);
            LinearLayout linearLayout = new LinearLayout(ctx);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);


            for(int i = 0; i < 6; i++){
                ImageView image = new ImageView(ctx);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(324, 324);
                layoutParams.setMarginStart(48);
                layoutParams.setMarginEnd(48);
                image.setLayoutParams(layoutParams);
                image.setBackgroundResource(R.drawable.bargainmart);
                linearLayout.addView(image);
            }
            items.addView(linearLayout);
            //setupHorizontalScrollView(parent);
            return view;
        }

        public void setupHorizontalScrollView(ViewGroup parent){

            //for (int i=0;i<8; i++) {
                View view = layoutInflater.inflate(R.layout.custom_store_item, parent, false);
                ImageView imageView = (ImageView) view.findViewById(R.id.grid_img);

//                if (i==(name.length)-1)
//                {
//                    scroll_item_layout.setBackgroundResource(android.R.color.transparent);
//                }
                linearLayout.addView(view);


           // }
        }
        }

//    public class HistoryItemAdapter extends ArrayAdapter<HistoryItem>{
//
//
//
//
//
//    }
}
