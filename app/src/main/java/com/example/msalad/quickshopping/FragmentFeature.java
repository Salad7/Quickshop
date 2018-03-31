package com.example.msalad.quickshopping;

import android.content.Context;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.msalad.quickshopping.Database.CartItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by msalad on 1/16/2018.
 */

public class FragmentFeature extends Fragment {

    ArrayList<Item> customGridItems;
    GridAdapter gridAdapter;
    GridView grid;
    FeatureItemAdapter featureItemAdapter;
    FeatureTrendyItem featureTrendyItem;
    private RecyclerView mRecyclerView;
    private RecyclerView mTrendyMens;
    private RecyclerView.LayoutManager mLayoutManager;
    Item item;
    FloatingSearchView floatingSearchView;
    MainActivity mainActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feature,container,false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.featured_items_rv);
        mTrendyMens = (RecyclerView) v.findViewById(R.id.trending_mens_rv);
        item = new Item();
        customGridItems = new ArrayList<>();
        for(int i = 0; i < 1; i++){
            item.setTitle("Expensive Jacket");
            item.setPrice(89.95);
            customGridItems.add(item);
        }
        loadRecyclerItems();
        loadTrendyMens();
        //grid = v.findViewById(R.id.grid_items);
//        floatingSearchView = v.findViewById(R.id.floating_search_view);
//        floatingSearchView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });

        //loadGridItems();


        return v;
    }

//    public void loadGridItems(){
//        item = new Item();
//        customGridItems = new ArrayList<>();
//        for(int i = 0; i < 2; i++){
//            item.setTitle("Expensive Jacket");
//            item.setPrice(122);
//            customGridItems.add(item);
//        }
//        gridAdapter = new GridAdapter(customGridItems,getContext());
//        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Bundle b = new Bundle();
//
//
//
//            }
//        });
//        grid.setAdapter(gridAdapter);
//
//    }

    public void loadRecyclerItems(){
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        featureItemAdapter = new FeatureItemAdapter(customGridItems, R.layout.custom_feature_item_2);
        mRecyclerView.setAdapter(featureItemAdapter);
    }

    public void loadTrendyMens(){
        ArrayList<Item> customTrendy = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            item.setTitle("Expensive Jacket");
            item.setPrice(89.95);
            customTrendy.add(item);
        }
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mTrendyMens.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mTrendyMens.addItemDecoration(new EqualSpaceItemDecoration(32));
        mTrendyMens.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        featureTrendyItem = new FeatureTrendyItem(customTrendy);
        mTrendyMens.setAdapter(featureTrendyItem);
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
        public View getView(final int pos, View convertView, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_feature_item,viewGroup,false);
            final TextView name = convertView.findViewById(R.id.grid_title);
            final TextView price =  convertView.findViewById(R.id.rv_price);
            //TextView cate = convertView.findViewById(R.id.grid_category);
            ImageView img =  convertView.findViewById(R.id.rv_img);
            Button addcart = convertView.findViewById(R.id.rv_add);
            addcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Item item = itemArrayAdapter.get(pos);
                    double  itemPrice =(double) itemArrayAdapter.get(pos).getPrice();
                    CartItem cartItem = new CartItem(itemPrice,item.getTitle(),"https://bloximages.chicago2.vip.townnews.com/qctimes.com/content/tncms/assets/v3/editorial/3/92/3920339a-9dba-11de-983a-001cc4c002e0/3920339a-9dba-11de-983a-001cc4c002e0.image.jpg","","key",1);
                    mainActivity.addItemToCart(cartItem);
                }
            });
            price.setText(itemArrayAdapter.get(pos).getPrice()+".00$");
            name.setText(itemArrayAdapter.get(pos).getTitle());

            return convertView;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }


    public class FeatureItemAdapter extends RecyclerView.Adapter<FeatureItemAdapter.ViewHolder> {
        private ArrayList<Item> mDataset;
        private int viewIn;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public  class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView mTitle;
            public TextView mPrice;
            public TextView mDesc;
            public AppCompatRatingBar mRating;
            public ImageView mImage;
            public Button mSave;
            public Button mAddCart;


            public ViewHolder(View v) {
                super(v);
                mTitle = v.findViewById(R.id.rv_title);
                mPrice = v.findViewById(R.id.rv_price);
                mDesc = v.findViewById(R.id.rv_desc);
                mRating = v.findViewById(R.id.rv_rating);
                mImage = v.findViewById(R.id.rv_img);
                mSave = v.findViewById(R.id.rv_save);
                mAddCart= v.findViewById(R.id.rv_add);

            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public FeatureItemAdapter(ArrayList<Item> myDataset, int viewIn) {
            mDataset = myDataset;
            this.viewIn = viewIn;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public FeatureItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(viewIn, parent, false);

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.mTitle.setText(mDataset.get(position).getTitle());
            holder.mDesc.setText(mDataset.get(position).getCategory());
            holder.mPrice.setText("$"+mDataset.get(position).getPrice());
            //Picasso.with(getContext()).load(mDataset.get(position).img_src).into(holder.mImage);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }

    public class FeatureTrendyItem extends RecyclerView.Adapter<FeatureTrendyItem.ViewHolder> {
        private ArrayList<Item> mDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public  class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView mPrice;
            public ImageView mImage;


            public ViewHolder(View v) {
                super(v);
                mPrice = v.findViewById(R.id.rv_simple_price);
                mImage = v.findViewById(R.id.rv_simple_image);

            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public FeatureTrendyItem(ArrayList<Item> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public FeatureTrendyItem.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.custom_feature_item_3, parent, false);

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            holder.mPrice.setText("$"+mDataset.get(position).getPrice());
            //Picasso.with(getContext()).load(mDataset.get(position).img_src).into(holder.mImage);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }

    public class EqualSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int mSpaceHeight;

        public EqualSpaceItemDecoration(int mSpaceHeight) {
            this.mSpaceHeight = mSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = mSpaceHeight;
            outRect.top = mSpaceHeight;
            outRect.left = mSpaceHeight;
            outRect.right = mSpaceHeight;
        }
    }
}
