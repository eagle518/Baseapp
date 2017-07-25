package com.lyw.app;

import android.content.Intent;

import com.lyw.app.ui.MainActivity;
import com.lyw.app.ui.base.atys.AtyLogin;
import com.lyw.app.ui.base.atys.BaseActivity;


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
        super.initData();
        // 在这里我们检测是否是新版本安装，如果是则进行老版本数据迁移工作
        // 该工作可能消耗大量时间所以放在自线程中执行
        /*AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                doMerge();
            }
        });*/
        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        doMerge();
    }
    private void doMerge() {
        // 判断是否是新版本
       /* if (Setting.checkIsNewVersion(this)) {
            // Cookie迁移
            String cookie = GlobalApplication.getInstance().getProperty("cookie");
            if (!TextUtils.isEmpty(cookie)) {
                GlobalApplication.getInstance().removeProperty("cookie");
                User user = AccountHelper.getUser();
                user.setCookie(cookie);
                AccountHelper.updateUserCache(user);
                GlobalApplication.reInit();
            }
        }*/

        // 栏目相关数据合并操作
       // DynamicTabFragment.initTabPickerManager();

        // Delay...
       /* try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        String token = Setting.getCachedToken(this);
        String phone_num = Setting.getCachedUID(this);

        if (token!=null&&phone_num!=null) {
            Intent i =new Intent(this, MainActivity.class);
            i.putExtra(Setting.KEY_TOKEN, token);
            i.putExtra(Setting.KEY_UID, phone_num);
            startActivity(i);
        }else{
            // 完成后进行跳转操作
            redirectTo();
        }

        finish();


    }

    private void redirectTo() {
        Intent intent = new Intent(this, AtyLogin.class);
        startActivity(intent);
        finish();
    }
}
