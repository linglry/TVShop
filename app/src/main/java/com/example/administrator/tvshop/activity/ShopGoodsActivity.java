package com.example.administrator.tvshop.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.ArrayList;

public class ShopGoodsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImgBack;
    private TextView mTvAddGoods;
    private ListView mLvGoods;
    private MyAdapter adapter;

    //    private List<String> list;
    private ArrayList<Goods.DataBean> list = new ArrayList<Goods.DataBean>();
    private SwipeMenuListView mSwipeMenuListView;
    private String TAG="xx";
    private RequestQueue queue;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shop_goods);

        queue = Volley.newRequestQueue(this);
        imageLoader = new ImageLoader(queue, new BitmapCache());
        initGoods();
        initView();
        initSwipeListView();


    }

    private void initSwipeListView() {
        adapter = new MyAdapter();
        mSwipeMenuListView.setAdapter(adapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(ShopGoodsActivity.this);
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
                SwipeMenuItem deleteItem = new SwipeMenuItem(ShopGoodsActivity.this);
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
        mSwipeMenuListView.setMenuCreator(creator);

        // 2.lister item click event
        mSwipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        openActivity(position);
                        break;
                    case 1:
                        Toast.makeText(ShopGoodsActivity.this, "删除:" + list.get(position).getId(), Toast.LENGTH_SHORT).show();
                        delGoods(position, list.get(position).getId());
                        break;
                }
                adapter.notifyDataSetChanged();
                return false;
            }


        });

        mSwipeMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openActivity(i);
            }
        });
    }

    private void delGoods(final int position, int id) {
        HttpUtils httpUtils = new HttpUtils();
        URLEncode urlEncode = new URLEncode(AppUrl.delMyGoods);
        urlEncode.add("id", id);
        Log.i("xx", urlEncode.out());
        RequestParams params = new RequestParams();

        String token = new GetInfo().getToken(this);
        params.addHeader("token", token);
        httpUtils.configCurrentHttpCacheExpiry(0);
        httpUtils.send(HttpRequest.HttpMethod.GET, urlEncode.out(), params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.i("xx", "pic: " + responseInfo.result);
                list.remove(position);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private void openActivity(int i) {
        int id = list.get(i).getId();
        String name = list.get(i).getTitle();
        int price = list.get(i).getPrice();
        int num = list.get(i).getCount();
        String picture = list.get(i).getPicture();
        String field1 = list.get(i).getField1();
        String field2 = list.get(i).getField2();
        String detail = list.get(i).getContent();

        Intent intent = new Intent();
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("price", price);
        intent.putExtra("num", num);
        intent.putExtra("picture", picture);
        intent.putExtra("field1", field1);
        intent.putExtra("field2", field2);
        intent.putExtra("detail", detail);
        if(!TextUtils.isEmpty(list.get(i).getSprice()+"")){
            intent.putExtra("sprice", list.get(i).getSprice());
        }
        if(!TextUtils.isEmpty(list.get(i).getContent())){
            intent.putExtra("content", list.get(i).getContent());
        }
        intent.setClass(ShopGoodsActivity.this, ShopUpdateGoodsActivity.class);
        startActivity(intent);
    }


    private void initGoods() {
        HttpUtils httpUtils = new HttpUtils();

        RequestParams params = new RequestParams();

        String token = new GetInfo().getToken(this);
        params.addHeader("token", token);
        httpUtils.configCurrentHttpCacheExpiry(0);

        httpUtils.send(HttpRequest.HttpMethod.GET, AppUrl.getMyGoods, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Log.i("xx", result);

                Gson gson = new Gson();
                Goods info = gson.fromJson(result, Goods.class);
                list = new ArrayList<Goods.DataBean>();
                switch (info.getCode()) {
                    case UserConstants.LOGIN_SUCCESS:
                        Goods.DataBean datas;
                        for (int i = 0; i < info.getData().size(); i++) {
                            int id = info.getData().get(i).getId();
                            String title = info.getData().get(i).getTitle();
                            int price = info.getData().get(i).getPrice();
                            int count = info.getData().get(i).getCount();
                            String picture = R.mipmap.ic_launcher+"";
                            if(info.getData().get(i).getPicture()!=null){
                                picture = info.getData().get(i).getPicture();
                            }
                            int sprice= 0;
                            if (info.getData().get(i).getSprice()!=0){
                                sprice = info.getData().get(i).getSprice();
                            }
                            String field1 = info.getData().get(i).getField1();
                            String field2 = info.getData().get(i).getField2();
                            datas = new Goods.DataBean(id, title, count, price, picture, field1, field2);
                            list.add(datas);
                        }
                        initSwipeListView();
//                        Log.i("xx", "onSuccess: " + data.get(0).getTitle());
                        break;
                    default:
                        Toast.makeText(ShopGoodsActivity.this, info.getMsg(), Toast.LENGTH_SHORT).show();
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
        mImgBack.setOnClickListener(this);
        mTvAddGoods = (TextView) findViewById(R.id.tv_add_goods);
        mTvAddGoods.setOnClickListener(this);
        mSwipeMenuListView = (SwipeMenuListView) findViewById(R.id.swipeMenuListView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_add_goods:
                startActivity(new Intent(this, ShopAddGoodsActivity.class));
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
                view = getLayoutInflater().inflate(R.layout.item_goods_list, null);
                holder = new ViewHolder();
                holder.imgGoods = (NetworkImageView) view.findViewById(R.id.img_icon);
                holder.tvGoodsName = (TextView) view.findViewById(R.id.tv_title);
                holder.tvGoodsPrice = (TextView) view.findViewById(R.id.tv_price);
                holder.tvGoodsCount = (TextView) view.findViewById(R.id.tv_count);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
//                    GoodsInfo goodsInfo = goodsList.get(position);
            Goods.DataBean shopCard = list.get(i);
            holder.tvGoodsName.setText(shopCard.getTitle());
            holder.tvGoodsPrice.setText(shopCard.getPrice() + "");
            holder.tvGoodsCount.setText(shopCard.getCount()+"");
            holder.imgGoods.setImageResource(R.mipmap.ic_launcher);
            holder.imgGoods.setTag(shopCard.getPicture());
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
            TextView tvGoodsCount;
        }
    }
}
