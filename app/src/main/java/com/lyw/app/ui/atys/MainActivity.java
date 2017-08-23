package com.lyw.app.ui.atys;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.lyw.app.GlobalApplication;
import com.lyw.app.R;
import com.lyw.app.Setting;
import com.lyw.app.ui.bean.Version;
import com.lyw.app.ui.frags.SubtestFragment;
import com.lyw.app.ui.bean.Tab;
import com.lyw.app.ui.frags.UserInfoFragment;
import com.lyw.app.ui.widget.FragmentTabHost;
import com.lyw.app.update.CheckUpdateManager;
import com.lyw.app.update.DownloadService;
import com.lyw.app.utils.DialogHelper;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by lyw on 2017/7/25.
 */

public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, CheckUpdateManager.RequestPermissions  {

    private static final int RC_EXTERNAL_STORAGE = 0x04;//存储权限
    private Version mVersion;
    private long mBackPressedTime;

    private LayoutInflater mInflater;
    private FragmentTabHost mTabhost;


    private List<Tab> mTabs = new ArrayList<>(5);


  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTab();

    }*/

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWindow() {
        super.initWindow();
        initTab();

    }

    @Override
    protected void initWidget() {
        super.initWidget();

    }

    private void initTab() {


        Tab tab_mine1 = new Tab(SubtestFragment.class,R.string.tab1,R.drawable.selector_icon_mine);
        Tab tab_mine2 = new Tab(SubtestFragment.class,R.string.tab2,R.drawable.selector_icon_mine);
        Tab tab_mine3 = new Tab(SubtestFragment.class,R.string.tab3,R.drawable.selector_icon_mine);
        Tab tab_mine4 = new Tab(SubtestFragment.class,R.string.tab4,R.drawable.selector_icon_mine);
        Tab tab_mine5 = new Tab(UserInfoFragment.class,R.string.mine,R.drawable.selector_icon_mine);

        mTabs.add(tab_mine1);
        mTabs.add(tab_mine2);
        mTabs.add(tab_mine3);
        mTabs.add(tab_mine4);
        mTabs.add(tab_mine5);



        mInflater = LayoutInflater.from(this);
        mTabhost = (FragmentTabHost) this.findViewById(android.R.id.tabhost);
        mTabhost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);

        for (Tab tab : mTabs){

            TabHost.TabSpec tabSpec = mTabhost.newTabSpec(getString(tab.getTitle()));

            tabSpec.setIndicator(buildIndicator(tab));

            mTabhost.addTab(tabSpec,tab.getFragment(),null);

        }


        mTabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabhost.setCurrentTab(0);


    }





    private View buildIndicator(Tab tab){


        View view =mInflater.inflate(R.layout.tab_indicator,null);
        ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
        TextView text = (TextView) view.findViewById(R.id.txt_indicator);

        img.setBackgroundResource(tab.getIcon());
        text.setText(tab.getTitle());

        return  view;
    }

    @Override
    protected void initData() {
        super.initData();
        checkUpdate();
    }
    private void checkUpdate() {
        if (!GlobalApplication.get(Setting.KEY_CHECK_UPDATE, true)) {
            return;
        }
        CheckUpdateManager manager = new CheckUpdateManager(this, false);
        manager.setCaller(this);
        manager.checkUpdate();
    }

    @Override
    public void call(Version version) {
        this.mVersion = version;
        requestExternalStorage();
    }

    @AfterPermissionGranted(RC_EXTERNAL_STORAGE)
    public void requestExternalStorage() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            DownloadService.startService(this, mVersion.getDownloadUrl());
        } else {
            EasyPermissions.requestPermissions(this, "", RC_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        for (String perm : perms) {
            if (perm.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                DialogHelper.getConfirmDialog(this, "温馨提示", "需要开启开源中国对您手机的存储权限才能下载安装，是否现在开启", "去开启", "取消", true, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
                    }
                }, null).show();

            }
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }



    @Override
    public void onBackPressed() {
        boolean isDoubleClick = GlobalApplication.get(Setting.KEY_DOUBLE_CLICK_EXIT, true);
        if (isDoubleClick) {
            long curTime = SystemClock.uptimeMillis();
            if ((curTime - mBackPressedTime) < (3 * 1000)) {
                finish();
            } else {
                mBackPressedTime = curTime;
                Toast.makeText(this, R.string.tip_double_click_exit, Toast.LENGTH_LONG).show();
            }
        } else {
            finish();
        }
    }


}
