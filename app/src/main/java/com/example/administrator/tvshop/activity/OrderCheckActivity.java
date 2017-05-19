package com.example.administrator.tvshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tvshop.R;
import com.example.administrator.tvshop.UserConstants;
import com.example.administrator.tvshop.utils.AppUrl;
import com.example.administrator.tvshop.utils.GetInfo;
import com.example.administrator.tvshop.utils.URLEncode;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderCheckActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImgBack;
    private TextView mTvId;
    private TextView mTvState;
    private TextView mTvTime;
    private TextView mTvAddress;
    private Button mBtCheck;
    private int id, state;
    private String time, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_order_check);
        initView();

        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getInt("id");
        state = bundle.getInt("state");
        time = bundle.getString("time");
        address = bundle.getString("address");

        mTvId.setText(id +"");
        if(state == 0){
            mTvState.setText("已付款");
        } else if (state == 1){
            mTvState.setText("已发货");
            mBtCheck.setVisibility(View.VISIBLE);//确认收货

        } else {
            mTvState.setText("交易完成");

        }
        mTvTime.setText(time);
        mTvAddress.setText(address);


    }

    private void initView() {
        mImgBack = (ImageView) findViewById(R.id.img_back);
        mTvId = (TextView) findViewById(R.id.tv_id);
        mTvState = (TextView) findViewById(R.id.tv_state);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mBtCheck = (Button) findViewById(R.id.bt_check);

        mBtCheck.setOnClickListener(this);
        mImgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_check:
                setSh();
                mTvState.setText("交易完成");
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    private void setSh() {
        HttpUtils httpUtils = new HttpUtils();

        URLEncode urlEncode = new URLEncode(AppUrl.setSH);
        urlEncode.add("id", id);
        RequestParams params = new RequestParams();

        String token = new GetInfo().getToken(this);
        params.addHeader("token", token);
        httpUtils.configCurrentHttpCacheExpiry(0);
        httpUtils.send(HttpRequest.HttpMethod.GET, urlEncode.out(), params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
//                        Log.i("xx", result);
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt("code");
                    switch (code) {
                        case UserConstants.LOGIN_SUCCESS:
                            Toast.makeText(OrderCheckActivity.this, "确认收货成功", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(OrderCheckActivity.this, json.getString("msg"), Toast.LENGTH_SHORT).show();
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Gson gson = new Gson();
//                Goods info = gson.fromJson(result, Goods.class);
//                data = new ArrayList<Goods.DataBean>();

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }


}
