package com.zjy.pocketbus.entity;

import java.util.List;

/**
 * com.zjy.pocketbus.entity
 * Created by 73958 on 2017/12/3.
 */

public class BusStation {

    private String stationName;

    private List<BusLine> busLines;

    // 站点距离
    private int distance;

    public BusStation(){

    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public List<BusLine> getBusLines() {
        return busLines;
    }

    public void setBusLines(List<BusLine> busLines) {
        this.busLines = busLines;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
