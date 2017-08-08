package com.lyw.app.ui.account;

import android.annotation.SuppressLint;
import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.lyw.app.ui.account.bean.User;
import com.lyw.app.utils.SharedPreferencesHelper;

import okhttp3.internal.http2.Header;


/**
 * 账户辅助类，
 * 用于更新用户信息和保存当前账户等操作
 */
public final class AccountHelper {
    private static final String TAG = AccountHelper.class.getSimpleName();
    private User user;
    private Application application;
    @SuppressLint("StaticFieldLeak")
    private static AccountHelper instances;

    private AccountHelper(Application application) {
        this.application = application;
    }

    public static void init(Application application) {
        if (instances == null)
            instances = new AccountHelper(application);
        else {
            // reload from source
            instances.user = SharedPreferencesHelper.loadFormSource(instances.application, User.class);
            Log.d(TAG, "init reload:" + instances.user);
        }
    }

    public static boolean isLogin() {
        return getUserId() > 0 && !TextUtils.isEmpty(getCookie());
    }

    public static String getCookie() {
        String cookie = getUser().getCookie();
        return cookie == null ? "" : cookie;
    }

    public static long getUserId() {
        return getUser().getId();
    }

    public synchronized static User getUser() {
        if (instances == null) {
            Log.e(TAG,"AccountHelper instances is null, you need call init() method.");
            return new User();
        }
        if (instances.user == null)
            instances.user = SharedPreferencesHelper.loadFormSource(instances.application, User.class);
        if (instances.user == null)
            instances.user = new User();
        return instances.user;
    }

    public static boolean updateUserCache(User user) {
        if (user == null)
            return false;
        // 保留Cookie信息
        if (TextUtils.isEmpty(user.getCookie()) && instances.user != user)
            user.setCookie(instances.user.getCookie());
        instances.user = user;
        return SharedPreferencesHelper.save(instances.application, user);
    }

    private static void clearUserCache() {
        instances.user = null;
        SharedPreferencesHelper.remove(instances.application, User.class);
    }

    public static boolean login(final User user, Header[] headers) {
        // 更新Cookie
       //登录逻辑保存获取相应信息
        //回馈成功失败状态值
        return false;
    }

    /**
     * 退出登陆操作需要传递一个View协助完成延迟检测操作
     *
     * @param view     View
     * @param runnable 当清理完成后回调方法
     */
    public static void logout(final View view, final Runnable runnable) {
        // 清除用户缓存
        clearUserCache();
        // 等待缓存清理完成
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.removeCallbacks(this);
                User user = SharedPreferencesHelper.load(instances.application, User.class);
                // 判断当前用户信息是否清理成功
                if (user == null || user.getId() <= 0) {
                    clearAndPostBroadcast(instances.application);
                    runnable.run();
                } else {
                    view.postDelayed(this, 200);
                }
            }
        }, 200);

    }

    /**
     * 当前用户信息清理完成后调用方法清理服务等信息
     *
     * @param application Application
     */
    private static void clearAndPostBroadcast(Application application) {
        // 清理网络相关

        // 清理对应缓存路径数据
       // CacheManager.deleteObject(application, 相应的缓存路径);

        // Logou  退出的广播
        //Intent intent = new Intent(Constants.INTENT_ACTION_LOGOUT);
        //application.sendBroadcast(intent);

    }
}