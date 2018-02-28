package com.example.msalad.quickshopping;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.msalad.quickshopping.Database.CartItem;
import com.example.msalad.quickshopping.Database.HistoryItem;
import com.example.msalad.quickshopping.Database.InventoryItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msalad on 1/16/2018.
 */

public class FragmentHistory extends Fragment {

    ListView listView;
    ListView dialogList;
    CustomItemAdapter customItemAdapter;
    CustomDialogListAdapter customDialogListAdapter;
    ArrayList<HistoryItem> items;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history,container,false);
        listView = v.findViewById(R.id.history_list);
        dialogList = v.findViewById(R.id.list_dialog);
        items = new ArrayList<>();
        final HistoryItem item = new HistoryItem();
        final InventoryItem inventoryItem = new InventoryItem(1.00,"Some Item","","","key");
        item.getItems().add(inventoryItem);
        customItemAdapter = new CustomItemAdapter(getContext(),R.layout.custom_history_item,items);
        listView.setAdapter(customItemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_search_view, null);
                dialogBuilder.setView(dialogView);
                dialogBuilder.setTitle("Search for an item");
                Log.d("MainActivity","Hit setupSearchView");
                dialogList = (ListView) dialogView.findViewById(R.id.search_items);
                customDialogListAdapter = new CustomDialogListAdapter(getContext(),items.get(position),R.layout.dialog_list);

                final FloatingSearchView floatingSearchView = (FloatingSearchView) dialogView.findViewById(R.id.floating_search_view_2);
                floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
                    @Override
                    public void onSearchTextChanged(String oldQuery, String newQuery) {
                        customSearchAdapter = new MainActivity.CustomSearchAdapter(MainActivity.this,listOfAllItems,R.layout.custom_search_item);
                        searchListView.setAdapter(customSearchAdapter);
                    }
                });
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //pass
                    }
                });
                AlertDialog b = dialogBuilder.create();
                b.show();
            }
        });
        return v;
    }


    public class CustomItemAdapter extends ArrayAdapter<HistoryItem>{
        List<HistoryItem> items;
        Activity ctx;
        int res;
        LayoutInflater layoutInflater;
        LinearLayout linearLayout;
        public CustomItemAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<HistoryItem> objects) {
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
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater=ctx.getLayoutInflater();
            View view = inflater.inflate(res, parent, false);
            //this code gets references to objects in the listview_row.xml file
            TextView price = view.findViewById(R.id.history_price);
            TextView date = view.findViewById(R.id.history_date);
            final HorizontalScrollView horizontalScrollView = view.findViewById(R.id.history_items);

            ImageView delete = view.findViewById(R.id.history_delete);
            //Delete cart from history
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   items.remove(position);
                   customItemAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(),"Removed Cart Item!",Toast.LENGTH_SHORT).show();
                }
            });

            //Button share =  view.findViewById(R.id.history_share);
            //Share Cart with friends

            Button viewCart =  view.findViewById(R.id.history_cart);
            //View Cart in dialog
            LinearLayout linearLayout = new LinearLayout(ctx);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            for(int i = 0; i < 6; i++){
                ImageView image = new ImageView(ctx);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(324, 324);
                layoutParams.setMarginStart(48);
                layoutParams.setMarginEnd(48);
                image.setPadding(24,24,24,24);
                image.setLayoutParams(layoutParams);
                image.setBackgroundResource(R.drawable.bargainmart);
                linearLayout.addView(image);
            }
            horizontalScrollView.addView(linearLayout);
            return view;
        }

        }

        public class CustomDialogListAdapter extends BaseAdapter {

            ArrayList<Item> items;
            Context context;
            int layout;
            CustomDialogListAdapter(Context ctx, ArrayList<Item> items, int layout){
                context = ctx;
                this.items = items;
                this.layout = layout;
            }

            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public Object getItem(int position) {
                return items.get(position);
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
                TextView price =  view.findViewById(R.id.item_price);
                ImageView img =  view.findViewById(R.id.item_img);
                price.setText(items.get(position).getPrice()+"");
                name.setText(items.get(position).getTitle());
//            quantity.setText(cartItems.get(position).getQuantity()+"");
                //Picasso.with(getContext()).load(items.get(position).getImage()).into(img);
                return view;
            }
        }

        }


