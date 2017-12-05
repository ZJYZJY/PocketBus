package com.zjy.pocketbus.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zjy.pocketbus.R;
import com.zjy.pocketbus.entity.BusLine;
import com.zjy.pocketbus.interfaces.NearbyBusItem;

/**
 * com.zjy.pocketbus.view
 * Created by 73958 on 2017/12/4.
 */

public class BusStationLine implements NearbyBusItem {

    private BusLine busLine;

    public BusStationLine(BusLine busLine){
        this.busLine = busLine;
    }

    @Override
    public int getLayout() {
        return R.layout.item_bus_line;
    }

    @Override
    public boolean isClickable() {
        return true;
    }

    @Override
    public View getView(Context context, View convertView, LayoutInflater inflater) {
        inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(getLayout(), null);

        TextView station_name = (TextView) convertView.findViewById(R.id.bus_line_name);
        TextView next_station = (TextView) convertView.findViewById(R.id.bus_next_station);
        TextView bus_distance = (TextView) convertView.findViewById(R.id.bus_line_distance);
        TextView arrive_time = (TextView) convertView.findViewById(R.id.bus_line_time);
        View has_info = convertView.findViewById(R.id.bus_line_info);
        View no_info = convertView.findViewById(R.id.no_bus_line_info);

        station_name.setText(busLine.getBusName());
        next_station.setText(busLine.getNextStation());
        if(busLine.haveBusInfo()){
            bus_distance.setText(String.valueOf(busLine.getDistance()));
            arrive_time.setText(String.valueOf(busLine.getArriveTime()));
            has_info.setVisibility(View.VISIBLE);
            no_info.setVisibility(View.GONE);
        } else {
            has_info.setVisibility(View.GONE);
            no_info.setVisibility(View.VISIBLE);
        }
        return convertView;
    }
}
