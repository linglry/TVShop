package com.example.administrator.tvshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.administrator.tvshop.R;
import com.example.administrator.tvshop.UserConstants;
import com.example.administrator.tvshop.bean.Goods;
import com.example.administrator.tvshop.bean.Order;
import com.example.administrator.tvshop.utils.AppUrl;
import com.example.administrator.tvshop.utils.BitmapCache;
import com.example.administrator.tvshop.utils.GetInfo;
import com.example.administrator.tvshop.utils.URLEncode;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopOrderActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ImageView mImgBack;
    private ListView mLvOrder;

    private ArrayList<Order.DataBean> list = new ArrayList<Order.DataBean>();
    private ArrayList<Goods.DataBean> data = new ArrayList<Goods.DataBean>();
    private MyAdapter adapter;
    private List<Integer> ids = new ArrayList<>();

    private RequestQueue queue;
    private ImageLoader imageLoader;
    private List<Integer> state = new ArrayList<>();
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shop_order);
        initView();

        queue = Volley.newRequestQueue(this);
        imageLoader = new ImageLoader(queue, new BitmapCache());

        getGoods();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (state != null) {
            state.clear();
            getState();
        }
    }

    private void getState() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();

        String token = new GetInfo().getToken(this);
        params.addHeader("token", token);
        httpUtils.configCurrentHttpCacheExpiry(0);
        httpUtils.send(HttpRequest.HttpMethod.GET, AppUrl.getSOrders, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Log.i("xx", result);

                Gson gson = new Gson();
                Order info = gson.fromJson(result, Order.class);
//                list = new ArrayList<Order.DataBean>();
                switch (info.getCode()) {
                    case UserConstants.LOGIN_SUCCESS:
//                        Order.DataBean datas;
                        for (int i = 0; i < info.getData().size(); i++) {
                            state.add(info.getData().get(i).getStatus());
                        }
//                        search();
                        break;
                    default:
                        Toast.makeText(ShopOrderActivity.this, info.getMsg(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private void getGoods() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();

        String token = new GetInfo().getToken(this);
        params.addHeader("token", token);
        httpUtils.configCurrentHttpCacheExpiry(0);
        httpUtils.send(HttpRequest.HttpMethod.GET, AppUrl.getSOrders, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Log.i("xx", "order" + result);

                Gson gson = new Gson();
                Order info = gson.fromJson(result, Order.class);
                list = new ArrayList<Order.DataBean>();
                switch (info.getCode()) {
                    case UserConstants.LOGIN_SUCCESS:
                        Order.DataBean datas;
                        for (int i = 0; i < info.getData().size(); i++) {
                            int id = info.getData().get(i).getId();
                            int gid = info.getData().get(i).getGid();
                            int uid = info.getData().get(i).getUid();
                            int guid = info.getData().get(i).getGuid();
                            int count = info.getData().get(i).getCount();
                            int price = info.getData().get(i).getPrice();
                            state.add(info.getData().get(i).getStatus());
                            String address = info.getData().get(i).getAddress();
                            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String times = sdf.format(new Date(String.valueOf(info.getData().get(i).getTime())));
                            String title = info.getData().get(i).getTitle();
                            String picture = info.getData().get(i).getPicture();
                            datas = new Order.DataBean(id, gid, uid, guid, count, price, address, times, state.get(i), title, picture);
                            list.add(datas);
//                            ids.add(gid);
                        }
//                        search();
                        initListView();
                        break;
                    default:
                        Toast.makeText(ShopOrderActivity.this, info.getMsg(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private void initView() {
        mImgBack = (ImageView) findViewById(R.id.img_back);
        mLvOrder = (ListView) findViewById(R.id.lv_order);
        mImgBack.setOnClickListener(this);
        mLvOrder.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(ShopOrderActivity.this, ShopOrderCheckActivity.class);
        intent.putExtra("id", list.get(i).getId());
        intent.putExtra("state", state.get(i));
        intent.putExtra("time", list.get(i).getTimes());
        intent.putExtra("address", list.get(i).getAddress());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    private void initListView() {
        adapter = new MyAdapter();
        mLvOrder.setAdapter(adapter);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            ViewHolder holder = null;
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.item_order_list, null);
                holder = new ViewHolder();
                holder.imgGoods = (NetworkImageView) view.findViewById(R.id.img_icon);
                holder.tvGoodsName = (TextView) view.findViewById(R.id.tv_title);
                holder.tvGoodsPrice = (TextView) view.findViewById(R.id.tv_price);
                holder.tvGoodsNum = (TextView) view.findViewById(R.id.tv_num);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
//                    GoodsInfo goodsInfo = goodsList.get(position);
//            Goods.DataBean shopCard = data.get(i);
            Order.DataBean goods = list.get(i);
            holder.tvGoodsName.setText(goods.getTitle());
            holder.tvGoodsPrice.setText(goods.getPrice() + "");
            holder.tvGoodsNum.setText(goods.getCount() + "");
            String imgUrl = URLEncode.encode(goods.getPicture());
            if (imgUrl != null && !imgUrl.equals("")) {
                holder.imgGoods.setDefaultImageResId(R.mipmap.ic_launcher);
                holder.imgGoods.setErrorImageResId(R.mipmap.ic_launcher);
                holder.imgGoods.setImageUrl(imgUrl, imageLoader);

            }
            return view;
        }

        class ViewHolder {
            NetworkImageView imgGoods;
            TextView tvGoodsName;
            TextView tvGoodsPrice;
            TextView tvGoodsNum;
        }
    }

}
