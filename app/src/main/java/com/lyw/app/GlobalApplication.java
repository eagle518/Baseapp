package com.lyw.app;

import android.content.Context;
import android.os.Handler;

import com.lyw.app.base.BaseApplication;
import com.lyw.app.cache.DataCleanManager;
import com.lyw.app.widget.MethodsCompat;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * @author lyw
 * 全局应用程序类
 * 用于保存和调用全局应用配置及访问网络数据
 * 全局application初始化
 *
 */
public class GlobalApplication extends BaseApplication {
    //全局单例
    private static GlobalApplication instance;
    //上下文对象
    private static Context context;
    private static Handler handler;
    private static  int mainThreadId;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        handler = new Handler();
        mainThreadId = android.os.Process.myTid();

        // 初始化
        init();
    }
    //全局单例
    public static GlobalApplication getInstance(){
        return instance;
    }
    //当前运行的全局上下文
    public static Context getContext(){
        return context;
    }
    //主线程handler
    public static Handler getHandler(){
        return handler;
    }
    //主线程id
    public static int getMainThreadId(){
        return mainThreadId;
    }
    //获取该全局对应的app_config目录下的config
    public Properties getProperties() {
        return AppConfig.getAppConfig(this).get();
    }
    //设置键值对
    public void setProperty(String key, String value) {
        AppConfig.getAppConfig(this).set(key, value);
    }
    //获取键对应值
    public String getProperty(String key) {
        return AppConfig.getAppConfig(this).get(key);
    }
    //移除对应键
    public void removeProperty(String... key) {
        AppConfig.getAppConfig(this).remove(key);
    }

    /**
     * 清除app缓存
     */
    public void clearAppCache() {
        DataCleanManager.cleanDatabases(this);
        // 清除数据缓存
        DataCleanManager.cleanInternalCache(this);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            DataCleanManager.cleanCustomCache(MethodsCompat
                    .getExternalCacheDir(this));
        }

        // 清除编辑器保存的临时内容
        Properties props = getProperties();
        for (Object key : props.keySet()) {
            String _key = key.toString();
            if (_key.startsWith("temp"))
                removeProperty(_key);
        }
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }
    //是否加载图片的标注方法
    /*public static void setLoadImage(boolean flag) {
        set(KEY_LOAD_IMAGE, flag);
    }*/


    //重新初始化操作
    public static void reInit() {
        ((GlobalApplication) GlobalApplication.getInstance()).init();
    }
    private void init() {
        // 初始化异常捕获类
       // AppCrashHandler.getInstance().init(this);
        // 初始化账户基础信息
       // AccountHelper.init(this);
        // 初始化网络请求
      //  OkHttpClient.init(this);
        //初始化其他相关，框架，sdk，数据库,百度地图....
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }




}

