package com.zjy.pocketbus.helper;

import android.content.Context;
import android.content.Intent;

import com.zjy.pocketbus.FieldConstant;
import com.zjy.pocketbus.UserStatus;
import com.zjy.pocketbus.event.LoginStateEvent;
import com.zjy.pocketbus.utils.LogUtil;
import com.zjy.pocketbus.utils.SpfsUtils;
import com.zjy.pocketbus.utils.ToastUtil;
import com.zjy.pocketbus.view.activity.LoginActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import static com.zjy.pocketbus.UserStatus.getCurrentUser;

public class LoginHelper {

    public static void login(final Context context, final Map<String, Object> data) {
        SpfsUtils.write(context, SpfsUtils.USER, "loginFlag", true);
        LogUtil.d("login_info", data == null ? "null" : data.toString());
        writeUserPreference(context, data);
        UserStatus.setCurrentUser(data);
        // post login event
        EventBus.getDefault().postSticky(new LoginStateEvent(LoginStateEvent.LOGIN, getCurrentUser()));
    }

    public static void logout(final Context context){
        UserStatus.clear(context);
        // post logout event
        EventBus.getDefault().postSticky(new LoginStateEvent(LoginStateEvent.LOGOUT, null));
    }

    public static boolean ifRequestLogin(Context context, String message) {
        if(!UserStatus.isLogin(context)){
            if(message != null){
                ToastUtil.show(context, message);
            }
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    private static void writeUserPreference(Context context, Map<String, Object> data){
        SpfsUtils.write(context, SpfsUtils.USER, "userId", data.get(FieldConstant.USER_ID));
        SpfsUtils.write(context, SpfsUtils.USER, "nickname", data.get(FieldConstant.USER_NICKNAME));
        SpfsUtils.write(context, SpfsUtils.USER, "gender", data.get(FieldConstant.USER_GENDER));
        SpfsUtils.write(context, SpfsUtils.USER, "iconUrl", data.get(FieldConstant.USER_ICON_URL));
        SpfsUtils.write(context, SpfsUtils.USER, "username", data.get(FieldConstant.USER_NAME));
        SpfsUtils.write(context, SpfsUtils.USER, "password", data.get(FieldConstant.USER_PASSWORD));
        SpfsUtils.write(context, SpfsUtils.USER, "star", data.get(FieldConstant.USER_STAR));
    }

    public static Map<String, String> readUserPreference(Context context){
        Map<String, String> data = new HashMap<>();
        data.put(FieldConstant.USER_ID, SpfsUtils.readString(
                context.getApplicationContext(), SpfsUtils.USER, "userId", ""));
        data.put(FieldConstant.USER_NICKNAME, SpfsUtils.readString(
                context.getApplicationContext(), SpfsUtils.USER, "nickname", ""));
        data.put(FieldConstant.USER_GENDER, SpfsUtils.readString(
                context.getApplicationContext(), SpfsUtils.USER, "gender", ""));
        data.put(FieldConstant.USER_ICON_URL, SpfsUtils.readString(
                context.getApplicationContext(), SpfsUtils.USER, "iconUrl", ""));
        data.put(FieldConstant.USER_NAME, SpfsUtils.readString(
                context.getApplicationContext(), SpfsUtils.USER, "username", ""));
        data.put(FieldConstant.USER_PASSWORD, SpfsUtils.readString(
                context.getApplicationContext(), SpfsUtils.USER, "password", ""));
        data.put(FieldConstant.USER_STAR, SpfsUtils.readString(
                context.getApplicationContext(), SpfsUtils.USER, "star", ""));
        return data;
    }
}
