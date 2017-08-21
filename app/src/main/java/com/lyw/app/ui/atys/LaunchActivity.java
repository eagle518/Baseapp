package com.lyw.app.ui.atys;

import android.content.Intent;
import android.text.TextUtils;

import com.lyw.app.GlobalApplication;
import com.lyw.app.R;
import com.lyw.app.Setting;
import com.lyw.app.ui.account.AccountHelper;
import com.lyw.app.ui.account.bean.User;
import com.lyw.app.utils.AppOperator;


/**
 * @author lyw
 * 欢迎界面，版本检测
 *
 */
public class LaunchActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.app_start;
    }
    @Override
    protected void initData() {

        //配置文件生成并记录app当前进入时间点
        //  AppConfig.getAppConfig(LaunchActivity.this).set("system_time", "初始化");
        //GlobalApplication.getInstance().setProperty("system_time", "123456");

        super.initData();
        // 在这里我们检测是否是新版本安装，如果是则进行老版本数据迁移工作
        // 该工作可能消耗大量时间所以放在自线程中执行
        //线程池执行
        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                doMerge();
            }
        });
    }
    //版本检查

    private void doMerge() {


        // 判断是否是新版本
        if (Setting.checkIsNewVersion(this)) {
            // Cookie迁移,获得保存着的，赋值前先移除，再赋给获得的登录user
            String cookie = GlobalApplication.getInstance().getProperty("cookie");
            //判断是否为空cookie
            if (!TextUtils.isEmpty(cookie)) {
                GlobalApplication.getInstance().removeProperty("cookie");
                User user = AccountHelper.getUser();
                user.setCookie(cookie);
                AccountHelper.updateUserCache(user);
                GlobalApplication.reInit();
            }
        }



        // Delay...
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 完成后进行跳转操作
        redirectTo();
    }

    private void redirectTo() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
