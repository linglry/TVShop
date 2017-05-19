package com.example.administrator.tvshop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.tvshop.adapter.CateLVAdapter;
import com.example.administrator.tvshop.fragment.CartFragment;
import com.example.administrator.tvshop.fragment.CategoryFragment;
import com.example.administrator.tvshop.fragment.HomeFragment;
import com.example.administrator.tvshop.fragment.MyInfoFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private RadioGroup mRg;
    private ArrayList<Fragment> frags = new ArrayList<>();

    private FragmentManager manager;
    private Fragment lastFragment;
    private String[] strs = { "常用分类", "服饰内衣", "鞋靴", "手机", "家用电器", "数码", "电脑办公",
            "个护化妆", "图书" };
    public static int mPosition;
    private CateLVAdapter adapter;
    private ListView listView;
    private CategoryFragment categoryFragment;

    private TextView toolsTextViews[];
    private String[] toolsList;
    private View[] views;
    private LayoutInflater inflater;
    private ViewPager shop_pager;
    private ShopAdapter shopAdapter;
    private int currentItem = 0;
    private boolean isLogined;
    private String uid;
    private MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //初始化
        manager = getSupportFragmentManager();
        shopAdapter = new ShopAdapter(getSupportFragmentManager());
        inflater =LayoutInflater.from(this);
        initFragments();

        initView();
//        showToolsView();



        //将第一个Fragment显示到屏幕上
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.fl, frags.get(0));
        ft.commit();

        lastFragment = frags.get(0);



        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = (RadioButton)findViewById(i);
                int tag = Integer.parseInt(rb.getTag().toString());
                if(!frags.get(tag).isAdded()){
                    manager.beginTransaction().add(R.id.fl, frags.get(tag)).commit();
                } else{
                    manager.beginTransaction().show(frags.get(tag)).commit();
                }

                manager.beginTransaction().hide(lastFragment).commit();
                lastFragment = frags.get(tag);

            }
        });


    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        // 注册广播接收者
//        receiver = new MyReceiver();
//        IntentFilter filter = new IntentFilter(
//                Constants.BROADCAST_FILTER.FILTER_CODE);
//        registerReceiver(receiver, filter);
//    }

    private void initFragments() {
        frags.add(new HomeFragment());
        frags.add(new CategoryFragment());
        frags.add(new CartFragment());
        frags.add(new MyInfoFragment());
    }


    private void initView() {
        mRg = (RadioGroup) findViewById(R.id.rg);
//        listView = (ListView)findViewById(R.id.lv_category);
    }

    /**
     * 由片段调用，设置登录信息
     *
     * @param isLogined
     * @param uid
     */
    public void setIsLogined(boolean isLogined, String uid) {
        this.isLogined = isLogined;
        this.uid = uid;
    }

    /**
     * 由片段调用，获取是否已登录
     *
     * @return
     */
    public boolean getLogined() {
        return isLogined;
    }
    public String getUid() {
        return uid;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //拿到当前位置
//        mPosition = i;
        //即使刷新adapter
//        adapter.notifyDataSetChanged();
//        for (int j = 0; j < strs.length; j++) {
//            categoryFragment = new CategoryFragment();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
//                    .beginTransaction();
//            fragmentTransaction.replace(R.id.fl_category, new CategoryFragment());
//            Bundle bundle = new Bundle();
//            bundle.putString(CategoryFragment.TAG, strs[j]);
//            categoryFragment.setArguments(bundle);
//            fragmentTransaction.commit();
//        }
    }

    private class ShopAdapter extends FragmentPagerAdapter {
        public ShopAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            Fragment fragment = new CategoryFragment();
            Bundle bundle = new Bundle();
            String str = toolsList[arg0];
            bundle.putString("typename", str);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return toolsList.length;
        }


    }
    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String extra = intent
                    .getStringExtra(Constants.BROADCAST_FILTER.EXTRA_CODE);
            if (extra.equals(Constants.INTENT_KEY.FROM_FAVOR)) {
//                    isFromFavor = true;
            } else if (extra.equals(Constants.INTENT_KEY.REFRESH_INCART)) {
//                    initInCartNum();
            } else if (extra.equals(Constants.INTENT_KEY.FROM_DETAIL)) {
//                    isFromDetail = true;
            } else if (extra.equals(Constants.INTENT_KEY.LOGOUT)) {
                isLogined = false;
            }
        }

    }
}
