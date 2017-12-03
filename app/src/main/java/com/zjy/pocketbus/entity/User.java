package com.zjy.pocketbus.entity;

import android.content.Context;

import com.zjy.pocketbus.utils.SpfsUtils;

import java.util.List;

/**
 * com.zjy.pocketbus
 * Created by 73958 on 2017/12/2.
 */

public class User {

    private Double userId;
    private String username;
    private String nickname;
    private List<String> staredBus;

    public User() {
    }

    public Double getUserId() {
        return userId;
    }

    public void setUserId(Double userId) {
        if (userId != null)
            this.userId = userId;
    }

    public String getNickname() {
        if(nickname == null){
            return username;
        } else {
            return nickname;
        }
    }

    public void setNickname(String nickname) {
        if (nickname != null)
            this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username != null)
            this.username = username;
    }

    public List<String> getStaredBus() {
        return staredBus;
    }

    public void setStaredBus(List<String> staredBus) {
        this.staredBus = staredBus;
    }

    public void star(Context context, String userId, boolean star) {
        if (star) {
            getStaredBus().add(userId);
            String str = SpfsUtils.readString(
                    context.getApplicationContext(), SpfsUtils.USER, "star", "");
            SpfsUtils.write(context, SpfsUtils.USER, "star", str + userId + "-");
        } else {
            getStaredBus().remove(userId);
            List<String> list = getStaredBus();
            StringBuilder builder = new StringBuilder("");
            for(String s : list){
                builder.append(s).append("-");
            }
            SpfsUtils.write(context, SpfsUtils.USER, "star", builder.toString());
        }
    }
}
