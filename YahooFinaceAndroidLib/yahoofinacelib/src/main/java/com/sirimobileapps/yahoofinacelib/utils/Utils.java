package com.sirimobileapps.yahoofinacelib.utils;

import android.util.Log;

import com.sirimobileapps.yahoofinacelib.classes.CompanySymbol;
import com.sirimobileapps.yahoofinacelib.interfaces.OnCompanySymbolListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;



/**
 * Created by smart on 5/6/15.
 */
public  class Utils {



    private static String DEBUG_TAG = "Utils";

    private static String YAHOO_RESULT_STRING = "YAHOO.Finance.SymbolSuggest.ssCallback(";



    /**
     *
     * @param symbol_query Pass the string with want to search.
     * @param listener     Listener to get the result of string searched.
     */
    public static void  searchCompanySymbol(String symbol_query , final OnCompanySymbolListener listener)
    {
        ArrayList<CompanySymbol> list = null;

       final String symbol_url = "http://d.yimg.com/autoc.finance.yahoo.com/autoc?query="+symbol_query+"&callback=YAHOO.Finance.SymbolSuggest.ssCallback";


            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if(listener !=null)
                            listener.onResult(convertJsonStringToList(removeYahooDataFromJsonString(openUrl(symbol_url))));

                    Log.d(DEBUG_TAG, " Got Symbols");

                }
                catch (Exception e)
                {
                    if(listener !=null)
                        listener.onResult(null);
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


        Log.d(DEBUG_TAG, "searchUrl::" + searchUrl);

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


    private static String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream,"UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        stream.close();
        return sb.toString();
    }

    private static String removeYahooDataFromJsonString(String data)
    {
        int len = YAHOO_RESULT_STRING.length();
        String result = "";
        result = data.substring(len,data.length()-2);

        Log.d(DEBUG_TAG, "result::" + result);
        return result;
    }


   private static ArrayList convertJsonStringToList(String jsonString) throws JSONException
   {


       JSONObject jsonSymbolObject = new JSONObject(jsonString);

       JSONArray jsonSymbolArray   = jsonSymbolObject.getJSONObject("ResultSet").getJSONArray("Result");

       Log.d(DEBUG_TAG , "jsonSymbolArray::"+jsonSymbolArray);

       int length = jsonSymbolArray.length();


       ArrayList list = new ArrayList(length);

       for(int i = 0 ; i < length ; i++)
       {
           JSONObject object = jsonSymbolArray.getJSONObject(i);
           CompanySymbol symbol = new CompanySymbol(object.getString("symbol"),object.getString("name"),object.getString("exch"),object.getString("type"),object.getString("exchDisp"),object.getString("typeDisp"));
           list.add(symbol);
       }

       return list;
   }




}
