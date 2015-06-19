package com.sirimobileapps.yahoofinaceandroidlib.test;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import android.os.Debug;
import android.util.Log;

import com.sirimobileapps.yahoofinaceandroidlib.R;
import com.sirimobileapps.yahoofinacelib.classes.CompanySymbol;
import com.sirimobileapps.yahoofinacelib.classes.Share;
import com.sirimobileapps.yahoofinacelib.interfaces.OnCompanySymbolListener;
import com.sirimobileapps.yahoofinacelib.interfaces.OnShareGetListener;
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


       Utils.searchCompanySymbol("AXIS", new OnCompanySymbolListener() {
            @Override
            public void onResult(ArrayList<CompanySymbol> list) {
                Log.d(TAG, "" + list);
                getShareValueDetails(list);
            }
        });



       

    }


    private IntentService mService = new IntentService("Myservice") {
        @Override
        protected void onHandleIntent(Intent intent) {

        }

        public void printText(String msg)
        {
            Log.d(TAG," ");
        }
    };



    private void getShareValueDetails(ArrayList<CompanySymbol> symbolList )
    {
        if(symbolList != null) {
            String SymbolsString = "";
            for (CompanySymbol symbol : symbolList) {
                SymbolsString += symbol.symbol+",";
            }

            Utils.getShareDetails(SymbolsString, new OnShareGetListener() {
                @Override
                public void onGetShareDetails(ArrayList<Share> list) {
                    if(list != null)
                    {
                        for(Share share : list)
                        {
                            Log.d(TAG,"company name "+share.name+" share price :"+share.price + " change :"+share.change);
                        }
                    }
                }
            });
        }
        else
        {
            Log.d(TAG , "SymbolList is null");
        }
    }

}
