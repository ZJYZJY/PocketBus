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

import com.zjy.pocketbus.FieldConstant;
import com.zjy.pocketbus.R;
import com.zjy.pocketbus.UserStatus;
import com.zjy.pocketbus.entity.SimpleResponse;
import com.zjy.pocketbus.event.LoginStateEvent;
import com.zjy.pocketbus.helper.LoginHelper;
import com.zjy.pocketbus.utils.HttpUtil;
import com.zjy.pocketbus.utils.ToastUtil;
import com.zjy.pocketbus.view.activity.LoginActivity;
import com.zjy.pocketbus.view.activity.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(UserStatus.isLogin(getContext())){
            Map<String, String> data = LoginHelper.readUserPreference(getContext());
            HttpUtil.login(data.get(FieldConstant.USER_NAME), data.get(FieldConstant.USER_PASSWORD))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<SimpleResponse>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            ToastUtil.show(getContext(), e.getMessage());
                        }

                        @Override
                        public void onNext(SimpleResponse simpleResponse) {
                            if(simpleResponse.isOk()){
                                Map<String, Object> data = simpleResponse.getData();
                                LoginHelper.login(getContext(), data);
                                ToastUtil.show(getContext(), "登录成功");
                            } else {
                                ToastUtil.show(getContext(), "登录失败");
                            }
                        }
                    });
        }
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
