package com.zjy.pocketbus.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.zjy.pocketbus.R;
import com.zjy.pocketbus.entity.SimpleResponse;
import com.zjy.pocketbus.helper.LoginHelper;
import com.zjy.pocketbus.utils.HttpUtil;
import com.zjy.pocketbus.utils.ToastUtil;
import com.zjy.pocketbus.utils.WindowUtils;
import com.zjy.pocketbus.view.Dialog;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {

    private EditText mUsername, mPassword;
    private String mUsernameStr, mPasswordStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        WindowUtils.setStatusBarColor(this, R.color.background_blank, true);
        WindowUtils.toggleKeyboard(this, true);

        mUsername = (EditText) findViewById(R.id.register_username);
        mPassword = (EditText) findViewById(R.id.register_password);
    }

    public void onRegister(View view) {
        mUsernameStr = mUsername.getText().toString().trim();
        mPasswordStr = mPassword.getText().toString().trim();
        if (TextUtils.isEmpty(mUsernameStr)) {
            ToastUtil.show(this, "用户名不能为空");
        }
        if (TextUtils.isEmpty(mPasswordStr)) {
            ToastUtil.show(this, "密码不能为空");
        }
        WindowUtils.toggleKeyboard(this, false);
        Dialog.showProgressDialog(this, "正在注册...");
        HttpUtil.register(mUsernameStr, mPasswordStr)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<SimpleResponse, Observable<SimpleResponse>>() {
                    @Override
                    public Observable<SimpleResponse> call(SimpleResponse simpleResponse) {
                        if(simpleResponse.isOk()){
                            Dialog.setMessage("正在登录...");
                            return HttpUtil.login(mUsernameStr, mPasswordStr);
                        } else {
                            ToastUtil.show(RegisterActivity.this, "注册失败");
                            Dialog.dismissProgressDialog();
                            return null;
                        }
                    }
                })
                .subscribe(new Subscriber<SimpleResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        ToastUtil.show(RegisterActivity.this, e.getMessage());
                        Dialog.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(SimpleResponse simpleResponse) {
                        Dialog.dismissProgressDialog();
                        if(simpleResponse.isOk()){
                            LoginHelper.login(getApplicationContext(), simpleResponse.getData());
                            ToastUtil.show(RegisterActivity.this, "登录成功");
                            finish();
                        } else {
                            ToastUtil.show(RegisterActivity.this, "登录失败");
                        }
                    }
                });
    }

    public void openLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void onBack(View view) {
        finish();
    }
}
