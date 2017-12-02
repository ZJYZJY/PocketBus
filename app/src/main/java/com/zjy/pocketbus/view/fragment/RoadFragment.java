package com.zjy.pocketbus.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.LatLonPoint;
import com.zjy.pocketbus.R;
import com.zjy.pocketbus.view.activity.BusResultActivity;
import com.zjy.pocketbus.view.activity.SearchActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class RoadFragment extends Fragment implements View.OnClickListener {


    private final int SEARCH_START_PLACE = 0;
    private final int SEARCH_END_PLACE = 1;

    private View mStart, mEnd;
    private View mSwapLoction, mSearchRoute;
    private TextView mStart_tv, mEnd_tv;
    private String mStartLoc = "", mEndLoc = "";

    private LatLonPoint mStartPoint, mEndPoint;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_road, container, false);
        mStart = view.findViewById(R.id.enter_start);
        mEnd = view.findViewById(R.id.enter_end);
        mStart_tv = (TextView) view.findViewById(R.id.start_name);
        mEnd_tv = (TextView) view.findViewById(R.id.end_name);
        mSwapLoction = view.findViewById(R.id.exchange_point);
        mSearchRoute = view.findViewById(R.id.search_bus_route);
        mStart.setOnClickListener(this);
        mEnd.setOnClickListener(this);
        mSwapLoction.setOnClickListener(this);
        mSearchRoute.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == SEARCH_START_PLACE){
                mStartLoc = data.getStringExtra("name");
                mStart_tv.setText(mStartLoc);
                mStartPoint = new LatLonPoint(
                        data.getDoubleExtra("pointX", 0),
                        data.getDoubleExtra("pointY", 0));
            } else if(requestCode == SEARCH_END_PLACE){
                mEndLoc = data.getStringExtra("name");
                mEnd_tv.setText(mEndLoc);
                mEndPoint = new LatLonPoint(
                        data.getDoubleExtra("pointX", 0),
                        data.getDoubleExtra("pointY", 0));
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), SearchActivity.class);
        intent.putExtra("start", mStartLoc);
        intent.putExtra("end", mEndLoc);
        switch (v.getId()){
            case R.id.enter_start:
                startActivityForResult(intent, SEARCH_START_PLACE);
                break;
            case R.id.enter_end:
                startActivityForResult(intent, SEARCH_END_PLACE);
                break;
            case R.id.exchange_point:
                if(!"".equals(mStartLoc) && !"".equals(mEndLoc)){
                    swapLocation();
                }
                break;
            case R.id.search_bus_route:
                if(mStartPoint == null || mEndPoint == null){
                    return;
                }
                Intent searchRoute = new Intent(getContext(), BusResultActivity.class);
                searchRoute.putExtra("startPoint", mStartPoint);
                searchRoute.putExtra("endPoint", mEndPoint);
                startActivity(searchRoute);
                break;
        }
    }

    public void swapLocation(){
        String tmp = mStartLoc;
        mStartLoc = mEndLoc;
        mEndLoc = tmp;
        mStart_tv.setText(mStartLoc);
        mEnd_tv.setText(mEndLoc);
    }

    @Subscribe(sticky = true)
    public void onLoactionChanged(AMapLocation aMapLocation){
        mStartLoc = aMapLocation.getPoiName();
        mStartPoint = new LatLonPoint(aMapLocation.getLongitude(), aMapLocation.getLatitude());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
