package com.zjy.pocketbus.entity;

import java.util.List;

/**
 * com.zjy.pocketbus.entity
 * Created by 73958 on 2017/12/3.
 */

public class BusLine {

    private String busId;

    private String busName;

    private List<String> stations;

    private String nextStation;

    // 下一班车的距离
    private Double distance;

    // 下一班车预计到达时间
    private Double arriveTime;

    public BusLine(){

    }

    public boolean haveBusInfo(){
        return distance != null;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

    public String getNextStation() {
        return nextStation;
    }

    public void setNextStation(String nextStation) {
        this.nextStation = nextStation;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(double arriveTime) {
        this.arriveTime = arriveTime;
    }
}
