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
import com.zjy.pocketbus.view.activity.SearchActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class NearbyFragment extends Fragment implements View.OnClickListener {

    private TextView mLocation;
    private View mSearchBox;

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
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_search_box:
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;
        }
    }

    @Subscribe(sticky = true)
    public void onLoactionChanged(AMapLocation aMapLocation){
        mLocation.setText(aMapLocation.getCity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
