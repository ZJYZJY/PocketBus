package com.zjy.pocketbus.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.zjy.pocketbus.helper.LocationHelper;
import com.zjy.pocketbus.R;
import com.zjy.pocketbus.adapter.BusResultListAdapter;
import com.zjy.pocketbus.utils.ToastUtil;
import com.zjy.pocketbus.utils.WindowUtils;
import com.zjy.pocketbus.view.Dialog;

public class BusResultActivity extends AppCompatActivity implements RouteSearch.OnRouteSearchListener {

    private LatLonPoint mStartPoint, mEndPoint;
    private RouteSearch mRouteSearch;
    private BusRouteResult mBusRouteResult;
    private ListView mBusResultList;

    private final int ROUTE_TYPE_BUS = 1;
    private final int ROUTE_TYPE_DRIVE = 2;
    private final int ROUTE_TYPE_WALK = 3;
    private final int ROUTE_TYPE_CROSSTOWN = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_result);
        WindowUtils.setStatusBarColor(this, R.color.colorPrimary, false);
        WindowUtils.setToolBarTitle(this, "搜索结果");

        mStartPoint = getIntent().getParcelableExtra("startPoint");
        mEndPoint = getIntent().getParcelableExtra("endPoint");

        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);

        mBusResultList = (ListView) findViewById(R.id.bus_result_list);
        searchRouteResult(ROUTE_TYPE_BUS, RouteSearch.BUS_DEFAULT);
    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode) {
        Dialog.showProgressDialog(this, "正在搜索...");
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        if (routeType == ROUTE_TYPE_BUS) {// 公交路径规划
            RouteSearch.BusRouteQuery query = new RouteSearch.BusRouteQuery(fromAndTo, mode,
                    LocationHelper.getInstance().getCity(), 0);// 第一个参数表示路径规划的起点和终点，第二个参数表示公交查询模式，第三个参数表示公交查询城市区号，第四个参数表示是否计算夜班车，0表示不计算
            mRouteSearch.calculateBusRouteAsyn(query);// 异步路径规划公交模式查询
        } else if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        } else if (routeType == ROUTE_TYPE_WALK) {// 步行路径规划
            RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, mode);
            mRouteSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
//        } else if (routeType == ROUTE_TYPE_CROSSTOWN) {
//            RouteSearch.FromAndTo fromAndTo_bus = new RouteSearch.FromAndTo(
//                    mStartPoint_bus, mEndPoint_bus);
//            RouteSearch.BusRouteQuery query = new RouteSearch.BusRouteQuery(fromAndTo_bus, mode,
//                    "呼和浩特市", 0);// 第一个参数表示路径规划的起点和终点，第二个参数表示公交查询模式，第三个参数表示公交查询城市区号，第四个参数表示是否计算夜班车，0表示不计算
////            query.setCityd("农安县");
//            mRouteSearch.calculateBusRouteAsyn(query);// 异步路径规划公交模式查询
        }
    }

    public void onBack(View view){
        finish();
    }

    @Override
    public void onBusRouteSearched(BusRouteResult result, int errorCode) {
        Dialog.dismissProgressDialog();
        if (errorCode == 1000) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mBusRouteResult = result;
                    BusResultListAdapter mBusResultListAdapter = new BusResultListAdapter(this, mBusRouteResult);
                    mBusResultList.setAdapter(mBusResultListAdapter);
                } else if (result != null && result.getPaths() == null) {
                    ToastUtil.show(this, R.string.no_result);
                }
            } else {
                ToastUtil.show(this, R.string.no_result);
            }
        } else {
            ToastUtil.show(this.getApplicationContext(), "错误码：" + errorCode);
        }
    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }
}
