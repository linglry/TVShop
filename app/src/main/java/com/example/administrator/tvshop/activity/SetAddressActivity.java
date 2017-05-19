package com.example.administrator.tvshop.activity;

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
import com.example.administrator.tvshop.utils.GetInfo;
import com.example.administrator.tvshop.utils.URLEncode;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class SetAddressActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImgBack;
    private Button mBtSave;
    private EditText mEtAddress;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_set_address);
        initView();
        initAddress();
    }


    private void initAddress() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        String token = new GetInfo().getToken(this);
//        Log.i("xx", "initAddress: " + System.currentTimeMillis());
        params.addHeader("token", token);
        httpUtils.configCurrentHttpCacheExpiry(0);
        httpUtils.send(HttpRequest.HttpMethod.GET, AppUrl.getAddress,
                params, new RequestCallBack<String>() {
                    @Override
                    public void onFailure(HttpException e, String s) {
                        Log.i("xx", "onFailure: ");
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        Log.i("xx", "setaddress: " + result);

                        Gson gson = new Gson();
                        User info = gson.fromJson(result, User.class);
                        switch (info.getCode()) {
                            case UserConstants.LOGIN_SUCCESS:
                                mEtAddress.setText(info.getData().getAddress());
                                break;
                            default:
                                Toast.makeText(SetAddressActivity.this, info.getMsg(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
    }

    private void initView() {
        mImgBack = (ImageView) findViewById(R.id.img_back);
        mImgBack.setOnClickListener(this);
        mBtSave = (Button) findViewById(R.id.bt_save);
        mBtSave.setOnClickListener(this);
        mEtAddress = (EditText) findViewById(R.id.et_address);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_save:
                save();
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    private void save() {
        address = mEtAddress.getText().toString();
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "请输入地址", Toast.LENGTH_SHORT).show();
            return;
        }

        HttpUtils httpUtils = new HttpUtils();

        URLEncode urlEncode = new URLEncode(AppUrl.updateAddress);
        urlEncode.add("address", address);

        RequestParams params = new RequestParams();
        String token = new GetInfo().getToken(this);
        params.addHeader("token", token);

        httpUtils.send(HttpRequest.HttpMethod.GET, urlEncode.out(),
                params, new RequestCallBack<String>() {
                    @Override
                    public void onFailure(HttpException e, String s) {
                        Log.i("xx", "onFailure: ");
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
//                        Log.i("xx", "login: " + result);
                        initAddress();

                        Gson gson = new Gson();
                        User info = gson.fromJson(result, User.class);
                        switch (info.getCode()) {
                            case UserConstants.LOGIN_SUCCESS:
                                Toast.makeText(SetAddressActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(SetAddressActivity.this, info.getMsg(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
    }

}
