package com.sirimobileapps.yahoofinaceandroidlib.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.sirimobileapps.yahoofinaceandroidlib.R;
import com.sirimobileapps.yahoofinacelib.classes.News;
import com.sirimobileapps.yahoofinacelib.classes.Share;
import com.sirimobileapps.yahoofinacelib.interfaces.OnGetNewsListener;
import com.sirimobileapps.yahoofinacelib.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by smart on 23/6/15.
 */
public class ShareDetailActivity extends Activity implements OnGetNewsListener {


    private TextView mSharePrice       = null;
    private TextView mShareName        = null;
    private TextView mShareSymbol      = null;
    private TextView mShareExchange    = null;
    private TextView mShareExchangePer = null;

    private TextView mShareDaysHigh    = null;
    private TextView mShareDaysLow     = null;
    private TextView mShareYearsHigh   = null;
    private TextView mShareYearLow     = null;

    private Share mShareData = null;
    private String TAG = "ShareDetailActivity";
    private NewsAdapter mNewsAdapter = null;

    private ListView mNewsList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharedetaillayout);
        Intent intent = getIntent();
        if(intent != null)
        {

            mSharePrice       = (TextView)findViewById(R.id.share_detail_price);
            mShareName        = (TextView)findViewById(R.id.share_detail_name);
            mShareSymbol      = (TextView)findViewById(R.id.share_detail_symbol);
            mShareExchange    = (TextView)findViewById(R.id.share_detail_exchange);
            mShareExchangePer = (TextView)findViewById(R.id.share_detail_exchange_per);
            mShareDaysHigh    = (TextView)findViewById(R.id.share_days_high);
            mShareDaysLow     = (TextView)findViewById(R.id.share_days_low);
            mShareYearsHigh   = (TextView)findViewById(R.id.share_years_high);
            mShareYearLow     = (TextView)findViewById(R.id.share_years_low);

            mNewsList = (ListView)findViewById(R.id.newslist);

            mShareData = (Share) intent.getSerializableExtra("data");
            Log.d(TAG,"mShareData::"+mShareData );
            mSharePrice.setText(mShareData.price);
            mShareName.setText(mShareData.name);
            mShareSymbol.setText(mShareData.symbol);
            Utils.getNews(mShareData.symbol,this);
            mShareExchange.setText(mShareData.change);
            mShareExchangePer.setText(mShareData.chg_percent);
            mShareDaysHigh.setText("Days High : "+(mShareData.days_high).substring(0,6));
            mShareDaysLow.setText("Days Low : "+mShareData.days_low.substring(0,6));
            mShareYearsHigh.setText("Years High : "+mShareData.years_high.substring(0,6));
            mShareYearLow.setText("Years Low : "+mShareData.years_low.substring(0,6));

        }
    }

    @Override
    public void onNewsAvailable(ArrayList<News> newsList) {
        if(newsList != null ) {
            Log.d(TAG, "newsList::"+newsList);
            mNewsAdapter = new NewsAdapter(this, 0, newsList);
            mNewsAdapter.setNewsList(newsList);
            mNewsList.post(new Runnable() {
                @Override
                public void run() {
                    mNewsList.setAdapter(mNewsAdapter);
                }
            });

        }
        else
        {
            Log.d(TAG, "newsList::"+newsList);
        }
    }
}
