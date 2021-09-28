package com.example.earthquake;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.AsyncTaskLoader;

import java.util.ArrayList;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {
    private String murl;



    public EarthquakeLoader(@NonNull Context context, String url) {
        super(context);
        murl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<Earthquake> loadInBackground() {
        if (murl==null) return null;

        ArrayList<Earthquake> earthq = JsonQuery.fetchEarthquake(murl);
        return earthq;
    }


}
