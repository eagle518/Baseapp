package com.lyw.app.ui.base.atys;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lyw.app.R;
import com.lyw.app.Setting;
import com.lyw.app.utils.MD5Tool;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Request;

public class AtyLogin extends Activity {

	public class MyStringCallback extends StringCallback
	{
		@Override
		public void onBefore(Request request, int id)
		{
			setTitle("loading...");
		}

		@Override
		public void onAfter(int id)
		{
			setTitle("Sample-okHttp");
		}

		@Override
		public void onError(Call call, Exception e, int id)
		{
			e.printStackTrace();
		}

		@Override
		public void onResponse(String response, int id)
		{
			try {
				JSONObject obj = new JSONObject(response);

				switch (obj.getBoolean(Setting.BACK_STATUS)) {
					case Setting.RESULT_STATUS_SUCCESS:
						if (successCallback!=null) {
							successCallback.onSuccess(obj.getString(Config.KEY_TOKEN));
						}
						break;
					default:
						if (failCallback!=null) {
							failCallback.onFail();
						}
						break;
				}

			} catch (JSONException e) {
				e.printStackTrace();

				if (failCallback!=null) {
					failCallback.onFail();
				}
			}

		}

		@Override
		public void inProgress(float progress, long total, int id)
		{

		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_login);
		
		etPhone = (EditText) findViewById(R.id.etPhoneNum);
		etCode = (EditText) findViewById(R.id.etCode);
		

		
		
		findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				if (TextUtils.isEmpty(etPhone.getText())) {
					Toast.makeText(AtyLogin.this, "账户不能为空", Toast.LENGTH_LONG).show();
					return;
				}

				if (TextUtils.isEmpty(etCode.getText())) {
					Toast.makeText(AtyLogin.this, "密码不能为空", Toast.LENGTH_LONG).show();
					return;
				}
				
				
				final ProgressDialog pd = ProgressDialog.show(AtyLogin.this, getResources().getString(R.string.connecting), getResources().getString(R.string.connecting_to_server));
				String url = Setting.API_SERVER_URL + "user!getUser";
				OkHttpUtils
						.post()
						.url(url)
						.addParams("username", "hyman")
						.addParams("password", "123")
						.build()
						.execute(new MyStringCallback());
			}
		});
	}
	
	private EditText etPhone = null,etCode;
}
