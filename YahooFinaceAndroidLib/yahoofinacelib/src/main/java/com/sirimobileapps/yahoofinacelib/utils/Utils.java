package com.sirimobileapps.yahoofinacelib.utils;

import android.nfc.Tag;
import android.util.Log;

import com.sirimobileapps.yahoofinacelib.classes.CompanySymbol;
import com.sirimobileapps.yahoofinacelib.interfaces.OnCompanySymbolListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by smart on 5/6/15.
 */
public  class Utils {



    private static String DEBUG_TAG = "Utils";



    /**
     *
     * @param symbol_query Pass the string with want to search.
     * @param listener     Listener to get the result of string searched.
     */
    public static void  searchCompanySymbol(String symbol_query ,OnCompanySymbolListener listener)
    {
        ArrayList<CompanySymbol> list = null;

       final String symbol_url = "http://d.yimg.com/autoc.finance.yahoo.com/autoc?query="+symbol_query+"&callback=YAHOO.Finance.SymbolSuggest.ssCallback";


            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                    Log.d(DEBUG_TAG, openUrl(symbol_url));

                }
                catch (Exception e)
                {
                    Log.d(DEBUG_TAG , "error came::"+e);
                }
                }
            });


        thread.start();


    }

    /**
     *
     * @param searchUrl Pass the url that need to be opened.
     * @return    Result in json string . returns null or empty string if failed
     * to open url.
     */
    public static String openUrl(String searchUrl) throws IOException
    {

        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.


        Log.d(DEBUG_TAG , "searchUrl::"+searchUrl);

        try {
            URL url = new URL(searchUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();

            is = conn.getInputStream();

            Log.d(DEBUG_TAG, "The response is: " + response);
            // Convert the InputStream into a string
            String contentAsString = readIt(is);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }

    }


    public static String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream,"UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        stream.close();
        return sb.toString();
    }

    

}
