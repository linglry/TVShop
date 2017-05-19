package com.example.administrator.tvshop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.tvshop.R;
import com.example.administrator.tvshop.bean.MyGrid;
import com.example.administrator.tvshop.utils.Global;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017.3.29.
 */

public class CategoryFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View layout;
    private String str;
    public static final String TAG = "xx";
    private FragmentManager manager;
    private CateLVAdapter adapter;
    private ListView mListview;


//    private String[] strs = {};
    public static int mPosition;
    private ListChildFragment listFragment;
    private BaseAdapter mGridAdaptet;
    private ArrayList<MyGrid> myGrids = new ArrayList<>();
    private GridView gv;

    private ArrayList<String> strs = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_category, container, false);

        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        manager = getFragmentManager();
        initView(view);
        Global app = (Global)getActivity().getApplication();
        strs = app.getStrs();
//        getdata();

        initAdapter();
    }

    private void getdata() {
//        Log.i(TAG, "size "+ strs.size());
        for (int i = 0; i <strs.size() ; i++) {
            Log.i(TAG, "getdata: "+ strs.get(i));
        }
    }


    private void initAdapter() {
        adapter = new CateLVAdapter();
        mListview.setAdapter(adapter);
    }


    private void initView(View view) {
        mListview = (ListView) view.findViewById(R.id.listview);
        mListview.setOnItemClickListener(this);
//        gv = (GridView) view.findViewById(R.id.item_gridView1);

    }

    /**
     * 左边部分列表点击事件
     * @param adapterView
     * @param view
     * @param position 位置
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        //拿到当前位置
        mPosition = position;
        //即使刷新adapter
        adapter.notifyDataSetChanged();
        for (int i = 0; i < strs.size(); i++) {
            listFragment = new ListChildFragment();
            FragmentTransaction fragmentTransaction = getChildFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, listFragment);
            Bundle bundle = new Bundle();
            bundle.putString(ListChildFragment.TAG, strs.get(position));
            listFragment.setArguments(bundle);
            fragmentTransaction.commit();
        }
    }



    class CateLVAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return strs.size();
        }

        @Override
        public Object getItem(int i) {
            return strs.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(getActivity()).inflate(R.layout.listview_item, null);
            TextView tv = (TextView)view.findViewById(R.id.tv_category);
//            mPostition = i;
            tv.setText(strs.get(i));
            return view;
        }
    }


}
