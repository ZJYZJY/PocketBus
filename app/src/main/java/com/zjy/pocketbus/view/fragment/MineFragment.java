package com.zjy.pocketbus.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zjy.pocketbus.R;
import com.zjy.pocketbus.view.activity.LoginActivity;

public class MineFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private LinearLayout mLogin_btn;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        mLogin_btn = (LinearLayout) view.findViewById(R.id.login_btn);

        mLogin_btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                startActivity(new Intent(mContext, LoginActivity.class));
                break;
        }
    }
}
