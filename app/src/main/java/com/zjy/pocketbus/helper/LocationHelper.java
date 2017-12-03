package com.zjy.pocketbus.helper;

import com.amap.api.location.AMapLocation;

/**
 * com.zjy.pocketbus
 * Created by 73958 on 2017/11/18.
 */

public final class LocationHelper {

    private static LocationHelper sInstance;
    private AMapLocation aMapLocation;

    public static LocationHelper getInstance(AMapLocation aMapLocation){
        if(sInstance == null){
            sInstance = new LocationHelper(aMapLocation);
        }
        return sInstance;
    }

    public static LocationHelper getInstance(){
        if(sInstance == null){
            sInstance = new LocationHelper();
        }
        return sInstance;
    }

    public LocationHelper(){}

    public LocationHelper(AMapLocation aMapLocation){
        this.aMapLocation = aMapLocation;
    }

    public double getLongitude(){
        // 获取经度
        return aMapLocation.getLongitude();
    }

    public double getLatitude(){
        // 获取维度
        return aMapLocation.getLatitude();
    }

    public String getAddress(){
        return aMapLocation.getAddress();
    }

    public String getCountry(){
        return aMapLocation.getCountry();
    }

    public String getCity(){
        return aMapLocation.getCity();
    }

    public String getDistrict(){
        // 获取城区信息
        return aMapLocation.getDistrict();
    }

    public String getStreet(){
        return aMapLocation.getStreet();
    }

    public String getAoiName(){
        return aMapLocation.getAoiName();
    }

    public String getPoiName(){
        return aMapLocation.getPoiName();
    }
}
