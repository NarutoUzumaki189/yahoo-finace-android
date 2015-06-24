package com.sirimobileapps.yahoofinacelib.utils;

import android.util.Log;

import com.sirimobileapps.yahoofinacelib.classes.CompanySymbol;
import com.sirimobileapps.yahoofinacelib.classes.News;
import com.sirimobileapps.yahoofinacelib.classes.Share;
import com.sirimobileapps.yahoofinacelib.interfaces.OnCompanySymbolListener;
import com.sirimobileapps.yahoofinacelib.interfaces.OnGetNewsListener;
import com.sirimobileapps.yahoofinacelib.interfaces.OnShareGetListener;

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

    private static boolean enableLog = true;

    /**
     *
     * @param symbol_query Pass the string with want to search.Ex TTM, SBIN.NS , TTM,SBIN.NS , etc.
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
                            listener.onResult(jsonToCompanySymbolList(removeYahooDataFromJsonString(openUrl(symbol_url))));

                    if(enableLog)
                       Log.d(DEBUG_TAG, " Got Symbols");

                }
                catch (Exception e)
                {
                    if(listener !=null)
                        listener.onResult(null);
                    if(enableLog)
                       Log.e(DEBUG_TAG , "error came::"+e);
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
        if(enableLog)
           Log.d(DEBUG_TAG, "result::" + result);
        return result;
    }


   private static ArrayList jsonToCompanySymbolList(String jsonString) throws JSONException
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



    private  static ArrayList jsonToShareList(String jsonString , String symbolString) throws JSONException
    {
        if(enableLog)
           Log.d(DEBUG_TAG , "jsonToShareList::"+jsonString);

        JSONObject jsonSymbolObject = new JSONObject(jsonString);

        JSONArray jsonSymbolArray   = jsonSymbolObject.getJSONObject("list").getJSONArray("resources");
        if(enableLog)
           Log.d(DEBUG_TAG , "jsonSymbolArray::"+jsonSymbolArray);

        int length = jsonSymbolArray.length();

        String symbols[] = symbolString.split(",");


        ArrayList list = new ArrayList(length);

        for(int i = 0 ; i < length ; i++)
        {
            JSONObject object = jsonSymbolArray.getJSONObject(i).getJSONObject("resource").getJSONObject("fields");
            Share share = new Share(symbols[i],object.getString("change"),object.getString("chg_percent"),object.getString("day_high"),object.getString("day_low"),object.getString("name"),object.getString("price"),object.getString("volume"),object.getString("year_high"),object.getString("year_low"));
            list.add(share);
        }

        return list;
    }


    /***
     *
     * @param symbols   Ex SBIN.NS , For multiple shares SBIN.NS,TTM
     * @param listener  OnShareGetListener to get the details in Objects of Share through ArrayList.
     */
    public static void getShareDetails(final String symbols ,final OnShareGetListener listener)
    {
        ArrayList list = null;
        final String url = "http://finance.yahoo.com/webservice/v1/symbols/"+symbols+"/quote?format=json&view=detail";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(listener !=null)
                        listener.onGetShareDetails(jsonToShareList(openUrl(url),symbols),symbols);
                    if(enableLog)
                      Log.d(DEBUG_TAG, " Got got shares");

                }
                catch (Exception e)
                {
                    if(listener !=null)
                        listener.onGetShareDetails(null,symbols);
                    if(enableLog)
                      Log.e(DEBUG_TAG , "error came::"+e);
                }

            }
        });


        thread.start();

    }


    /***
     * @param symbol    Example SBIN.NS,TTM,etc.
     * @param listener  Listener to get the details back.
     */
  public static void getNews(String symbol , final OnGetNewsListener listener)
  {
      final String  url = "http://pipes.yahoo.com/pipes/pipe.run?_id=2FV68p9G3BGVbc7IdLq02Q&_render=json&feedcount=10&feedurl=http://finance.yahoo.com/rss/headline?s="+symbol;
      Thread thread = new Thread(new Runnable() {
          @Override
          public void run() {
              try {
                  if(listener != null)
                      listener.onNewsAvailable(parseJsonToNews(openUrl(url)));

              }
              catch (Exception e)
              {
                  Log.e(DEBUG_TAG ,"Error Came "+e);
                  if(listener != null)
                      listener.onNewsAvailable(null);
              }

          }
      });
      thread.start();
  }


    private static ArrayList<News> parseJsonToNews(String jsonString) throws JSONException
    {
        ArrayList newsList = null;
        JSONObject jsonNewsObject = new JSONObject(jsonString);
        Log.d(DEBUG_TAG , "newsjon"+jsonString);
        JSONArray jsonArray         = jsonNewsObject.getJSONObject("value").getJSONArray("items");
        int length = jsonArray.length();
        newsList = new ArrayList(length);
        for(int i=0 ; i<length ; i++)
        {
            JSONObject object = jsonArray.getJSONObject(i);
            News news = new News(object.getString("title"),object.getString("link"),object.getString("pubDate"));

            Log.d(DEBUG_TAG , "news::"+news);
         newsList.add(news);
        }
        Log.d(DEBUG_TAG , "newsList::"+newsList);
        return newsList;
    }

}
