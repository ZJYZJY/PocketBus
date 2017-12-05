package com.zjy.pocketbus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zjy.pocketbus.interfaces.NearbyBusItem;

import java.util.List;

/**
 * com.zjy.pocketbus.adapter
 * Created by 73958 on 2017/12/4.
 */

public class NearbyBusListAdapter extends BaseAdapter {

    private Context context;
    private List<NearbyBusItem> nearbyBusItems;
    private LayoutInflater mIflater;

    public NearbyBusListAdapter(Context context, List<NearbyBusItem> nearbyBusItems){
        this.context = context;
        this.nearbyBusItems = nearbyBusItems;
    }

    @Override
    public int getCount() {
        return nearbyBusItems.size();
    }

    @Override
    public Object getItem(int position) {
        return nearbyBusItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        return nearbyBusItems.get(position).isClickable();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return nearbyBusItems.get(position).getView(context, convertView, mIflater);
    }

    public void upateList(List<NearbyBusItem> nearbyBusItems) {
        this.nearbyBusItems.addAll(nearbyBusItems);
        this.notifyDataSetChanged();
    }

    public void clearList() {
        this.nearbyBusItems.clear();
    }
}
