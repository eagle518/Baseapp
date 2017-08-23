package com.lyw.app.ui.bean;

/*导入要放入替换的同一个activity下可后退的fragment*/

import com.lyw.app.R;
import com.lyw.app.ui.frags.subfrags.SettingsFragment;

public enum SimpleBackPage {
        /*枚举*/

    SETTING(15, R.string.actionbar_title_setting, SettingsFragment.class);

   // ABOUT_OSC(17, R.string.actionbar_title_about_osc, AboutOSCFragment.class);



    private int title;
    private Class<?> clz;
    private int value;

    private SimpleBackPage(int value, int title, Class<?> clz) {
        this.value = value;
        this.title = title;
        this.clz = clz;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static SimpleBackPage getPageByValue(int val) {
        for (SimpleBackPage p : values()) {
            if (p.getValue() == val)
                return p;
        }
        return null;
    }


}
