package com.sirimobileapps.yahoofinacelib.interfaces;

import com.sirimobileapps.yahoofinacelib.classes.Share;

import java.util.ArrayList;

/**
 * Created by smart on 15/6/15.
 */
public interface OnShareGetListener {
    void onGetShareDetails(ArrayList<Share> list);
}
