package com.lyw.app.ui.frags;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lyw.app.R;
import com.lyw.app.ui.account.AccountHelper;
import com.lyw.app.ui.account.atys.LoginActivity;

import butterknife.ButterKnife;


/**
 * Created by Ivan on 15/9/22.
 */
public class SubtestFragment extends BaseFragment implements View.OnClickListener{

    private TextView login;
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
        login = view.findViewById(R.id.txt_username);
        setListener();
    }
    @Override
    public void initData() {

    }

    private void setListener(){
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.txt_username:
            //判断是否已经登录
                if (!AccountHelper.isLogin()) {
                    LoginActivity.show(getContext());
                    return;
                }
            default:
                break;


        }

    }


}
