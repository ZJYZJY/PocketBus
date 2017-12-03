package com.zjy.pocketbus.view.fragment;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.zjy.pocketbus.R;
import com.zjy.pocketbus.utils.SpfsUtils;
import com.zjy.pocketbus.utils.ToastUtil;
import com.zjy.pocketbus.view.activity.SearchActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static android.app.Activity.RESULT_OK;

public class NearbyFragment extends Fragment implements View.OnClickListener {

    private final int SEARCH_BUS_STOP = 2;

    private TextView mLocation;
    private View mSearchBox;

    private String city;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby, container, false);
        mLocation = (TextView) view.findViewById(R.id.toolbar_location);
        mSearchBox = view.findViewById(R.id.toolbar_search_box);
        mSearchBox.setOnClickListener(this);

        city = SpfsUtils.readString(getContext(), SpfsUtils.CACHE, "city", "定位中");
        mLocation.setText(city);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_search_box:
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("search_bus_stop", true);
                startActivityForResult(intent, SEARCH_BUS_STOP);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == SEARCH_BUS_STOP){

            }
        }
    }

    @Subscribe(sticky = true)
    public void onLoactionChanged(AMapLocation aMapLocation){
        String newCity = aMapLocation.getCity();
        if(!newCity.equals(city)){
            mLocation.setText(newCity);
            ToastUtil.show(getContext(), "已切换到" + newCity);
            SpfsUtils.write(getContext(), SpfsUtils.CACHE, "city", aMapLocation.getCity());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
