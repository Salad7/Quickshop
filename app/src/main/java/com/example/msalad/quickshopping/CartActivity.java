package com.example.msalad.quickshopping;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msalad.quickshopping.Database.CartItem;
import com.example.msalad.quickshopping.Database.CartListOfItems;
import com.example.msalad.quickshopping.Database.InventoryItem;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
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
    ImageView backBtn;
    CardView scanAndGo;
    String EditTextValue;
    Bitmap bitmap;

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
        backBtn = findViewById(R.id.item_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CartActivity.this,MainActivity.class);
                i.putExtra("cart",cartListOfItems);
                startActivity(i);
                //finish();
            }
        });

        scanAndGo = findViewById(R.id.scanandgo);
        scanAndGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CartActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_qr_generator, null);
                dialogBuilder.setView(dialogView);
                ImageView qr_code = dialogView.findViewById(R.id.imageView3);

                dialogBuilder.setTitle("QR Generator");
                final AlertDialog b = dialogBuilder.create();
                dialogBuilder.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        b.dismiss();
                    }
                });

                EditTextValue = "";


                cartListOfItems.getCart();
                for(int i = 0; i < cartListOfItems.getCart().size(); i++){
                    String barcode = cartListOfItems.getCart().get(i).getItemKey(); //Gets item from cart
                    int quantity = cartListOfItems.getCart().get(i).getQuantity();

                    if(i == 0){
                        EditTextValue += barcode;
                        EditTextValue += "*";
                        EditTextValue += quantity;
                    } else {
                        EditTextValue += "-";
                        EditTextValue += barcode;
                        EditTextValue += "*";
                        EditTextValue += quantity;
                    }


                }



                try{
                    bitmap = TextToImageEncode(EditTextValue);

                    qr_code.setImageBitmap(bitmap);

                } catch(WriterException e){
                    e.printStackTrace();
                }

                dialogBuilder.show();
            }

        });

    }
//
    public void loadCart(){
        Log.d("CartActivity","Loading cart");
        customCartItemAdapter = new CustomCartItemAdapter(this,R.layout.carty,cartListOfItems.getCart());
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
            Spinner quality_spinner = view.findViewById(R.id.spinner2);
            TextView name = view.findViewById(R.id.textView5);
            TextView price =  view.findViewById(R.id.textView6);
            Button saveForLater = view.findViewById(R.id.button2);
//            TextView remove = view.findViewById(R.id.item_remove);
            ImageView delete =  view.findViewById(R.id.imageView2);
            ImageView img =  view.findViewById(R.id.imageView);
            Spinner quantity = view.findViewById(R.id.spinner2);
            //this code sets the values of the objects to values from the arrays
            price.setText(items.get(position).getPrice()+"");
            name.setText(items.get(position).getName());

            quantity.setSelection(getIndex(quantity, items.get(position).getQuantity()+""));
            //sp.setText(items.get(position).getQuantity()+"");
            //Picasso.with(ctx).load(items.get(position).getImage()).into(img);
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

    //private method of your class
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    500, 500, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        ContextCompat.getColor(CartActivity.this,R.color.qrColor):ContextCompat.getColor(CartActivity.this, R.color.qrColor_white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_8888);



        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }


}
