package com.zjy.pocketbus;

import android.content.Context;

import com.zjy.pocketbus.entity.User;
import com.zjy.pocketbus.utils.SpfsUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * com.donutcn.memo.utils
 * Created by 73958 on 2017/7/28.
 */

public class UserStatus {

    public static int PHONE_LOGIN = 1000;
    public static int WECHAT_LOGIN = 2000;

    private static User USER = new User();

    private static int loginType;

    public static void setCurrentUser(Map<String, Object> data) {
        if (USER == null)
            USER = new User();
        USER.setUserId((Double) data.get(FieldConstant.USER_ID));
        USER.setUsername((String) data.get(FieldConstant.USER_NAME));
        USER.setNickname((String) data.get(FieldConstant.USER_NICKNAME));
        String str = (String) data.get(FieldConstant.USER_STAR);
        if(str != null && !str.equals("")) {
            String[] followed = str.split("-");
            List<String> list = new ArrayList<>();
            Collections.addAll(list, followed);
            USER.setStaredBus(list);
        } else {
            USER.setStaredBus(new ArrayList<String>());
        }
    }

    public static User getCurrentUser() {
        return USER;
    }

    public static boolean isLogin(Context context) {
        return SpfsUtils.readBoolean(context, SpfsUtils.USER, "loginFlag", false);
    }

    public static void clear(Context context){
        USER = null;
        // remove login flag
        SpfsUtils.clear(context, SpfsUtils.USER);
    }
}
