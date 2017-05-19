package com.example.administrator.tvshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tvshop.R;
import com.example.administrator.tvshop.UserConstants;
import com.example.administrator.tvshop.bean.Goods;
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

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtSearch;
    private ImageView mImgBack;
    private TextView mTvSearch;
    private String title;
    private ArrayList<Goods.DataBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        initView();
    }

    private void search() {
        String title = mEtSearch.getText().toString();
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "请输入", Toast.LENGTH_SHORT).show();
            return;
        }

        HttpUtils httpUtils = new HttpUtils();

        URLEncode urlEncode = new URLEncode(AppUrl.search);
        urlEncode.add("title", title);
        RequestParams params = new RequestParams();

        String token = new GetInfo().getToken(this);
        params.addHeader("token", token);
        httpUtils.configCurrentHttpCacheExpiry(0);
        httpUtils.send(HttpRequest.HttpMethod.GET, urlEncode.out(), params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
//                        Log.i("xx", result);

                Gson gson = new Gson();
                Goods info = gson.fromJson(result, Goods.class);
                data = new ArrayList<Goods.DataBean>();
                switch (info.getCode()) {
                    case UserConstants.LOGIN_SUCCESS:
                        Log.i("xx", "onSuccess: " + info.getData().size());
                        Goods.DataBean datas;
                        /**
                         * 将获取到的数据传递给商品详情页
                         */
                        for (int i = 0; i < info.getData().size(); i++) {
                            int id = info.getData().get(i).getId();
                            String title = info.getData().get(i).getTitle();
                            int price = info.getData().get(i).getPrice();
                            int count = info.getData().get(i).getCount();
                            String picture = info.getData().get(i).getPicture();
                            datas = new Goods.DataBean(id, title, count, price, picture);
                            data.add(datas);
                        }

                        Intent intent = new Intent(SearchActivity.this, GoodsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", data);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    default:
                        Toast.makeText(SearchActivity.this, info.getMsg(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i("xx", "失败");
            }

        });
    }

    private void initView() {
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mImgBack = (ImageView) findViewById(R.id.img_back);
        mTvSearch = (TextView) findViewById(R.id.tv_search);

        mTvSearch.setOnClickListener(this);
        mImgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search://打开搜索结果页面
                search();
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

}
