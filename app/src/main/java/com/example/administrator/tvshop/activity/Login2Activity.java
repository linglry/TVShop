package com.example.administrator.tvshop.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.tvshop.R;
import com.example.administrator.tvshop.UserConstants;
import com.example.administrator.tvshop.bean.User;
import com.example.administrator.tvshop.utils.AppUrl;
import com.example.administrator.tvshop.utils.URLEncode;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class Login2Activity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImgBack;
    private Button mBtnLogin;
    private EditText mEditUid;
    private EditText mEditPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login2);
        initView();
    }

    private void initView() {
        mImgBack = (ImageView) findViewById(R.id.img_back);
        mBtnLogin = (Button) findViewById(R.id.btn_login);

        mBtnLogin.setOnClickListener(this);
        mImgBack.setOnClickListener(this);
        mEditUid = (EditText) findViewById(R.id.edit_uid);
        mEditUid.setOnClickListener(this);
        mEditPsw = (EditText) findViewById(R.id.edit_psw);
        mEditPsw.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String uid = mEditUid.getText().toString();
        String psw = mEditPsw.getText().toString();
        switch (v.getId()) {
            case R.id.btn_login:
                shopLogin(uid, psw);

                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    private void shopLogin(final String uid, final String psw) {
//        String name = mEditUid.getText().toString();
//        String password = mEditUid.getText().toString();
        if (TextUtils.isEmpty(uid)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(psw)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        HttpUtils httpUtils = new HttpUtils();
//        RequestParams params = new RequestParams();
//        params.addBodyParameter("name", uid);
//        params.addBodyParameter("password", psw);
//        params.addHeader("token", "123");

        URLEncode urlEncode = new URLEncode(AppUrl.shopLogin);
        urlEncode.add("name", uid);
        urlEncode.add("password", psw);

        httpUtils.send(HttpRequest.HttpMethod.GET, urlEncode.out(),
                null, new RequestCallBack<String>() {
                    @Override
                    public void onFailure(HttpException e, String s) {
                        Log.i("xx", "onFailure: ");
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        Log.i("xx", "login: " + result);

                        Gson gson = new Gson();
                        User info = gson.fromJson(result, User.class);
                        switch (info.getCode()) {
                            case UserConstants.LOGIN_SUCCESS:
                                Toast.makeText(Login2Activity.this, "成功" + info.getData().getToken(), Toast.LENGTH_SHORT).show();
                                saveAccount(uid, psw, info.getData().getToken());
                                startActivity(new Intent(Login2Activity.this, ShopActivity.class));
                                finish();

                                break;
                            default:
                                Toast.makeText(Login2Activity.this, info.getMsg(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
    }

    public void saveAccount(String un, String psw, String token) {
        SharedPreferences sp = this.getSharedPreferences("info_account",
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", un);
        editor.putString("password", psw);
        editor.putString("token", token);
        editor.putBoolean("isLogined", true);
        editor.commit();

    }
}
