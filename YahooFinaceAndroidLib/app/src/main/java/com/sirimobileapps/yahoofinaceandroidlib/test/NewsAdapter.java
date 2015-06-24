package com.sirimobileapps.yahoofinaceandroidlib.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sirimobileapps.yahoofinaceandroidlib.R;
import com.sirimobileapps.yahoofinacelib.classes.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smart on 24/6/15.
 */
public class NewsAdapter extends ArrayAdapter
{
    private String TAG = "NewsAdapter";
    private LayoutInflater mInflater = null;
    private ArrayList<News> newsList ;
    public NewsAdapter(Context context, int resource) {
        super(context, resource);
        init(context);
    }

    public NewsAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        init(context);
    }

    public NewsAdapter(Context context, int resource, Object[] objects) {
        super(context, resource, objects);
        init(context);
    }

    public NewsAdapter(Context context, int resource, int textViewResourceId, Object[] objects) {
        super(context, resource, textViewResourceId, objects);
        init(context);
    }

    public NewsAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        init(context);
    }

    public NewsAdapter(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
        init(context);
    }

    public void setNewsList(ArrayList list)
    {
        newsList = list;
    }

    public ArrayList getObjects()
    {
        return  newsList;
    }

    private void init(Context context)
    {
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        TextView title = null;
        TextView date = null ;
        TextView link = null;

        if(convertView == null)
        {
            view = (View)mInflater.inflate(R.layout.newslayout, parent, false);
        }
        else
        {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder)view.getTag();

        if(holder == null)
        {
            title = (TextView)view.findViewById(R.id.title);
            date = (TextView)view.findViewById(R.id.date);
            link = (TextView)view.findViewById(R.id.link);

            holder = new ViewHolder( title , date ,link);
            view.setTag(holder);
        }

        title   = holder.mTitle;
        date     = holder.mDate;
        link    = holder.mLink;


        if( newsList != null)
        {
            News news = newsList.get(position);
            title.setText(news.title);
            link.setText(news.link);
            date.setText(news.date);


        }

        return view;
    }


    static class ViewHolder
    {
        TextView mTitle;
        TextView mDate;
        TextView mLink;


        public ViewHolder(TextView mTitle,TextView mDate,TextView mLink)
        {
            this.mTitle = mTitle;
            this.mDate = mDate;
            this.mLink = mLink;

        }
    }


}
