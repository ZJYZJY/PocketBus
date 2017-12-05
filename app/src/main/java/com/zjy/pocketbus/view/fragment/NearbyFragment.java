package com.zjy.pocketbus.view.fragment;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.zjy.pocketbus.R;
import com.zjy.pocketbus.adapter.NearbyBusListAdapter;
import com.zjy.pocketbus.entity.BusLine;
import com.zjy.pocketbus.entity.BusStation;
import com.zjy.pocketbus.helper.LocationHelper;
import com.zjy.pocketbus.interfaces.NearbyBusItem;
import com.zjy.pocketbus.utils.LogUtil;
import com.zjy.pocketbus.utils.SpfsUtils;
import com.zjy.pocketbus.utils.ToastUtil;
import com.zjy.pocketbus.view.BusStationLine;
import com.zjy.pocketbus.view.BusStationTitle;
import com.zjy.pocketbus.view.activity.SearchActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class NearbyFragment extends Fragment implements View.OnClickListener, PoiSearch.OnPoiSearchListener {

    private final int SEARCH_BUS_STOP = 2;

    private TextView mLocation;
    private View mSearchBox;
    private ListView mList;

    private List<BusStation> busStations;
    private List<NearbyBusItem> nearbyBusItems;
    private NearbyBusListAdapter mAdapter;
    private String city;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        busStations = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby, container, false);
        mLocation = (TextView) view.findViewById(R.id.toolbar_location);
        mSearchBox = view.findViewById(R.id.toolbar_search_box);
        mList = (ListView) view.findViewById(R.id.nearby_bus_list);
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

    public void searchBusStop(double latitude, double longitude) {
        PoiSearch.Query query = new PoiSearch.Query("", "公交车站", LocationHelper.getInstance().getCity());
        query.setPageSize(4);
        query.setPageNum(0);
        PoiSearch poiSearch = new PoiSearch(getContext(), query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 5000, true));
        poiSearch.searchPOIAsyn();
    }

    @Subscribe(sticky = true)
    public void onLoactionChanged(AMapLocation aMapLocation){
        // 开始搜索附近公交站
        searchBusStop(aMapLocation.getLatitude(), aMapLocation.getLongitude());
        String newCity = aMapLocation.getCity();
        if(!newCity.equals(city)){
            mLocation.setText(newCity);
            ToastUtil.show(getContext(), "已切换到" + newCity);
            SpfsUtils.write(getContext(), SpfsUtils.CACHE, "city", aMapLocation.getCity());
        }
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i == 1000) {
            if (poiResult != null && poiResult.getQuery() != null) {
                ArrayList<PoiItem> poiItems = poiResult.getPois();
                if (poiItems != null) {
                    for(PoiItem item : poiItems){
                        BusStation station = new BusStation();
                        List<BusLine> busLines = new ArrayList<>();
                        station.setStationName(item.getTitle());
                        station.setDistance(item.getDistance());
                        String[] lines = item.getSnippet().split(";");
                        for(int k = 0; k < lines.length; k++){
                            BusLine line = new BusLine();
                            line.setBusName(lines[k]);
                            busLines.add(line);
                        }
                        station.setBusLines(busLines);
                        busStations.add(station);
                    }
                    setupBusLinesData();
                }
            } else {
                LogUtil.d("无结果");
            }
        } else if (i == 27) {
            LogUtil.e("error_network");
        } else if (i == 32) {
            LogUtil.e("error_key");
        } else {
            LogUtil.e("error_other：" + i);
        }
    }

    public void setupBusLinesData(){
        nearbyBusItems = new ArrayList<>();
        for(BusStation busStation : busStations){
            BusStationTitle title = new BusStationTitle(busStation);
            nearbyBusItems.add(title);
            for(BusLine busLine : busStation.getBusLines()){
                BusStationLine busStationLine = new BusStationLine(busLine);
                nearbyBusItems.add(busStationLine);
            }
        }
        mAdapter = new NearbyBusListAdapter(getContext(), nearbyBusItems);
        mList.setAdapter(mAdapter);
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
        LogUtil.d("onPoiItemSearched");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
