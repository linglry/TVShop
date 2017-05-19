package com.example.administrator.tvshop.activity;

import android.content.Intent;
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

import com.example.administrator.tvshop.Constants;
import com.example.administrator.tvshop.LoginActivity;
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

public class UserRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImgBack;
    private EditText mEtRegName;
    private EditText mEtRegPsw;
    private Button mBtReg;
    private String name, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_user_register);
        initView();
    }

    private void initView() {
        mImgBack = (ImageView) findViewById(R.id.img_back);
        mEtRegName = (EditText) findViewById(R.id.et_reg_name);
        mEtRegPsw = (EditText) findViewById(R.id.et_reg_psw);
        mBtReg = (Button) findViewById(R.id.bt_reg);

        mBtReg.setOnClickListener(this);
        mImgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_reg:
                userReg();
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    private void userReg() {
        name = mEtRegName.getText().toString();
        password = mEtRegPsw.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        HttpUtils httpUtils = new HttpUtils();

        URLEncode urlEncode = new URLEncode(AppUrl.reg);
        urlEncode.add("name", name);
        urlEncode.add("password", password);
        Log.i("xx", "login: " + urlEncode.out()) ;

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
                                Toast.makeText(UserRegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UserRegisterActivity.this, LoginActivity.class);
                                startActivityForResult(intent, Constants.INTENT_KEY.LOGIN_REQUEST_CODE);
                                finish();
                            default:
                                Toast.makeText(UserRegisterActivity.this, info.getMsg(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
    }

}
