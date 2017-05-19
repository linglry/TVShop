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

public class SetPswActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImgBack;
    private Button mBtSave;
    private EditText mEtOldPsw;
    private EditText mEtNewPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_set_psw);
        initView();
    }

    private void initView() {
        mImgBack = (ImageView) findViewById(R.id.img_back);
        mImgBack.setOnClickListener(this);
        mBtSave = (Button) findViewById(R.id.bt_save);

        mBtSave.setOnClickListener(this);
        mEtOldPsw = (EditText) findViewById(R.id.et_oldPsw);
        mEtNewPsw = (EditText) findViewById(R.id.et_newPsw);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_save:
                updatePassword();
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    private void updatePassword() {
        String oldPsw = mEtOldPsw.getText().toString();
        String newPsw = mEtNewPsw.getText().toString();
        if (TextUtils.isEmpty(oldPsw)) {
            Toast.makeText(this, "请输入旧密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(newPsw)) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return;
        }

        HttpUtils httpUtils = new HttpUtils();

        URLEncode urlEncode = new URLEncode(AppUrl.updatePasswprd);
        urlEncode.add("oldPassword", oldPsw);
        urlEncode.add("newPassword", newPsw);
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
                        Log.i("xx", result);

                        Gson gson = new Gson();
                        User info = gson.fromJson(result, User.class);
                        switch (info.getCode()) {
                            case UserConstants.LOGIN_SUCCESS:
                                Toast.makeText(SetPswActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
//                                saveAccount(uid, psw, info.getData().getToken());
//                                startActivity(new Intent(Login2Activity.this, ShopActivity.class));
                                finish();

                                break;
                            default:
                                Toast.makeText(SetPswActivity.this, info.getMsg(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });

    }

//    private void submit() {
//        // validate
//        String oldPsw = et_oldPsw.getText().toString().trim();
//        if (TextUtils.isEmpty(oldPsw)) {
//            Toast.makeText(this, "oldPsw不能为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String newPsw = et_newPsw.getText().toString().trim();
//        if (TextUtils.isEmpty(newPsw)) {
//            Toast.makeText(this, "newPsw不能为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // TODO validate success, do something
//
//
//    }
}
