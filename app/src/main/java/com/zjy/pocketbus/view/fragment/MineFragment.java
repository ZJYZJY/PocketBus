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
import android.widget.TextView;

import com.zjy.pocketbus.R;
import com.zjy.pocketbus.event.LoginStateEvent;
import com.zjy.pocketbus.view.activity.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MineFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private LinearLayout mUnLogin_btn, mLogin_btn;
    private TextView mLoginName;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        mUnLogin_btn = (LinearLayout) view.findViewById(R.id.unlogin_btn);
        mLogin_btn = (LinearLayout) view.findViewById(R.id.login_btn);
        mLoginName = (TextView) view.findViewById(R.id.login_name);

        mUnLogin_btn.setOnClickListener(this);
        mLogin_btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.unlogin_btn:
                startActivity(new Intent(mContext, LoginActivity.class));
                break;
            case R.id.login_btn:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void onLoginStateEvent(LoginStateEvent event){
        if(event.isLogin()){
            mUnLogin_btn.setVisibility(View.GONE);
            mLogin_btn.setVisibility(View.VISIBLE);
            mLoginName.setText(event.getUser().getNickname());
        } else if(event.isLogout()){
            mUnLogin_btn.setVisibility(View.VISIBLE);
            mLogin_btn.setVisibility(View.GONE);
            mLoginName.setText(" ");
        }
    }
}
