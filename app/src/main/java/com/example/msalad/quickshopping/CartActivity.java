package com.example.msalad.quickshopping;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msalad.quickshopping.Database.CartItem;
import com.example.msalad.quickshopping.Database.CartListOfItems;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msalad on 1/16/2018.
 */

public class CartActivity extends AppCompatActivity {

    ListView listView;
    CustomCartItemAdapter customCartItemAdapter;
    CartListOfItems cartListOfItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        listView = (ListView) findViewById(R.id.cart_items);
        if(getIntent().hasExtra("cart")){
            try {
                cartListOfItems = (CartListOfItems) getIntent().getSerializableExtra("cart");
                loadCart();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            cartListOfItems = new CartListOfItems();
        }

    }

    public void loadCart(){
        Log.d("CartActivity","Loading cart");
        customCartItemAdapter = new CustomCartItemAdapter(this,R.layout.custom_shopping_item,cartListOfItems.getCart());
        listView.setAdapter(customCartItemAdapter);
    }

    public class CustomCartItemAdapter extends ArrayAdapter<CartItem> {
        List<CartItem> items;
        Activity ctx;
        int res;
        public CustomCartItemAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<CartItem> objects) {
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
            TextView name = view.findViewById(R.id.item_title);
            TextView price =  view.findViewById(R.id.item_price);
//            TextView remove = view.findViewById(R.id.item_remove);
            ImageView delete =  view.findViewById(R.id.item_delete);
            ImageView img =  view.findViewById(R.id.item_img);
            TextView quantity = view.findViewById(R.id.item_quantity);
            //this code sets the values of the objects to values from the arrays
            price.setText(items.get(position).getPrice()+"");
            name.setText(items.get(position).getName());
            quantity.setText(items.get(position).getQuantity()+"");
            Picasso.with(ctx).load(items.get(position).getImage()).into(img);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(),"Item deleted!",Toast.LENGTH_SHORT).show();
                    items.remove(position);
                    notifyDataSetChanged();
                }
            });


            return view;
        }
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter mAdapter = listView.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listView);
            mView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += mView.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
