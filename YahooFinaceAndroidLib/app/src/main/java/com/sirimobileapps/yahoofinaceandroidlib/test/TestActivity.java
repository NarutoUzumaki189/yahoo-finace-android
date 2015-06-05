package com.sirimobileapps.yahoofinaceandroidlib.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.sirimobileapps.yahoofinaceandroidlib.R;
import com.sirimobileapps.yahoofinacelib.classes.CompanySymbol;
import com.sirimobileapps.yahoofinacelib.interfaces.OnCompanySymbolListener;
import com.sirimobileapps.yahoofinacelib.utils.Utils;

import java.util.ArrayList;

/**
 * Created by smart on 5/6/15.
 */
public class TestActivity extends Activity {

     String TAG = "TestActivity";


    protected void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);

        Log.d(TAG, "oncreate");

        setContentView(R.layout.testlayout);


        Utils.searchCompanySymbol("yahoo", new OnCompanySymbolListener() {
            @Override
            public void onResult(ArrayList<CompanySymbol> list) {
                Log.d(TAG, "" + list);
            }
        });



    }
}
