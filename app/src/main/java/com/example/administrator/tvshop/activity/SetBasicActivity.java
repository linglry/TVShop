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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class SetBasicActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImgBack;
    private EditText mEtTel;
    private Button mBtSave;
    private EditText mEtNickname;
    private RadioButton mRbMan;
    private RadioButton mRbWoman;
    private RadioGroup mRadioGroup;
    int sex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_set_jiben);
        initView();
    }

    private void initView() {
        mImgBack = (ImageView) findViewById(R.id.img_back);
        mImgBack.setOnClickListener(this);
        mEtTel = (EditText) findViewById(R.id.et_tel);
        mEtNickname = (EditText) findViewById(R.id.et_nickname);

        mBtSave = (Button) findViewById(R.id.bt_save);
        mBtSave.setOnClickListener(this);
        mRbMan = (RadioButton) findViewById(R.id.rbMan);
        mRbMan.setOnClickListener(this);
        mRbWoman = (RadioButton) findViewById(R.id.rbWoman);
        mRbWoman.setOnClickListener(this);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radbtn = (RadioButton) findViewById(checkedId);
//                Toast.makeText(getApplicationContext(), "按钮组值发生改变,你选了" + radbtn.getText(), Toast.LENGTH_LONG).show();
                if (radbtn.getText().equals("女")) {
                    sex = 2;
                } else {
                    sex = 1;
                }

            }
        });
//        mRadioGroup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_save:
                setInfo();
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    private void setInfo() {
        String tel = mEtTel.getText().toString();
        String nickName = mEtNickname.getText().toString();
        if (TextUtils.isEmpty(tel)) {
            Toast.makeText(this, "请输入电话", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(nickName)) {
            Toast.makeText(this, "请输入昵称", Toast.LENGTH_SHORT).show();
            return;
        }

        HttpUtils httpUtils = new HttpUtils();

        URLEncode urlEncode = new URLEncode(AppUrl.updateAddress);
        urlEncode.add("tel", tel);
        urlEncode.add("nickName", nickName);
        urlEncode.add("sex", String.valueOf(sex));
        RequestParams params = new RequestParams();
        String token = new GetInfo().getToken(this);
        Log.i("xx", "setInfo: " + urlEncode.out());
//        Log.i("xx", "initAddress: " + System.currentTimeMillis());
        params.addHeader("token", token);
        httpUtils.configCurrentHttpCacheExpiry(0);
        httpUtils.send(HttpRequest.HttpMethod.GET, urlEncode.out(),
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
                                Toast.makeText(SetBasicActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
//                                mEtAddress.setText(info.getData().getAddress());
                                break;
                            default:
                                Toast.makeText(SetBasicActivity.this, info.getMsg(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
    }

//    private void submit() {
//        // validate
//        String nickname = et_nickname.getText().toString().trim();
//        if (TextUtils.isEmpty(nickname)) {
//            Toast.makeText(this, "nickname不能为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // TODO validate success, do something
//
//
//    }
}
