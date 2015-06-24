package com.sirimobileapps.yahoofinacelib.classes;

import java.io.Serializable;

/**
 * Created by smart on 12/6/15.
 */
public class Share implements Serializable {

    public String change      = "";
    public String chg_percent = "";
    public String days_high   = "";
    public String days_low    = "";
    public String name        = "";
    public String price       = "";
    public String volume      = "";
    public String years_high  = "";
    public String years_low   = "";
    public String symbol      = "";

    public Share(String symbol ,String change,String chg_percent ,String days_high   ,String days_low    ,String name  ,String price ,String volume,String years_high  ,String years_low )
    {
        this.change = change;
        this.chg_percent = chg_percent;
        this.days_high = days_high;
        this.days_low = days_low;
        this.name = name;
        this.price = price;
        this.volume = volume;
        this.years_high = years_high;
        this.years_low = years_low;
        this.symbol = symbol;
    }

}
