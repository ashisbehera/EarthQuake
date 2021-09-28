package com.example.earthquake;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.telephony.IccOpenLogicalChannelResponse;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    private static final String LOCATION_SEPARATOR = "of";

    public EarthquakeAdapter(@NonNull MainActivity context, List<Earthquake> earthquakes) {
        super(context,0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemview = convertView;
        if (listitemview == null){
            listitemview = LayoutInflater.from(getContext()).inflate
                    (R.layout.list_item,parent,false);
        }
        Earthquake currearthquake = getItem(position);

        TextView magnitudeview = listitemview.findViewById(R.id.magnitude);

        String formatedmagn = fortmatmag(currearthquake.getmMagnitude());
        magnitudeview.setText(formatedmagn);

        GradientDrawable magnitudecircle = (GradientDrawable) magnitudeview.getBackground();
        int magnitudecolor = magcolor(currearthquake.getmMagnitude());
        magnitudecircle.setColor(magnitudecolor);

        String originalLocation = currearthquake.getMlocation();
        String primaryLocation;
        String locationOfset;

        if (originalLocation.contains(LOCATION_SEPARATOR)){
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOfset = parts[0]+ LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        }else {
            locationOfset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        TextView locOfsetview = listitemview.findViewById(R.id.location_offset);
        locOfsetview.setText(locationOfset);
        TextView primlocview = listitemview.findViewById(R.id.primary_location);
        primlocview.setText(primaryLocation);

        Date dateobject = new Date(currearthquake.getMtimeinmilisec());
        String formateddate = formatdate(dateobject);
        String formatedtime = formattime(dateobject);
        TextView dateview = listitemview.findViewById(R.id.date);
        dateview.setText(formateddate);
        TextView timeview = listitemview.findViewById(R.id.time);
        timeview.setText(formatedtime);

        return listitemview;

    }

    private int magcolor(double getmMagnitude) {
        int magcolorid;
        int magcolorstage = (int) Math.floor(getmMagnitude);
        switch (magcolorstage){
            case 0:
            case 1:
                magcolorid = R.color.magnitude1;
                break;
            case 2:
                magcolorid = R.color.magnitude2;
                break;
            case 3:
                magcolorid = R.color.magnitude3;
                break;
            case 4:
                magcolorid = R.color.magnitude4;
                break;
            case 5:
                magcolorid = R.color.magnitude5;
                break;
            case 6:
                magcolorid = R.color.magnitude6;
                break;
            case 7:
                magcolorid = R.color.magnitude7;
                break;
            case 8:
                magcolorid = R.color.magnitude8;
                break;
            case 9:
                magcolorid = R.color.magnitude9;
                break;
            default:
                magcolorid = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(),magcolorid);
    }

    private String fortmatmag(double magn){
        DecimalFormat magform = new DecimalFormat("0.0");
        return magform.format(magn);
    }

    private  String formatdate(Date dateobject){
        SimpleDateFormat date  = new SimpleDateFormat("LLL DD, YYYY");
        return date.format(dateobject);
    }

    private String formattime(Date dateobject){
        SimpleDateFormat time = new SimpleDateFormat("h:mm a");
        return time.format(dateobject);
    }
}
