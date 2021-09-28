package com.example.earthquake;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public final class JsonQuery {



    private static final String LOG_TAG = JsonQuery.class.getSimpleName();

    private JsonQuery(){

    }

    public static ArrayList<Earthquake> fetchEarthquake(String requestUrl){

        URL url = createUrl(requestUrl);
        String jsonres = null;
        try {
            jsonres = makehttprequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG,"problem making http request",e);
        }

        ArrayList<Earthquake> jsonlist = extractvaluefromJson(jsonres);;

        return jsonlist;
    }

    // CREATE URL
    public static URL createUrl(String stringurl){
        URL url = null;
        try {
            url = new URL(stringurl);

        }catch (MalformedURLException e){
            Log.e(LOG_TAG,"problem building the url", e);
        }
        return url;
    }

    public static String makehttprequest(URL url) throws IOException {
        String jsonresponse = "";
        if (url == null) return jsonresponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode()==200) {
                inputStream = urlConnection.getInputStream();
                jsonresponse = resposnsecode(inputStream);
            }else
                Log.e(LOG_TAG,"resposne code error"+urlConnection.getResponseCode());
        }catch (IOException e){
            Log.e(LOG_TAG,"probing earthquake problem", e);
        }finally {
            if (urlConnection!=null)
                urlConnection.disconnect();
            if (inputStream!=null)
                inputStream.close();
        }
        return jsonresponse;
    }

    public static String resposnsecode(InputStream inputStream) throws IOException{
        StringBuilder collect = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        String line = reader.readLine();
        while (line!=null){
            collect.append(line);
            line=reader.readLine();
        }
        return collect.toString();
    }

    public static ArrayList<Earthquake> extractvaluefromJson(String earthjson){
        if(TextUtils.isEmpty(earthjson))
            return null;

        ArrayList<Earthquake> earthquakesvalues = new ArrayList<>();
        try {
            JSONObject earthquakeobject = new JSONObject(earthjson);
            JSONArray earthqarray = earthquakeobject.getJSONArray("features");
            for (int i =0;i<earthqarray.length();i++){
                JSONObject currentobject = earthqarray.getJSONObject(i);
                JSONObject properties = currentobject.getJSONObject("properties");
                double magnitude = properties.getDouble("mag");
                String location = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");

                Earthquake earthquakevalue = new Earthquake(magnitude,location,time,url);
                earthquakesvalues.add(earthquakevalue);

            }

        }catch (JSONException e){
            Log.e("Json_phrase" , "problem phasing the earthquake result",e);

        }
        return earthquakesvalues;
    }

}
