package com.example.administrator.tvshop.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.tvshop.LoginActivity;
import com.example.administrator.tvshop.R;

public class SetActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mLayoutJibenSet;
    private LinearLayout mLayoutNameSet;
    private LinearLayout mLayoutPswSet;
    private LinearLayout mLayoutAddressSet;
    private ImageView mImgBack;
    private Button mLoginOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_set);
        initView();
    }

    private void initView() {
        mLayoutJibenSet = (LinearLayout) findViewById(R.id.layout_jiben_set);
        mLayoutJibenSet.setOnClickListener(this);
        mLayoutPswSet = (LinearLayout) findViewById(R.id.layout_psw_set);
        mLayoutPswSet.setOnClickListener(this);
        mLayoutAddressSet = (LinearLayout) findViewById(R.id.layout_address_set);
        mLayoutAddressSet.setOnClickListener(this);
        mImgBack = (ImageView) findViewById(R.id.img_back);
        mImgBack.setOnClickListener(this);
        mLoginOut = (Button) findViewById(R.id.login_out);
        mLoginOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_jiben_set:
                startActivity(new Intent(this, SetBasicActivity.class));
                break;
            case R.id.layout_psw_set:
                startActivity(new Intent(this, SetPswActivity.class));
                break;
            case R.id.layout_address_set:
                startActivity(new Intent(this, SetAddressActivity.class));
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.login_out:
                startActivity(new Intent(this, LoginActivity.class));
                SharedPreferences sp = this.getSharedPreferences("info_account",
                        MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("username", "");
                editor.putString("password", "");
                editor.putString("token", "");
                editor.putBoolean("isLogined", false);
                editor.commit();
                finish();
                break;
        }
    }
}
