package com.example.administrator.tvshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.administrator.tvshop.R;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mLayoutShopOrder;
    private LinearLayout mLayoutShopGoods;
    private LinearLayout mLayoutShopNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shop);
        initView();
    }

    private void initView() {
        mLayoutShopOrder = (LinearLayout) findViewById(R.id.layout_shop_order);
        mLayoutShopOrder.setOnClickListener(this);
        mLayoutShopGoods = (LinearLayout) findViewById(R.id.layout_shop_goods);
        mLayoutShopGoods.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_shop_order:
                startActivity(new Intent(this, ShopOrderActivity.class));
                break;
            case R.id.layout_shop_goods:
                startActivity(new Intent(this, ShopGoodsActivity.class));
                break;
        }

    }
}
