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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
        customItemAdapter = new CustomItemAdapter(getContext(),R.layout.custom_shopping_item,items);
        listView.setAdapter(customItemAdapter);
        return v;
    }

    public class CustomItemAdapter extends ArrayAdapter<Item>{
        List<Item> items;
        Activity ctx;
        int res;
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
            TextView name = view.findViewById(R.id.item_title);
            TextView price =  view.findViewById(R.id.item_price);
//            TextView remove = view.findViewById(R.id.item_remove);
            ImageView more =  view.findViewById(R.id.item_more_info);
            ImageView img =  view.findViewById(R.id.item_img);

            //this code sets the values of the objects to values from the arrays
           // price.setText(items.get(position).getPrice()+".00$");
//            name.setText(items.get(position).getTitle());


            return view;
        }
    }
}
