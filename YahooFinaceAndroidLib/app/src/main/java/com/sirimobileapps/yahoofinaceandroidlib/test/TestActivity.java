package com.sirimobileapps.yahoofinaceandroidlib.test;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.sirimobileapps.yahoofinaceandroidlib.R;
import com.sirimobileapps.yahoofinacelib.classes.CompanySymbol;
import com.sirimobileapps.yahoofinacelib.classes.Share;
import com.sirimobileapps.yahoofinacelib.interfaces.OnCompanySymbolListener;
import com.sirimobileapps.yahoofinacelib.interfaces.OnShareGetListener;
import com.sirimobileapps.yahoofinacelib.utils.Utils;
import com.sirimobileapps.yahoofinacelib.view.EditDropBox;

import java.util.ArrayList;

/**
 * Created by smart on 5/6/15.
 */
public class TestActivity extends Activity {

     String TAG = "TestActivity";

    String items[] = {"siva","phani","srikrishna"};

    EditDropBox mEditDropBox = null;
    Button mButton = null;
    ListView mView = null;

    protected void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);

        Log.d(TAG, "oncreate");

        setContentView(R.layout.testlayout);
        mEditDropBox = (EditDropBox)(findViewById(R.id.editText));

        mView = (ListView)(findViewById(R.id.listView));

        mButton = (Button)(findViewById(R.id.addButton));

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  mEditDropBox.enableListView();
                mView.setAdapter(new ArrayAdapter<String>(TestActivity.this,android.R.layout.simple_list_item_1,items));

            }
        });

    }




}
