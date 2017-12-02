package com.zjy.pocketbus.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.zjy.pocketbus.R;
import com.zjy.pocketbus.utils.WindowUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements Inputtips.InputtipsListener {

    private EditText mSearch;
    private ListView minputlist;
    private String mStartLoc, mEndLoc;

    private boolean isSearchBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        WindowUtils.setStatusBarColor(this, R.color.colorPrimary, false);

        mStartLoc = getIntent().getStringExtra("start");
        mEndLoc = getIntent().getStringExtra("end");
        isSearchBus = getIntent().getBooleanExtra("search_bus_stop", false);

        minputlist = (ListView) findViewById(R.id.inputtips_list);
        mSearch = (EditText) findViewById(R.id.toolbar_search);
        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString().trim();
                InputtipsQuery inputQuery = new InputtipsQuery(newText, null);
                Inputtips inputTips = new Inputtips(SearchActivity.this, inputQuery);
                inputTips.setInputtipsListener(SearchActivity.this);
                inputTips.requestInputtipsAsyn();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public void onGetInputtips(final List<Tip> tipList, int code) {
        if (code == AMapException.CODE_AMAP_SUCCESS) {
            List<HashMap<String, String>> listString = new ArrayList<HashMap<String, String>>();
            for (int i = 0; i < tipList.size(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("name", tipList.get(i).getName());
                map.put("address", tipList.get(i).getDistrict());
                listString.add(map);
            }
            SimpleAdapter aAdapter = new SimpleAdapter(getApplicationContext(), listString, R.layout.item_tips,
                    new String[] {"name","address"}, new int[] {R.id.poi_field_id, R.id.poi_value_id});
            minputlist.setAdapter(aAdapter);
            aAdapter.notifyDataSetChanged();

            minputlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent data = new Intent();
                    data.putExtra("name", tipList.get(i).getName());
                    data.putExtra("address", tipList.get(i).getDistrict() + tipList.get(i).getAddress());
                    data.putExtra("pointX", tipList.get(i).getPoint().getLatitude());
                    data.putExtra("pointY", tipList.get(i).getPoint().getLongitude());
                    if(!isSearchBus){
                        setResult(RESULT_OK, data);
                        finish();
                    } else {

                    }
                }
            });

        } else {
            Log.e("tips_error", "errorCode:" + code);
        }
    }

    public void onCancel(View view){
        finish();
    }
}
