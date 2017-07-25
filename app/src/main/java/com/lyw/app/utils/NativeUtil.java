package com.lyw.app.utils;

/**
 * Created by lyw on 2017/7/23.
 */

public final class NativeUtil {
    /***
     * 当前Common库加载native的方法
     */
    public static void doLoadNative() {
        try {
           // System.loadLibrary("XXXXXXXX1");
        } catch (UnsatisfiedLinkError error) {
           // System.loadLibrary("XXXXXXXXXX2");
        }
    }
}