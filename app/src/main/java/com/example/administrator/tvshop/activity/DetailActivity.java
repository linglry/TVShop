package com.example.administrator.tvshop.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.tvshop.R;
import com.example.administrator.tvshop.UserConstants;
import com.example.administrator.tvshop.bean.Goods;
import com.example.administrator.tvshop.bean.ShopCard;
import com.example.administrator.tvshop.model.DBHelper;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImgBack;
    private ImageView mImgFavor;
    private Button mBtnAddToCart;
    private TextView mTvTitle;
    private TextView mTvPrice;
    private List<ShopCard> datas;

    DBHelper helper;
    private String TAG = "xx";
    private ImageView mIvImg;
    private TextView mTvCount;
    private RelativeLayout mBtnCollect;
    private TextView mTvContent;
    private TextView mTvSprice;

//    int ids;
    private String title;
    private int price;
    private int count;
    private String picture;
    private RequestQueue queues;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);
        queues = Volley.newRequestQueue(this);
        initView();

        initData();

        helper = new DBHelper(this);

    }

    /**
     * 接收数据
     */
    private void initData() {
        /**
         * 获取id
         */
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getInt("id");

        HttpUtils httpUtils = new HttpUtils();

        URLEncode urlEncode = new URLEncode(AppUrl.search);
        urlEncode.add("id", id + "");
        RequestParams params = new RequestParams();

        String token = new GetInfo().getToken(this);

        params.addHeader("token", token);
        httpUtils.configCurrentHttpCacheExpiry(0);
        httpUtils.send(HttpRequest.HttpMethod.GET, urlEncode.out(), params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Gson gson = new Gson();
                Goods info = gson.fromJson(result, Goods.class);
                switch (info.getCode()) {
                    /**
                     * 成功，获取其他相关信息
                     */
                    case UserConstants.LOGIN_SUCCESS:
//                        ids = info.getData().get(0).getGid();
                        title = info.getData().get(0).getTitle();
                        price = info.getData().get(0).getPrice();
                        count = info.getData().get(0).getCount();
                        picture = info.getData().get(0).getPicture();

                        mTvTitle.setText(info.getData().get(0).getTitle());
                        mTvPrice.setText(info.getData().get(0).getPrice()+"");
                        mTvCount.setText(info.getData().get(0).getCount()+"");
                        ImageRequest imageRequest = new ImageRequest(
                                URLEncode.encode(picture),
                                new Response.Listener<Bitmap>() {
                                    @Override
                                    public void onResponse(Bitmap response) {
                                        mIvImg.setImageBitmap(response);
                                    }
                                },0,0,
                                ImageView.ScaleType.FIT_XY,
                                Bitmap.Config.ARGB_8888,
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.i("xx", "onErrorResponse: ");
                                        mIvImg.setImageResource(R.mipmap.ic_launcher);
                                    }
                                });
                        queues.add(imageRequest);
                        if (info.getData().get(0).getContent() != null) {
                            mTvContent.setText(info.getData().get(0).getContent());
                        }
                        if (info.getData().get(0).getSprice() != 0) {
                            mTvSprice.setText(info.getData().get(0).getSprice());
                        }
                        break;
                    default:
                        Toast.makeText(DetailActivity.this, info.getMsg(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i(TAG, "onFailure: ");
            }
        });

//        mTvTitle.setText();
    }

    private void initView() {
        mImgBack = (ImageView) findViewById(R.id.img_back);
        mImgFavor = (ImageView) findViewById(R.id.img_favor);
        mBtnAddToCart = (Button) findViewById(R.id.btn_add_to_cart);

        mBtnAddToCart.setOnClickListener(this);
        mImgBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mIvImg = (ImageView) findViewById(R.id.iv_img);
        mTvCount = (TextView) findViewById(R.id.tv_count);
        mTvSprice = (TextView) findViewById(R.id.tv_sprice);

        mBtnCollect = (RelativeLayout) findViewById(R.id.btn_collect);//收藏
        mBtnCollect.setOnClickListener(this);
        mTvContent = (TextView) findViewById(R.id.tv_content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_to_cart:
                add2Cart();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_collect:
                add2Collect();
                break;
        }
    }

    private void add2Collect() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("id");
        Log.i(TAG, "initData: " + id);

        HttpUtils httpUtils = new HttpUtils();

        URLEncode urlEncode = new URLEncode(AppUrl.addFav);
        urlEncode.add("gid", id + "");
        Log.i(TAG, "add2Collect: "+ urlEncode.out());
        RequestParams params = new RequestParams();

        String token = new GetInfo().getToken(this);
        params.addHeader("token", token);
        httpUtils.configCurrentHttpCacheExpiry(0);
        httpUtils.send(HttpRequest.HttpMethod.GET, urlEncode.out(), params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
//                Log.i(TAG, "onSuccess: "+ result);
                int code = 0;
                try {
                    JSONObject json = new JSONObject(result);
                     code = json.getInt("code");
                    Log.i(TAG, "onSuccess: " + code);
//                    datas = json.getJSONObject("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                switch (code) {
                    case UserConstants.LOGIN_SUCCESS:
                        Toast.makeText(DetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Gson gson = new Gson();
                        Goods info = gson.fromJson(result, Goods.class);
                        Toast.makeText(DetailActivity.this, info.getMsg(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i(TAG, "onFailure: ");
            }
        });
    }

    /**
     * 加入购物车
     */
    private void add2Cart() {
        String uid = new GetInfo().getUid(this);
        Goods.DataBean a = new Goods.DataBean(id, uid,title, price, 1, picture);
        helper.addBySql(a, uid);     //插入数据库
        Toast.makeText(this, "加入成功", Toast.LENGTH_SHORT).show();
    }

}
