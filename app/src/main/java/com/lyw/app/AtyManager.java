package com.lyw.app;


import android.app.Activity;
import android.content.Context;

import com.lyw.app.utils.UIUtils;

import java.util.Stack;


/**
 * @author lyw
 *	单例模式，用于activity管理
 */
public class AtyManager {
    private static Stack<Activity> activityStack;
    private static AtyManager instance;
    private AtyManager(){}

    /**
     * @return 返回管理器单例对象
     */
    public static AtyManager getInstance(){
        if(instance == null){
            synchronized (AtyManager.class) {
                if(instance == null){
                    instance = new AtyManager();
                }

            }
        }
        return instance;
    }

    /**
     * @param activity
     * 添加activity到堆栈的方法
     */
    public void addActivity(Activity activity){
        if(activityStack == null){
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);

    }

    public Activity currentActivity(){
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前界面的activity；
     */
    public void finishActivity(){
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * @param activity 指定结束的activity
     * 指定activity销毁的方法
     */
    public void finishActivity(Activity activity){
        if(activity != null && !activity.isFinishing()){
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * @param //cls传入关闭的类名
     * 指定类名关闭activity；
     */
    public void finishActivity(Class<?> cls){
        for(Activity activity : activityStack){
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 清空堆栈内的所有activity
     */
    public void finishAllActivity(){
        for(int i = 0,size = activityStack.size();i<size;i++){
            if(null != activityStack.get(i)){
                finishActivity(activityStack.get(i));
                break;
            }
        }
        activityStack.clear();
    }

    /**
     * @param //cls传入指定
     * @return 返回指定类名的activity对象，没有返回null
     */
    public static Activity getActivity(Class<?> cls){
        if(activityStack != null){
            for(Activity activity : activityStack){
                if(activity.getClass().equals(cls)){
                    return activity;
                }
            }
        }
        return null;

    }

    /**
     * @param context 安全退出程序
     */
    public void ppExit(Context context){
        try {
            finishAllActivity();
            //杀应用进程
            android.os.Process.killProcess(UIUtils.getMainThreadId());
            System.exit(0);
        } catch (Exception e) {
            // TODO: handle exception
        }


    }

}
