package com.lyw.app.ui.atys;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.lyw.app.R;
import com.lyw.app.ui.frags.MineFragment;
import com.lyw.app.ui.bean.Tab;
import com.lyw.app.ui.widget.FragmentTabHost;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyw on 2017/7/25.
 */

public class MainActivity extends AppCompatActivity {



    private LayoutInflater mInflater;

    private FragmentTabHost mTabhost;


    private List<Tab> mTabs = new ArrayList<>(5);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTab();

    }



    private void initTab() {


        Tab tab_mine1 = new Tab(MineFragment.class,R.string.tab1,R.drawable.selector_icon_mine);
        Tab tab_mine2 = new Tab(MineFragment.class,R.string.tab2,R.drawable.selector_icon_mine);
        Tab tab_mine3 = new Tab(MineFragment.class,R.string.tab3,R.drawable.selector_icon_mine);
        Tab tab_mine4 = new Tab(MineFragment.class,R.string.tab4,R.drawable.selector_icon_mine);
        Tab tab_mine5 = new Tab(MineFragment.class,R.string.mine,R.drawable.selector_icon_mine);

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

}
