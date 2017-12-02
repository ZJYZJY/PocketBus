package com.zjy.pocketbus.entity;

import com.google.gson.annotations.Expose;
import com.zjy.pocketbus.base.Response;
import com.zjy.pocketbus.interfaces.Jsonify;

import org.json.JSONArray;

import java.util.LinkedHashMap;
import java.util.List;

public class ArrayResponse<E extends Jsonify> extends Response<List<E>> {

    @Expose
    private LinkedHashMap info;

    @Expose
    private List<E> data;

    @Override
    public List<E> getData() {
        return data;
    }

    public LinkedHashMap getInfo() {
        return info;
    }

    public int size(){
        return data == null ? 0 : data.size();
    }

    /**
     * used for user's content list cache.
     * @return string array of user content list.
     */
    @Override
    public String toString() {
        JSONArray array = new JSONArray();
        if (data != null) {
            for (E elem : data) {
                array.put(elem.toJson());
            }
        }
        return array.toString();
    }
}
