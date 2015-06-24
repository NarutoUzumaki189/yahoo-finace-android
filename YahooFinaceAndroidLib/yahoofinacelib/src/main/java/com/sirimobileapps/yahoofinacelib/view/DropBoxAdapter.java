package com.sirimobileapps.yahoofinacelib.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.sirimobileapps.yahoofinacelib.classes.CompanySymbol;
import com.sirimobileapps.yahoofinacelib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smart on 22/6/15.
 */
public class DropBoxAdapter extends ArrayAdapter<ArrayList> {

    private String TAG = "DropBoxAdapter";
    private LayoutInflater mInflater;
    public ArrayList<CompanySymbol> symbols = new ArrayList<CompanySymbol>();
    KNoFilter filter = new KNoFilter();
    private int layout = -1;



    public DropBoxAdapter(Context context , int resource ,ArrayList list) {
        super(context,0,list);
        init(context);
    }

    private void init(Context context)
    {
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setObjects(ArrayList list) {
        symbols = list;
    }

    public ArrayList<CompanySymbol> getObjects()
    {
        return symbols;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }


    private class KNoFilter extends Filter {


        @Override
        protected FilterResults performFiltering(CharSequence arg0) {
            FilterResults result = new FilterResults();
            result.values = symbols;
            if(symbols != null) {
                result.count = symbols.size();
            }
            else
            {
                result.count = 0 ;
            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence arg0, FilterResults arg1) {
            notifyDataSetChanged();
        }
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Log.d(TAG, "getView");
        View view;
        TextView symbol;
        TextView shareName;
        if(convertView == null)
        {
            view = mInflater.inflate(R.layout.symboldroplist,parent ,false );
        }
        else
        {
            view = convertView ;
        }
        Holder holder = (Holder)view.getTag();
        if(holder == null)
        {
            symbol       = (TextView)(view.findViewById(R.id.symbol));
            shareName    = (TextView)(view.findViewById(R.id.share_name));
            holder = new Holder(symbol,shareName);
            view.setTag(holder);
        }

        symbol = holder.symbol;
        shareName = holder.shareName;



        if(getObjects() != null && getObjects().size() > 0) {
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
