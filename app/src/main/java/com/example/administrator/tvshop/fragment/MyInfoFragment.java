package com.example.administrator.tvshop.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.tvshop.LoginActivity;
import com.example.administrator.tvshop.R;
import com.example.administrator.tvshop.activity.FavActivity;
import com.example.administrator.tvshop.activity.OrderActivity;
import com.example.administrator.tvshop.activity.SetActivity;

/**
 * Created by Administrator on 2017.4.5.
 */

public class MyInfoFragment extends Fragment implements View.OnClickListener {

    private View layout;
    private Button mPersonalLoginButton;
    private LinearLayout mLayoutMineOrder;
    private LinearLayout mLayoutMineFav;
    private LinearLayout mLayoutMineSet;

    private String uid;
    private RelativeLayout mLayoutNotLogined;
    private TextView mTvUid;
    private RelativeLayout mLayoutLogined;
    private boolean isLogined;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_my_info, container, false);
        initView(layout);
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLogin();

        initView(view);
    }

    private void initLogin() {
//        MainActivity activity = (MainActivity) getActivity();
//        boolean isLogined = activity.getLogined();
//        if (isLogined) {
            // 读取登录类型
            SharedPreferences sp = getActivity().getSharedPreferences("info_account",
                    Context.MODE_PRIVATE);
            isLogined = sp.getBoolean("isLogined", false);
            uid = sp.getString("username", "");
        if (isLogined){
            mLayoutNotLogined.setVisibility(View.GONE);
            mLayoutLogined.setVisibility(View.VISIBLE);
            mTvUid.setText(uid);
        } else {
            mLayoutNotLogined.setVisibility(View.VISIBLE);
            mLayoutLogined.setVisibility(View.GONE);
        }
    }

    private void initView(View view) {

        mPersonalLoginButton = (Button) layout.findViewById(R.id.personal_login_button);
        mPersonalLoginButton.setOnClickListener(this);
        mLayoutMineOrder = (LinearLayout) layout.findViewById(R.id.layout_mine_order);
        mLayoutMineOrder.setOnClickListener(this);
        mLayoutMineFav = (LinearLayout) layout.findViewById(R.id.layout_mine_fav);
        mLayoutMineFav.setOnClickListener(this);
        mLayoutMineSet = (LinearLayout) layout.findViewById(R.id.layout_mine_set);
        mLayoutMineSet.setOnClickListener(this);
        mLayoutNotLogined = (RelativeLayout) layout.findViewById(R.id.layout_not_logined);
        mLayoutNotLogined.setOnClickListener(this);
        mTvUid = (TextView) layout.findViewById(R.id.tv_uid);
        mTvUid.setOnClickListener(this);
        mLayoutLogined = (RelativeLayout) layout.findViewById(R.id.layout_logined);
        mLayoutLogined.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_login_button:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                startActivityForResult(intent, Constants.INTENT_KEY.LOGIN_REQUEST_CODE);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.layout_mine_order:
                startActivity(new Intent(getActivity(), OrderActivity.class));
                break;
            case R.id.layout_mine_fav:
                startActivity(new Intent(getActivity(), FavActivity.class));
                break;
            case R.id.layout_mine_set:
                startActivity(new Intent(getActivity(), SetActivity.class));
                break;
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == Constants.INTENT_KEY.LOGIN_REQUEST_CODE) {
//            if (resultCode == Constants.INTENT_KEY.LOGIN_RESULT_SUCCESS_CODE) {
//                SharedPreferences sp = getActivity().getSharedPreferences(
//                        "info_account", Context.MODE_PRIVATE);
//                boolean isLogined = sp.getBoolean("isLogined", false);
//                String uid = "";
////                String icon = "";
//                uid = data.getStringExtra("uid");
//
//                mTvUid.setText(uid);
//                mLayoutNotLogined.setVisibility(View.GONE);
//                mLayoutLogined.setVisibility(View.VISIBLE);
//                // 将登录结果设置给MainActivity
//                MainActivity activity = (MainActivity) getActivity();
//                activity.setIsLogined(true, uid);
//            }
//        } else if (requestCode == Constants.INTENT_KEY.REQUEST_MOREACTIVITY) {
//            initLogin();
//        }
//    }
}
