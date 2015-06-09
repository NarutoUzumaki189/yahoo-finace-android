package com.sirimobileapps.yahoofinacelib.classes;

/**
 * Created by smart on 5/6/15.
 */
public class CompanySymbol {

       public String symbol;
       public String companyName;
       public String exchange;
       public String type;
       public String exchDisp;
       public String typeDisp;

    public CompanySymbol (String symbol, String companyName,String exchange,String type,String exchDisp, String typeDisp )
    {
        this.symbol = symbol;
        this.companyName = companyName;
        this.exchange = exchange;
        this.type = type;
        this.exchDisp = exchDisp;
        this.typeDisp = typeDisp;
    }

}
