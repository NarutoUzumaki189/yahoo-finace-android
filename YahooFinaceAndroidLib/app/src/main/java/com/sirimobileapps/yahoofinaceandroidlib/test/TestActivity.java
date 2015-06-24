package com.sirimobileapps.yahoofinaceandroidlib.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.sirimobileapps.yahoofinaceandroidlib.R;
import com.sirimobileapps.yahoofinacelib.classes.CompanySymbol;
import com.sirimobileapps.yahoofinacelib.classes.Share;
import com.sirimobileapps.yahoofinacelib.interfaces.OnShareGetListener;
import com.sirimobileapps.yahoofinacelib.utils.Utils;
import com.sirimobileapps.yahoofinacelib.view.EditDropBox;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by smart on 5/6/15.
 */
public class TestActivity extends Activity implements View.OnClickListener , OnShareGetListener ,AdapterView.OnItemClickListener {

     String TAG = "TestActivity";
    public final static String SHARE_HASHMAP_KEY = "SHARE_HASHMAP_KEY" ;
    EditDropBox mSymbolSearchView = null;

    Button mAddShare = null;

    ListView mMyShareListView = null;

    ArrayList<Share> mMyShareList = new ArrayList();

    ArrayList<String> mMyShareSymbols = new ArrayList<String>();

    ShareListAdapter myShareListAdapter = null ;


    protected void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);

        Log.d(TAG, "oncreate");

        setContentView(R.layout.testlayout);
        mSymbolSearchView = (EditDropBox)(findViewById(R.id.searchView));

        mMyShareListView = (ListView)(findViewById(R.id.listView));

        mAddShare = (Button)(findViewById(R.id.addButton));

        mSymbolSearchView.setAdapter(null);

        mAddShare.setOnClickListener(this);

        myShareListAdapter = new ShareListAdapter(this,0,mMyShareList);

        mMyShareListView.setAdapter(myShareListAdapter);
        mMyShareListView.setOnItemClickListener(this);

    }


    @Override
    public void onClick(View v) {
        String newSymbol = mSymbolSearchView.getText().toString();
        if(mMyShareSymbols != null && !isSymbolExist(mMyShareSymbols ,newSymbol)) {

            Utils.getShareDetails(newSymbol, this);
        }
    }

    public boolean isSymbolExist(ArrayList<String> list , String newSymbol)
    {
        for(String string : list)
        {
            if(string.equalsIgnoreCase(newSymbol))
                return true;
        }
        return false;
    }


    @Override
    public void onGetShareDetails(ArrayList<Share> list , String symbolString) {
        if(list != null) {
            String symbols [] = symbolString.split(",");
            for(int i = 0 ; i < symbols.length ; i++)
            {
                mMyShareSymbols.add(symbols[i]);
            }
            mMyShareList.addAll(list);
            myShareListAdapter.setData(mMyShareList);
            mMyShareListView.post(new Runnable() {
                @Override
                public void run() {
                    myShareListAdapter.notifyDataSetChanged();
                }
            });

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap hashMap = new HashMap(1);

        Share share = mMyShareList.get(position);
        Log.d(TAG ,"hashMap::"+share);
        hashMap.put(share, "data");

        Intent intent = new Intent(this , ShareDetailActivity.class);
        intent.putExtra("data",share);
        startActivity(intent);
    }
}
