package com.lyw.app.ui.api;

import android.app.Application;
import android.util.Log;

import com.lyw.app.GlobalApplication;
import com.lyw.app.Setting;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class MyApi {


    public static final int CATALOG_ALL = 0;

    public static final String CATALOG_NEWS_DETAIL = "news";
   /*
    Map<String, String> params = new HashMap<>();
        params.put("username", "张鸿洋");
        params.put("password", "123");

    Map<String, String> headers = new HashMap<>();
        headers.put("APP-Key", "APP-Secret222");
        headers.put("APP-Secret", "APP-Secret111");


                .url(url)//
                .params(params)//
                .headers(headers)//
                .build()//
                .execute(new MyStringCallback());
        */

    /**
     * 初始化网络请求，包括Cookie的初始化
     *
     * @param context AppContext
     */
    public static void init(Application context) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //          .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    //根url通过build.gradle配置到setting的sp文件中，并获取得到
    public static String getAbsoluteApiUrl(String partUrl) {
        String url = partUrl;
        if (!partUrl.startsWith("http:") && !partUrl.startsWith("https:")) {
            url = Setting.getServerUrl(GlobalApplication.getContext())+partUrl;
        }
        Log.i("url","request:" + url);
        return url;
    }


    public static void checkUpdate(StringCallback callback) {

        Map<String, String> params = new HashMap<>();
        params.put("appId", "1");
        params.put("catalog", "1");
        params.put("all", "false");
        OkHttpUtils.get().url(getAbsoluteApiUrl("action/apiv2/product_version")).params(params).build().execute(callback);
       // OkHttpUtils.get().url("https://www.oschina.net/action/apiv2/product_version").params(params).build().execute(callback);

    }






}
