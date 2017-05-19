package com.example.administrator.tvshop;

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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.administrator.tvshop.activity.Login2Activity;
import com.example.administrator.tvshop.activity.UserRegisterActivity;
import com.example.administrator.tvshop.bean.User;
import com.example.administrator.tvshop.utils.AppUrl;
import com.example.administrator.tvshop.utils.URLEncode;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImgBack;
    private TextView mTvUid;
    private EditText mEditUid;
    private ImageView mImgLoginClearUid;
    private ImageView mImgLoginClearPsw;
    private ToggleButton mTgbtnShowPsw;
    private TextView mTvPsw;
    private EditText mEditPsw;
    private Button mBtnLogin;
    private TextView mTvQuickSignUp;
    private TextView mTvFindBackPsw;
    private TextView mTvQuickShopLogin;
    private final static String ENCODE = "UTF-8";
    private String name,password;
    private String uid, psw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mImgBack = (ImageView) findViewById(R.id.img_back);
        mTvUid = (TextView) findViewById(R.id.tv_uid);
        mEditUid = (EditText) findViewById(R.id.edit_uid);
        mImgLoginClearUid = (ImageView) findViewById(R.id.img_login_clear_uid);
        mImgLoginClearPsw = (ImageView) findViewById(R.id.img_login_clear_psw);
        mTgbtnShowPsw = (ToggleButton) findViewById(R.id.tgbtn_show_psw);
        mTvPsw = (TextView) findViewById(R.id.tv_psw);
        mEditPsw = (EditText) findViewById(R.id.edit_psw);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mTvQuickSignUp = (TextView) findViewById(R.id.tv_quick_sign_up);
        mTvQuickShopLogin = (TextView) findViewById(R.id.tv_quick_shop_login);

        mTgbtnShowPsw.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mImgBack.setOnClickListener(this);

        mTvQuickShopLogin.setOnClickListener(this);
        mTvQuickSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        uid = mEditUid.getText().toString();
        psw = mEditPsw.getText().toString();
//        Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();
        switch (v.getId()) {
            case R.id.tgbtn_show_psw:

                break;
            case R.id.btn_login:
//                Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
                login(uid, psw);
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_quick_shop_login://商家登录
                startActivity(new Intent(this, Login2Activity.class));
//                finish();
                break;
            case R.id.tv_quick_sign_up://用户注册
                startActivity(new Intent(this, UserRegisterActivity.class));
                finish();
                break;
        }
    }

//    private void submit() {
//        // validate
//        String uid = edit_uid.getText().toString().trim();
//        if (TextUtils.isEmpty(uid)) {
//            Toast.makeText(this, "用户名/邮箱/手机号", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String psw = edit_psw.getText().toString().trim();
//        if (TextUtils.isEmpty(psw)) {
//            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // TODO validate success, do something
//
//
//    }

//    private void check(){
//        String uid = mEditUid.getText().toString();
//        String psw = mEditPsw.getText().toString();
//    }

    private void login(final String name, final String password) {
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        /***
         * 服务器处理部分代码
         */
        HttpUtils httpUtils = new HttpUtils();

        URLEncode urlEncode = new URLEncode(AppUrl.login);//解析网址
        urlEncode.add("name", uid);
        urlEncode.add("password", psw);

        httpUtils.send(HttpMethod.GET, urlEncode.out(),
                null, new RequestCallBack<String>() {
                    @Override
                    public void onFailure(HttpException e, String s) {
                        Log.i("xx", "onFailure: ");
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;//获取返回信息

                        Gson gson = new Gson();
                        User info = gson.fromJson(result, User.class);//Gson解析返回信息
                        switch (info.getCode()) {
                            case UserConstants.LOGIN_SUCCESS://成功
                                Toast.makeText(LoginActivity.this, "成功" + info.getData().getToken(), Toast.LENGTH_SHORT).show();
                                saveAccount(name, password, info.getData().getToken());
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                                break;
                            default:
                                Toast.makeText(LoginActivity.this, info.getMsg(), Toast.LENGTH_SHORT).show();
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

//        Intent intent = new Intent();
//        intent.putExtra("uid", un);
//        setResult(Constants.INTENT_KEY.LOGIN_RESULT_SUCCESS_CODE, intent);

    }
}
