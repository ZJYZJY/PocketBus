package com.zjy.pocketbus.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zjy.pocketbus.FieldConstant;
import com.zjy.pocketbus.entity.SimpleResponse;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * com.zjy.pocketbus.utils
 * Created by 73958 on 2017/11/30.
 */

public class HttpUtil {

    private static final String APP_SCHEME = "memo";
    private static final String HTTP_SCHEME = "http";
    private static final String HOST = "192.168.31.110:8080";
    private static final String API_PATH = "/api/";

    public static final String PATH = HTTP_SCHEME + "://" + HOST + API_PATH;

    private static Retrofit instance;
    private static OkHttpClient okHttpClient;
//    private static ClearableCookieJar cookieJar;

    public static synchronized void create(Context context) {
        if (instance == null) {
//            cookieJar = new PersistentCookieJar(
//                    new SetCookieCache(), new SharedPrefsCookiePersistor(context));
            okHttpClient = new OkHttpClient.Builder()
//                    .cookieJar(cookieJar)
                    .build();
            Gson gson = new GsonBuilder()
//                    .setLenient()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            instance = new Retrofit.Builder()
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(PATH)
                    .build();
        }
    }

    private static synchronized PocketBusService create() {
        return instance.create(PocketBusService.class);
    }

//    private static List<Cookie> getCookies(){
//        HttpUrl httpUrl = new HttpUrl.Builder().scheme(RouterHelper.scheme()).host(RouterHelper.host()).build();
//        return okHttpClient.cookieJar().loadForRequest(httpUrl);
//    }
//
//    public static String cookieHeader() {
//        List<Cookie> cookies = getCookies();
//        StringBuilder cookieHeader = new StringBuilder();
//        for (int i = 0, size = cookies.size(); i < size; i++) {
//            if (i > 0) {
//                cookieHeader.append("; ");
//            }
//            Cookie cookie = cookies.get(i);
//            cookieHeader.append(cookie.name()).append('=').append(cookie.value());
//        }
//        return cookieHeader.toString();
//    }
//
//    public static void clearCookies() {
//        cookieJar.clear();
//    }

    public interface PocketBusService {

        /**
         * user login.
         *
         * @param user user info
         */
        @POST(APIPath.LOGIN)
        Observable<SimpleResponse> login(@Body RequestBody user);

        /**
         * user register.
         *
         * @param phone user info
         */
        @POST(APIPath.REGISTER)
        Observable<SimpleResponse> register(@Body RequestBody phone);

        /**
         * cookie test.
         */
        @POST("article_api/ceshi")
        Observable<SimpleResponse> test();
    }

    class APIPath {
        static final String LOGIN = "login";
        static final String REGISTER = "register";
    }

    public static Observable<SimpleResponse> login(String username, String password) {
        JSONObject json = new JSONObject();
        try {
            json.put(FieldConstant.USER_NAME, username);
            json.put("password", StringUtil.getMD5(password));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody request = RequestBody
                .create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
        return create().login(request);
    }

    public static Observable<SimpleResponse> register(String username, String password) {
        JSONObject json = new JSONObject();
        try {
            json.put(FieldConstant.USER_NAME, username);
            json.put("password", StringUtil.getMD5(password));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody request = RequestBody
                .create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
        return create().register(request);
    }
}
