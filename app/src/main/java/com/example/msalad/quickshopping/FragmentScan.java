package com.example.msalad.quickshopping;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.content.ContentValues.TAG;

/**
 * Created by msalad on 1/16/2018.
 */

public class FragmentScan extends Fragment  implements ZXingScannerView.ResultHandler  {

    private ZXingScannerView mScannerView;
    private MainActivity mainActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scan,container,false);
        mScannerView = v.findViewById(R.id.scanner);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        //if(!mScannerView.isActivated()){
            //mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
            //mScannerView.startCamera();
        //}
        mScannerView.resumeCameraPreview(this);

    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v(TAG, rawResult.getText()); // Prints scan results
        Toast.makeText(getContext(),rawResult.getText(),Toast.LENGTH_SHORT).show();
        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
        // If you would like to resume scanning, call this method below:
       // mScannerView.resumeCameraPreview(this);
        mainActivity.findItem(rawResult.getText(), mScannerView,this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }
}
