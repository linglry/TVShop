package com.example.administrator.tvshop.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.administrator.tvshop.R;
import com.example.administrator.tvshop.UserConstants;
import com.example.administrator.tvshop.activity.DetailActivity;
import com.example.administrator.tvshop.bean.Goods;
import com.example.administrator.tvshop.model.DBHelper;
import com.example.administrator.tvshop.utils.AppUrl;
import com.example.administrator.tvshop.utils.GetInfo;
import com.example.administrator.tvshop.utils.ImageUtil;
import com.example.administrator.tvshop.utils.URLEncode;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017.4.5.
 */

public class CartFragment extends Fragment implements View.OnClickListener {

    private View layout;
    private TextView mTvEditCart;
    private RelativeLayout mLayoutTopbar;
    private CheckBox mBtnCheckAll;
    private TextView mTvAddAll;
    private TextView mTvTotal;
    private TextView mTvPay;
    private TextView mTvCount;
    private RelativeLayout mBtnPay;
    private TextView mTvPrice;
    private RelativeLayout mLayoutPayBar;
    private CheckBox mBtnCheckAllDeit;
    private Button mBtnDelete;
    private Button mBtnCollect;
    private RelativeLayout mLayoutEditBar;
    private ImageView mImgNull;
    private TextView mTvNull;
    private TextView mTvNull2;
    private Button products;
    private RelativeLayout mLayoutNull;
    private SwipeMenuListView mSwipeMenuListView;

    private List<Goods.DataBean> datas;//数据源
    //    private List<Map> datas;
    private CartAdapter adapter;//适配器
    private ListView listView;

    private HashMap<String, Boolean> inCartMap = new HashMap<String, Boolean>();// 用于存放选中的项

    private DBHelper helper;
    private TextView tvPrice;
    String uid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_card, container, false);
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        datas = helper.getAllData(uid);
        adapter = new CartAdapter(datas, getContext());
        mSwipeMenuListView.setAdapter(adapter);
        totalSum(datas);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        helper = new DBHelper(getActivity());
        uid = new GetInfo().getUid(getActivity());
        getData();
        adapter = new CartAdapter(datas, getContext());
//        listView.setAdapter(adapter);

        //添加减少商品
        adapter.setOnAddNum(this);
        adapter.setOnSubNum(this);

        initSwipeListView();
        totalSum(datas);

    }

    private void initView(View view) {
        mSwipeMenuListView = (SwipeMenuListView) view.findViewById(R.id.swipeMenuListView);
        mBtnPay = (RelativeLayout) view.findViewById(R.id.btn_pay);
        mBtnPay.setOnClickListener(this);
        tvPrice = (TextView) view.findViewById(R.id.tv_price);
    }

    /**
     * 从数据库中取出数据
     */
    private void getData() {
        datas = new ArrayList<>();
        datas = helper.getAllData(uid);
//        if (helper.getAllData(uid).size() == 0){//从数据库查询出所有数据
//            datas = null;
//        }
    }

    /**
     * 新建布局
     * 左移删除
     */
    private void initSwipeListView() {
        mSwipeMenuListView.setAdapter(adapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                //获取屏幕宽度
                DisplayMetrics metrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
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
                        getActivity());
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
//                        Toast.makeText(getActivity(), "打开:" + position, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra("id", datas.get(position).getId());
                        startActivity(intent);
                        break;
                    case 1:
                        Log.i("xx", "onMenuItemClick: " + position + datas.get(position).getId());
//                        Toast.makeText(getActivity(), "删除:" + position, Toast.LENGTH_SHORT).show();
                        helper.deleteBySql(datas.get(position).getId(), uid);
                        datas.remove(datas.get(position));
                        break;
                }
                adapter.notifyDataSetChanged();

                // false : close the menu; true : not close the menu
                return false;
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_pay:
                go2Pay();
                break;
        }
    }

    private void go2Pay() {
        URLEncode urlEncode = new URLEncode(AppUrl.setOrders);
        for (int i = 0; i < datas.size(); i++) {
            int key = datas.get(i).getId();
            int value = datas.get(i).getNum();
            Log.i("xx", "go2Pay: " + key + ", " + value);


            urlEncode.add(key, value);
        }
        Log.i("xx", "go2Pay: " + urlEncode.out());
        HttpUtils httpUtils = new HttpUtils();

        RequestParams params = new RequestParams();

        String token = new GetInfo().getToken(getActivity());
        params.addHeader("token", token);
        httpUtils.configCurrentHttpCacheExpiry(0);

        httpUtils.send(HttpRequest.HttpMethod.GET, urlEncode.out(), params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt("code");
                    switch (code) {
                        case UserConstants.LOGIN_SUCCESS:
                            Toast.makeText(getActivity(), "支付成功，请在订单列表中确认", Toast.LENGTH_SHORT).show();
                            helper.deleteByUid(uid);
                            datas.clear();
                            adapter.notifyDataSetChanged();
                            totalSum(datas);
                            break;
                        default:
                            Toast.makeText(getActivity(), json.getString("msg"), Toast.LENGTH_SHORT).show();
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

    /**
     * 适配器部分
     */
    class CartAdapter extends BaseAdapter implements View.OnClickListener {
        Context context;
        private List<Goods.DataBean> products;

        //1.设置接口
        private View.OnClickListener onAddNum;
        private View.OnClickListener onSubNum;

        //2.设置接口方法
        public void setOnAddNum(View.OnClickListener onAddNum) {
            this.onAddNum = onAddNum;
        }

        public void setOnSubNum(View.OnClickListener onSubNum) {
            this.onSubNum = onSubNum;
        }

        public CartAdapter(List<Goods.DataBean> products, Context context) {
            this.products = products;
            this.context = context;
        }

        @Override
        public int getCount() {
            int ret = 0;
            if (products != null) {
                ret = products.size();
            }
            return ret;
        }

        @Override
        public Object getItem(int i) {
            return products.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            final Goods.DataBean inCart = products.get(i);
//            Boolean isChecked = inCartMap.get(inCart.getGoodsId());
            if (view == null) {
                view = getActivity().getLayoutInflater().inflate(R.layout.item_card_list, null);
                holder = new ViewHolder();

                holder.butSub = (Button) view.findViewById(R.id.btn_card_sub);
                holder.butAdd = (Button) view.findViewById(R.id.btn_card_add);
                holder.btnNumEdit = (Button) view.findViewById(R.id.btn_cart_num_edit);
                holder.imgGoods = (ImageView) view.findViewById(R.id.img_goods);
                holder.tvGoodsName = (TextView) view.findViewById(R.id.tv_goods_name);
                holder.tvGoodsPrice = (TextView) view.findViewById(R.id.tv_goods_price);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.tvGoodsName.setText(products.get(i).getTitle());
            holder.tvGoodsPrice.setText(products.get(i).getPrice() + "");
            new ImageUtil().VolleyImage(products.get(i).getPicture(), getActivity(), holder.imgGoods);
            holder.btnNumEdit.setText(products.get(i).getNum() + "");
            //设置点击事件
            holder.butAdd.setOnClickListener(this);
            holder.butSub.setOnClickListener(this);

            //得到可用的图片
//            Bitmap bitmap = getHttpBitmap(products.get(i).getGoodsIcon());
//            holder.imgGoods.setImageBitmap(bitmap);

            holder.butAdd.setTag(i);
            holder.butSub.setTag(i);
            view.setTag(holder);

//            holder.btnRedece.setEnabled(true);//加判断
            return view;
        }

        @Override
        public void onClick(View view) {
            Object tag = view.getTag();
            int position = (Integer) tag;
            int num = products.get(position).getNum();
            switch (view.getId()) {
                case R.id.btn_card_add:
                    if (tag != null && tag instanceof Integer) {// 解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的position
                        num++;
                        products.get(position).setNum(num);
                        adapter.notifyDataSetChanged();
                        totalSum(products);
                    }
                    break;
                case R.id.btn_card_sub:
                    if (tag != null && tag instanceof Integer) {
                        if (num > 1) {
                            num--;
                            products.get(position).setNum(num);
                            adapter.notifyDataSetChanged();
                            totalSum(products);
                        }
                    }
                    break;
            }
        }

        class ViewHolder {
            Button butAdd;
            Button butSub;
            Button btnNumEdit;
            ImageView imgGoods;
            TextView tvGoodsName;
            TextView tvGoodsPrice;
        }
    }


    private void totalSum(List<Goods.DataBean> datas) {
        double mdoubTotal = 0;
        if (datas.size() == 0) {
            tvPrice.setText(0 + "");
        }
        for (int i = 0; i < datas.size(); i++) {
            int price = datas.get(i).getNum() * datas.get(i).getPrice();
            mdoubTotal += price;
            tvPrice.setText(mdoubTotal + "");
        }


    }

}
