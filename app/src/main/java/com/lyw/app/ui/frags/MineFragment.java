package com.lyw.app.ui.frags;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyw.app.R;
import com.lyw.app.ui.atys.MainActivity;

import butterknife.ButterKnife;


/**
 * Created by Ivan on 15/9/22.
 */
public class MineFragment extends BaseFragment{

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container,
                false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void initView(View view) {


    }
    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            /*case R.id.rl_clean_cache:

            default:
                break;*/
        }

    }


}
