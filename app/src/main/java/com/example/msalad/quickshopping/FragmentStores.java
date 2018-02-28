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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msalad.quickshopping.Database.Store;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by cci-loaner on 2/12/18.
 */

public class FragmentStores extends Fragment {
    ArrayList<Store> stores;
    GridView gridView;
    StoreAdapter gridAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stores,container,false);
        gridView = v.findViewById(R.id.grid_stores);
        stores = new ArrayList<>();
        loadStores();
        return v;
    }

    public void loadGrid(){
        gridAdapter = new StoreAdapter(stores,getContext());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),"Hit "+stores.get(i).getName(),Toast.LENGTH_SHORT).show();
            }
        });
        gridView.setAdapter(gridAdapter);
    }

    public void loadStores(){
        Store greatbuy = new Store(R.drawable.greatbuy);//new Store("Walmart","https://lh3.googleusercontent.com/aUxQggeuIel4IZsWbdHaEuys8FNids7l3ZMtrQHX0DJyoTGHxo7xfTfi3CpRhd0mpsI=w300-rw");
        greatbuy.setName("Great Buy");
        Store abode = new Store(R.drawable.abodedepot);//new Store("Walmart","https://lh3.googleusercontent.com/aUxQggeuIel4IZsWbdHaEuys8FNids7l3ZMtrQHX0DJyoTGHxo7xfTfi3CpRhd0mpsI=w300-rw");
        abode.setName("Abode Depot");
        stores.add(greatbuy);
        stores.add(abode);
        loadGrid();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String store = ((Store)gridAdapter.getItem(position)).getName();
                Intent i = new Intent(getActivity(), MainActivity.class);
                i.putExtra("store",((Store)gridAdapter.getItem(position)).getName());
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
           // img.setBackgroundResource(R.drawable.greatbuy);

            return convertView;
        }
    }
}
