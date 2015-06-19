package com.sirimobileapps.yahoofinacelib.interfaces;

import com.sirimobileapps.yahoofinacelib.classes.News;

import java.util.ArrayList;

/**
 * Created by smart on 19/6/15.
 */
public interface OnGetNewsListener {
    void onNewsAvailable(ArrayList<News> newsList);

}
