package com.example.administrator.tvshop.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.tvshop.R;
import com.example.administrator.tvshop.UserConstants;
import com.example.administrator.tvshop.activity.GoodsActivity;
import com.example.administrator.tvshop.bean.Goods;
import com.example.administrator.tvshop.bean.MyGrid;
import com.example.administrator.tvshop.utils.AppUrl;
import com.example.administrator.tvshop.utils.GetInfo;
import com.example.administrator.tvshop.utils.Global;
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
import java.util.Iterator;

/**
 * Created by Administrator on 2017.4.5.
 */

public class ListChildFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View layout;
    public static final String TAG = "xx";
    private String str;
    private ArrayList<MyGrid> list = new ArrayList<>();
    private TextView mTvCategory;
    private ImageView mLine;
    private GridView mItemGridView;
    private ProgressBar mProgressBar;
    private GridAdapter adapter;
    private JSONObject datas;
    private RequestQueue queues;
    private ArrayList<Goods.DataBean> data;
//    private ArrayList<MyGrid> myGrids;

    //    private Type type;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.listview_item, container, false);
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        queues = Volley.newRequestQueue(getActivity());
        initView(view);
        Global app = (Global) getActivity().getApplication();
        datas = app.getDatas();


        //得到数据
        str = getArguments().getString(TAG);


        mTvCategory.setText(str);

        try {
            initData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new GridAdapter();
        mItemGridView.setAdapter(adapter);

    }

    /**
     * 获取具体分类内容
     * @throws JSONException
     */
    private void initData() throws JSONException {
        JSONObject key = datas.getJSONObject(str);
        Iterator<String> k = key.keys();
        MyGrid myGrid = null;
        //遍历Json字符串
        while (k.hasNext()) {
            String ks = k.next();
            JSONObject values = key.getJSONObject(ks);
            String url = values.getString("img");
            myGrid = new MyGrid(ks, url);
            list.add(myGrid);

        }
    }

    private void initView(View view) {
        mTvCategory = (TextView) view.findViewById(R.id.tv_category);
        mItemGridView = (GridView) view.findViewById(R.id.item_gridView1);

        mItemGridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        Toast.makeText(getActivity(), list.get(i).getiId(), Toast.LENGTH_SHORT).show();
        HttpUtils httpUtils = new HttpUtils();

        URLEncode urlEncode = new URLEncode(AppUrl.search);
        urlEncode.add("field2", list.get(i).getiId());
        RequestParams params = new RequestParams();

        String token = new GetInfo().getToken(getActivity());
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
//                                Toast.makeText(SearchActivity.this, "成功", Toast.LENGTH_SHORT).show();
                        Goods.DataBean datas;
                        for (int i = 0; i < info.getData().size(); i++) {
                            int id = info.getData().get(i).getId();
                            String title = info.getData().get(i).getTitle();
                            int price = info.getData().get(i).getPrice();
                            int count = info.getData().get(i).getCount();
                            String picture = info.getData().get(i).getPicture();
                            datas = new Goods.DataBean(id, title, count, price, picture);
                            data.add(datas);
                        }
//                        Log.i("xx", "onSuccess: " + data.get(0).getTitle());

                        Intent intent = new Intent(getActivity(), GoodsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", data);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    default:
                        Toast.makeText(getActivity(), info.getMsg(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final ViewHolder viewHolder;
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.grid_item, null);
                viewHolder = new ViewHolder();
                viewHolder.tv = (TextView) view.findViewById(R.id.text);
                viewHolder.iv = (ImageView) view.findViewById(R.id.img);

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.tv.setText(list.get(i).getiId());

            ImageRequest imageRequest = new ImageRequest(
                    list.get(i).getiName(),
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            viewHolder.iv.setImageBitmap(response);
                        }
                    }, 100, 100,
                    ImageView.ScaleType.FIT_XY,
                    Bitmap.Config.ARGB_8888,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            viewHolder.iv.setImageResource(R.mipmap.ic_launcher);
                        }
                    });
            queues.add(imageRequest);

            return view;
        }

        class ViewHolder {
            ImageView iv;
            TextView tv;
        }
    }

}
