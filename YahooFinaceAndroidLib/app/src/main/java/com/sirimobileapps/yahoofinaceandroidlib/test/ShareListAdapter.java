package com.sirimobileapps.yahoofinaceandroidlib.test;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sirimobileapps.yahoofinaceandroidlib.R;
import com.sirimobileapps.yahoofinacelib.classes.Share;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smart on 23/6/15.
 */
public class ShareListAdapter extends ArrayAdapter<Share> {

    private LayoutInflater mInflater = null;

    private ArrayList<Share> mShareList;

    public ShareListAdapter(Context context, int resource) {
        super(context, resource);
        init(context);
    }

    public ShareListAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        init(context);
    }

    public ShareListAdapter(Context context, int resource, List<Share> objects) {
        super(context, resource, objects);
        init(context);
    }

    public ShareListAdapter(Context context, int resource, int textViewResourceId, Share[] objects) {
        super(context, resource, textViewResourceId, objects);
        init(context);
    }

    public ShareListAdapter(Context context, int resource, Share[] objects) {
        super(context, resource, objects);
        init(context);
    }

    public ShareListAdapter(Context context, int resource, int textViewResourceId, List<Share> objects) {
        super(context, resource, textViewResourceId, objects);
        init(context);
    }

    private void init(Context context)
    {
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList list)
    {
        mShareList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        TextView shareSymbol = null;
        TextView shareName = null ;
        TextView sharePrice = null;
        TextView shareExchange = null;
        if(convertView == null)
        {
            view = (View)mInflater.inflate(R.layout.sharelistview, parent, false);
        }
        else
        {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder)view.getTag();

        if(holder == null)
        {
             shareSymbol = (TextView)view.findViewById(R.id.shareSymbol);
             shareName = (TextView)view.findViewById(R.id.shareName);
             sharePrice = (TextView)view.findViewById(R.id.sharePrice);
             shareExchange = (TextView)view.findViewById(R.id.shareChange);
             holder = new ViewHolder( shareSymbol , shareName ,sharePrice, shareExchange );
             view.setTag(holder);
        }

        shareSymbol   = holder.mShareSymbol;
        shareName     = holder.mShareName;
        sharePrice    = holder.mSharePrice;
        shareExchange = holder.mShareExchange;

        if( mShareList != null)
        {
            Share share = mShareList.get(position);
            shareSymbol.setText(share.symbol);
            shareName.setText(share.name);
            shareExchange.setText(share.change);
            float value = Float.parseFloat(share.change);
            if(value > 0)
                shareExchange.setTextColor(Color.GREEN);
            else
                shareExchange.setTextColor(Color.RED);
            sharePrice.setText(share.price);

        }

        return view;
    }


    static class ViewHolder
    {
        TextView mShareSymbol;
        TextView mShareName;
        TextView mSharePrice;
        TextView mShareExchange;

        public ViewHolder(TextView mShareSymbol,TextView mShareName ,TextView mSharePrice,TextView mShareExchange)
        {
            this.mShareSymbol = mShareSymbol;
            this.mShareName = mShareName;
            this.mSharePrice = mSharePrice;
            this.mShareExchange = mShareExchange;
        }
    }


}
