package com.example.administrator.tvshop.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.administrator.tvshop.R;
import com.example.administrator.tvshop.UserConstants;
import com.example.administrator.tvshop.bean.Goods;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FavActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImgBack;
    private SwipeMenuListView mLvOrder;
    private MyAdapter adapter;
    private ArrayList<Goods.DataBean> list = new ArrayList<Goods.DataBean>();
//    private ArrayList<Goods.DataBean> data;
private RequestQueue queue;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fav);

        queue = Volley.newRequestQueue(this);
        imageLoader = new ImageLoader(queue, new BitmapCache());
        initView();
        initGoods();
//        initListView();
    }


//    private void initListView() {
////        mLvOrder.setAdapter(adapter);
//
//
//    }

    private void initView() {
        mImgBack = (ImageView) findViewById(R.id.img_back);
        mLvOrder = (SwipeMenuListView) findViewById(R.id.lv_order);
//        mLvOrder.setOnItemClickListener(this);

        mImgBack.setOnClickListener(this);
    }

    private void initSwipeListView() {
        adapter = new MyAdapter();

        mLvOrder.setAdapter(adapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(
                        FavActivity.this);
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                //获取屏幕宽度
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                openItem.setWidth(metrics.widthPixels / 5);
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // 左移删除
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        FavActivity.this);
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                //获取屏幕宽度
//                getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                deleteItem.setWidth(metrics.widthPixels / 5);
                // set a icon
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mLvOrder.setMenuCreator(creator);

        // 2.lister item click event
        mLvOrder.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
//                        Toast.makeText(getActivity(), "打开:" + position, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FavActivity.this, DetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", list.get(position).getId());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case 1:
//                        Toast.makeText(FavActivity.this, "删除:" + list.get(position).getId(), Toast.LENGTH_SHORT).show();
                        delFav(position, list.get(position).getId());
                        break;
                }
                adapter.notifyDataSetChanged();

                // false : close the menu; true : not close the menu
                return false;
            }
        });

        mLvOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FavActivity.this, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", list.get(i).getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void delFav(final int position, final int id) {

        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        URLEncode urlEncode = new URLEncode(AppUrl.delFav);
        urlEncode.add("gid", id);

        String token = new GetInfo().getToken(this);
        params.addHeader("token", token);
        httpUtils.configCurrentHttpCacheExpiry(0);
        httpUtils.send(HttpRequest.HttpMethod.GET, urlEncode.out(), params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Log.i("xx", "onSuccess: " + result);
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt("code");
                    switch (code) {
                        case UserConstants.LOGIN_SUCCESS:
                            Toast.makeText(FavActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            list.remove(position);
                            adapter.notifyDataSetChanged();
                            break;
                        default:
                            Toast.makeText(FavActivity.this, json.getString("msg"), Toast.LENGTH_SHORT).show();
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private void initGoods() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();

        String token = new GetInfo().getToken(this);
        params.addHeader("token", token);
        httpUtils.configCurrentHttpCacheExpiry(0);
        httpUtils.send(HttpRequest.HttpMethod.GET, AppUrl.getFav, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
//                Log.i("xx", result);

                Gson gson = new Gson();
                Goods info = gson.fromJson(result, Goods.class);
                list = new ArrayList<Goods.DataBean>();
                switch (info.getCode()) {
                    case UserConstants.LOGIN_SUCCESS:
                        Goods.DataBean datas;
                        for (int i = 0; i < info.getData().size(); i++) {
                            int id = info.getData().get(i).getGid();
                            String title = info.getData().get(i).getTitle();
                            int price = info.getData().get(i).getPrice();
                            int count = info.getData().get(i).getCount();
                            String picture = info.getData().get(i).getPicture();
                            Log.i("xx", "onSuccess: " + info.getData().get(i).getGid());
                            datas = new Goods.DataBean(id, title, count, price, picture);
                            list.add(datas);
                        }
                        initSwipeListView();
//                        Log.i("xx", "onSuccess: " + data.get(0).getTitle());
                        break;
                    default:
                        Toast.makeText(FavActivity.this, info.getMsg(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
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
            Goods.DataBean shopCard = list.get(i);
            holder.tvGoodsName.setText(shopCard.getTitle());
            holder.tvGoodsPrice.setText(shopCard.getPrice() + "");
            holder.tvGoodsNum.setText(shopCard.getCount() + "");
            String imgUrl = URLEncode.encode(shopCard.getPicture());
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
