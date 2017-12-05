package com.zjy.pocketbus.interfaces;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * com.zjy.pocketbus.interfaces
 * Created by 73958 on 2017/12/4.
 */

public interface NearbyBusItem {

    int getLayout();

    boolean isClickable();

    View getView(Context context, View convertView, LayoutInflater inflater);
}
