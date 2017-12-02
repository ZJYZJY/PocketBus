package com.zjy.pocketbus.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.zjy.pocketbus.R;
import com.zjy.pocketbus.entity.SimpleResponse;
import com.zjy.pocketbus.utils.HttpUtil;
import com.zjy.pocketbus.utils.ToastUtil;
import com.zjy.pocketbus.utils.WindowUtils;
import com.zjy.pocketbus.view.Dialog;

import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsername, mPassword;
    private String mUsernameStr, mPasswordStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        WindowUtils.setStatusBarColor(this, R.color.background_blank, true);

        mUsername = (EditText) findViewById(R.id.login_username);
        mPassword = (EditText) findViewById(R.id.login_password);
    }

    public void onLogin(View view) {
        mUsernameStr = mUsername.getText().toString().trim();
        mPasswordStr = mPassword.getText().toString().trim();
        if (TextUtils.isEmpty(mUsernameStr)) {
            ToastUtil.show(this, "用户名不能为空");
        }
        if (TextUtils.isEmpty(mPasswordStr)) {
            ToastUtil.show(this, "密码不能为空");
        }
        Dialog.showProgressDialog(this, "正在登录...");
        HttpUtil.login(mUsernameStr, mPasswordStr)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SimpleResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        ToastUtil.show(LoginActivity.this, e.getMessage());
                        Dialog.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(SimpleResponse simpleResponse) {
                        Dialog.dismissProgressDialog();
                        if(simpleResponse.isOk()){
                            ToastUtil.show(LoginActivity.this, "登录成功");
                            finish();
                        } else {
                            ToastUtil.show(LoginActivity.this, "登录失败");
                        }
                    }
                });
    }

    public void onBack(View view) {
        finish();
    }
}
