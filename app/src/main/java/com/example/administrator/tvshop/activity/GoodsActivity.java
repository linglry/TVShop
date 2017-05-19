package com.example.administrator.tvshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.example.administrator.tvshop.R;
import com.example.administrator.tvshop.bean.Goods;
import com.example.administrator.tvshop.utils.BitmapCache;
import com.example.administrator.tvshop.utils.URLEncode;

import java.util.ArrayList;


public class GoodsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImgBack;
    private ListView mLvResult;
    private ArrayList<Goods.DataBean> list = new ArrayList<>();
    private MyAdapter adapter;
    private RequestQueue queue;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goods);
        queue = Volley.newRequestQueue(this);
        imageLoader = new ImageLoader(queue, new BitmapCache());
        initView();
        getData();

        initListView();
    }

    private void getData() {
        Intent intent = this.getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        Bundle bundle = intent.getExtras();//.getExtras()得到intent所附带的额外数据
        list = (ArrayList<Goods.DataBean>) bundle.getSerializable("data");
        if (list.size() == 0) {
            Toast.makeText(this, "没有搜索到数据", Toast.LENGTH_SHORT).show();
            return;
        }

    }


    private void initListView() {
        adapter = new MyAdapter();
        mLvResult.setAdapter(adapter);
        mLvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(GoodsActivity.this, i+"," + list.get(i).getId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GoodsActivity.this, DetailActivity.class);
                intent.putExtra("id", list.get(i).getId());
                startActivity(intent);
            }
        });
    }


    private void initView() {
        mImgBack = (ImageView) findViewById(R.id.img_back);
        mLvResult = (ListView) findViewById(R.id.lv_result);

        mImgBack.setOnClickListener(this);
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

            final ViewHolder holder;
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
            Goods.DataBean goods = list.get(i);
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
