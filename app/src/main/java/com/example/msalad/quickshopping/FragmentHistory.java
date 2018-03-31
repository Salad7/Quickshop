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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msalad.quickshopping.Database.HistoryItem;
import com.example.msalad.quickshopping.Database.InventoryItem;

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
        item.addInventoryItem(inventoryItem);
        item.addInventoryItem(inventoryItem);
        item.addInventoryItem(inventoryItem);
        items.add(item);
        customItemAdapter = new CustomItemAdapter(getContext(),R.layout.custom_history_item,items);
        listView.setAdapter(customItemAdapter);
        return v;
    }

    public void inflateDialogList(int position){
        ArrayList<InventoryItem> clickedItem = items.get(position).getItems();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_list, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("History for cart on "+items.get(position).getDate());
        dialogList = (ListView) dialogView.findViewById(R.id.list_dialog);
        customDialogListAdapter = new CustomDialogListAdapter(getContext(),clickedItem,R.layout.custom_shopping_item);
        dialogList.setAdapter(customDialogListAdapter);
        Log.d("FragmentHistory","Size of InventoryItems: "+items.get(position).getItems().size());
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
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
            TextView price = view.findViewById(R.id.rv_price);
//            TextView date = view.findViewById(R.id.item)
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
            Button mViewCartBtn =  view.findViewById(R.id.history_cart);
            mViewCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inflateDialogList(position);
                }
            });
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

            ArrayList<InventoryItem> items;
            Context context;
            int layout;
            CustomDialogListAdapter(Context ctx, ArrayList<InventoryItem> items, int layout){
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
                TextView price =  view.findViewById(R.id.rv_price);
                ImageView img =  view.findViewById(R.id.item_img);
                price.setText(items.get(position).getPrice()+"");
                final TextView quantity = view.findViewById(R.id.item_quantity);
                int quantityVal = Integer.parseInt(quantity.getText().toString());
                name.setText(items.get(position).getName());
                ImageView up = view.findViewById(R.id.shopping_up);
                ImageView down = view.findViewById(R.id.shopping_down);
                quantity.setText(quantityVal+"");
                up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),"Hit up!",Toast.LENGTH_SHORT).show();
                    }
                });
                down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),"Hit down!",Toast.LENGTH_SHORT).show();
                    }
                });
                //Picasso.with(getContext()).load(items.get(position).getImage()).into(img);
                return view;
            }
        }

        }


