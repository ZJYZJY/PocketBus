package com.zjy.pocketbus.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zjy.pocketbus.R;

public class RoadFragment extends Fragment implements View.OnClickListener {

    private View mStart, mEnd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_road, container, false);
        mStart = view.findViewById(R.id.enter_start);
        mEnd = view.findViewById(R.id.enter_end);
        mStart.setOnClickListener(this);
        mEnd.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.enter_start:
                break;
            case R.id.enter_end:
                break;
        }
    }
}
