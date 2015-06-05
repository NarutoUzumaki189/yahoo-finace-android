package com.sirimobileapps.yahoofinacelib.interfaces;

import com.sirimobileapps.yahoofinacelib.classes.CompanySymbol;

import java.util.ArrayList;

/**
 * Created by smart on 5/6/15.
 */
public interface OnCompanySymbolListener {

     void onResult(ArrayList<CompanySymbol> list);
}
