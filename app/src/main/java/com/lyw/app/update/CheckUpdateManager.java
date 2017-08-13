package com.lyw.app.update;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lyw.app.GlobalApplication;
import com.lyw.app.ui.api.MyApi;
import com.lyw.app.ui.bean.Version;
import com.lyw.app.ui.bean.base.ResultBean;
import com.lyw.app.utils.AppOperator;
import com.lyw.app.utils.DialogHelper;
import com.lyw.app.utils.TDevice;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;


/**
 * Created by haibin
 * on 2016/10/19.
 */
public class CheckUpdateManager {

    private boolean mIsShowDialog;
    private ProgressDialog mWaitDialog;
    private Context mContext;
    private RequestPermissions mCaller;

    public CheckUpdateManager(Context context, boolean showWaitingDialog) {
        this.mContext = context;
        mIsShowDialog = showWaitingDialog;
        if (mIsShowDialog) {
            mWaitDialog = DialogHelper.getProgressDialog(mContext);
            mWaitDialog.setMessage("正在检查中...");
            mWaitDialog.setCancelable(false);
            mWaitDialog.setCanceledOnTouchOutside(false);
        }
    }


    public void checkUpdate() {
        if (mIsShowDialog) {
            mWaitDialog.show();
        }

        MyApi.checkUpdate(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (mIsShowDialog) {
                    DialogHelper.getMessageDialog(mContext, "网络异常，无法获取新版本信息").show();
                }
            }



            @Override
            public void onResponse(String response, int id) {
                GlobalApplication.showToast(response);
                try {
                    ResultBean<List<Version>> bean = AppOperator.createGson()
                            .fromJson(response, new TypeToken<ResultBean<List<Version>>>() {
                            }.getType());
                    if (bean != null && bean.isSuccess()) {
                        List<Version> versions = bean.getResult();
                        if (versions.size() > 0) {
                            final Version version = versions.get(0);
                            int curVersionCode = TDevice.getVersionCode(GlobalApplication
                                    .getInstance().getPackageName());
                            if (curVersionCode < Integer.parseInt(version.getCode())) {

                                Toast.makeText(GlobalApplication.getContext(),"进入更新界面",Toast.LENGTH_LONG).show();
                            //    UpdateActivity.show((Activity) mContext, version);
                             /*   AlertDialog.Builder dialog = DialogHelper.getConfirmDialog(mContext, version.getMessage(), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //mCaller.call(version);
                                        if (!TDevice.isWifiOpen()) {
                                           DialogHelper.getConfirmDialog(mContext, "当前非wifi环境，是否升级？", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    mCaller.call(version);
                                                }
                                            }).show();
                                        } else {
                                            mCaller.call(version);
                                        }
                                    }
                               });
                                dialog.setTitle("发现新版本");
                                dialog.show();*/
                            } else {
                                if (mIsShowDialog) {
                                    DialogHelper.getMessageDialog(mContext, "已经是新版本了").show();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        });

    }

    public void setCaller(RequestPermissions caller) {
        this.mCaller = caller;
    }

    public interface RequestPermissions {
        void call(Version version);
    }
}
