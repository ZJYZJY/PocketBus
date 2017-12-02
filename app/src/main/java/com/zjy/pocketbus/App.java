package com.zjy.pocketbus;

import android.app.Application;

import com.zjy.pocketbus.utils.HttpUtil;

/**
 * com.zjy.pocketbus
 * Created by 73958 on 2017/12/2.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HttpUtil.create(getApplicationContext());
    }
}
