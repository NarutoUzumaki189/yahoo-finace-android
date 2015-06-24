package com.sirimobileapps.yahoofinaceandroidlib.test;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sirimobileapps.yahoofinaceandroidlib.R;
import com.sirimobileapps.yahoofinacelib.classes.CompanySymbol;
import com.sirimobileapps.yahoofinacelib.view.DropBoxAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smart on 22/6/15.
 */
public class CustomDropBoxAdapter extends DropBoxAdapter {

    private LayoutInflater mInflater;
    private String TAG = "CustomDropBoxAdapter";


    public CustomDropBoxAdapter(Context context,int resource ,ArrayList<CompanySymbol> list) {
        super(context,0,list);
        init(context);
    }

    private void init(Context context)
    {
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Log.d(TAG, "getView");
        View view;
        TextView symbol;
        TextView shareName;
        if(convertView == null)
        {
            view = mInflater.inflate(R.layout.symboldroplist,parent ,true );
        }
        else
        {
            view = convertView;
        }
         Holder holder = (Holder)view.getTag();
        if(holder == null)
        {
             symbol       = (TextView)(view.findViewById(R.id.symbol));
             shareName    = (TextView)(view.findViewById(R.id.share_name));
            holder = new Holder(symbol,shareName);
        }

        symbol = holder.symbol;
        shareName = holder.shareName;

        if(getObjects() != null) {
            CompanySymbol symbolData = getObjects().get(position);
            symbol.setText(symbolData.symbol);
            shareName.setText(symbolData.companyName);
            Log.d(TAG, "u r not null hero");
        }
        else
        {
            Log.d(TAG,"u r null hero");
        }


        return view;
    }

    public static class Holder {
        TextView symbol;
        TextView shareName;
        public Holder(  TextView symbol,TextView shareName)
        {
            this.symbol = symbol;
            this.shareName = shareName;
        }
    }
}
