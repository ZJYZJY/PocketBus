package com.zjy.pocketbus.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zjy.pocketbus.R;
import com.zjy.pocketbus.entity.BusStation;
import com.zjy.pocketbus.interfaces.NearbyBusItem;

/**
 * com.zjy.pocketbus.view
 * Created by 73958 on 2017/12/4.
 */

public class BusStationTitle implements NearbyBusItem {

    private BusStation busStation;

    public BusStationTitle(BusStation busStation){
        this.busStation = busStation;
    }

    @Override
    public int getLayout() {
        return R.layout.item_bus_station;
    }

    @Override
    public boolean isClickable() {
        return false;
    }

    @Override
    public View getView(Context context, View convertView, LayoutInflater inflater) {
        inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(getLayout(), null);

        TextView station_name = (TextView) convertView.findViewById(R.id.station_name);
        TextView station_distance = (TextView) convertView.findViewById(R.id.station_distance);

        station_name.setText(busStation.getStationName());
        station_distance.setText(String.valueOf(busStation.getDistance()) + "m");
        return convertView;
    }
}
