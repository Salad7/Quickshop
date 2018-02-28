package com.example.msalad.quickshopping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.msalad.quickshopping.Database.Store;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by cci-loaner on 2/24/18.
 */

public class FragmentResturant extends Fragment {
    ArrayList<Store> stores;
    GridView gridView;
    StoreAdapter gridAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_resturant,container,false);
        gridView = v.findViewById(R.id.grid_rest);
        stores = new ArrayList<>();
        loadStores();
        return v;
    }

    public void loadGrid(){
        gridAdapter = new StoreAdapter(stores,getActivity());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),"Hit "+stores.get(i).getName(),Toast.LENGTH_SHORT).show();
            }
        });
        gridView.setAdapter(gridAdapter);
    }

    public void loadStores(){
        //Store target = new Store("Target","http://adele.wikia.com/wiki/File:TARGET.png");
        Store bargain = new Store(R.drawable.bargainmart);//new Store("Walmart","https://lh3.googleusercontent.com/aUxQggeuIel4IZsWbdHaEuys8FNids7l3ZMtrQHX0DJyoTGHxo7xfTfi3CpRhd0mpsI=w300-rw");
        bargain.setName("Bargain Mart");
        stores.add(bargain);


        loadGrid();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String store = ((Store)gridAdapter.getItem(position)).getName();
                Intent i = new Intent(getActivity(), MainActivity.class);
                i.putExtra("restaurant",((Store)gridAdapter.getItem(position)).getName());
                Context context = getActivity();
                SharedPreferences sharedPref = context.getSharedPreferences(
                        getString(R.string.ref), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("store",store).apply();
                startActivity(i);
            }
        });
        Log.d("FragmentStores",gridAdapter.getCount()+"");
    }

    public class StoreAdapter extends BaseAdapter {
        ArrayList<Store> itemArrayAdapter;
        Context context;
        public StoreAdapter(ArrayList<Store> stores, Context ctx) {
            itemArrayAdapter = stores;
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
            convertView = layoutInflater.inflate(R.layout.custom_store_item,viewGroup,false);
            //TextView name = convertView.findViewById(R.id.grid_title);
            ImageView img =  convertView.findViewById(R.id.grid_img);
            img.setBackgroundResource(itemArrayAdapter.get(i).getDrawableID());
//            Picasso.with(context)
//                    .load(itemArrayAdapter.get(i).getUrl())
//                    .resize(50, 50)
//                    .centerCrop()
//                    .into(img);
            //price.setText(itemArrayAdapter.get(i).getPrice()+".00$");
            //name.setText(itemArrayAdapter.get(i).getTitle());

            return convertView;
        }
    }
}
