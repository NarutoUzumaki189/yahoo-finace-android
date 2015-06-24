package com.sirimobileapps.yahoofinacelib.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;

import com.sirimobileapps.yahoofinacelib.classes.CompanySymbol;
import com.sirimobileapps.yahoofinacelib.interfaces.OnCompanySymbolListener;
import com.sirimobileapps.yahoofinacelib.interfaces.onDropBoxDataChange;
import com.sirimobileapps.yahoofinacelib.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smart on 19/6/15.
 */
public class EditDropBox extends AutoCompleteTextView  implements OnCompanySymbolListener , AdapterView.OnItemClickListener{


    private int dropDownLayout = -1;
    private ArrayList<CompanySymbol> symbols = new ArrayList<>();
    private Context mContext ;
    private DropBoxAdapter mAdapter = null;
    private String TAG = "EditDropBox";
    private onDropBoxDataChange mListener = null;


    public EditDropBox(Context context) {
        super(context);
        init(context);
    }

    public EditDropBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }



    public EditDropBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    public void setListViewLayout(int layout)
    {
        dropDownLayout = layout;
    }

    private void addTextChangedListener()
    {
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                  Utils.searchCompanySymbol(s.toString(), EditDropBox.this);
            }
        });
    }



    private void init(Context context)
    {
        this.setThreshold(1);
        mContext = context;
        addTextChangedListener();
        this.setOnItemClickListener(this);
    }


    /**
     *
     * @param adapter can be NULL for default view or Implement DropBoxAdapter for Custom drop view
     */
    public void setAdapter(DropBoxAdapter adapter ,onDropBoxDataChange listener) {
        if (adapter == null) {
            adapter = new DropBoxAdapter(mContext,0 ,symbols);
        }
        mAdapter = adapter;

        mListener = listener;
        super.setAdapter(adapter);
    }

    public void setAdapter(DropBoxAdapter adapter)
    {
        if (adapter == null) {
            adapter = new DropBoxAdapter(mContext,0 ,symbols);
        }
        mAdapter = adapter;
        super.setAdapter(adapter);
    }

    @Override
    public void onResult(ArrayList<CompanySymbol> list) {

        symbols.clear();
        mAdapter.setObjects(list);
        Log.d(TAG,"list::"+list+":mAdapter:"+mAdapter);
        if(mAdapter != null && list != null) {
            symbols.addAll(list);
            this.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                }
            });

            if(mListener != null)
                mListener.onDataChanged();
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(symbols != null && symbols.size() > 0 ) {
            this.setOnItemClickListener(null);
            CompanySymbol symbol = symbols.get(position);
            this.setText(symbol.symbol);
            this.setOnItemClickListener(this);
        }
    }
}
