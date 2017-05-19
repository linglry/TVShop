package com.example.administrator.tvshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.tvshop.R;
import com.example.administrator.tvshop.activity.DetailActivity;
import com.example.administrator.tvshop.activity.SearchActivity;
import com.example.administrator.tvshop.bean.Goods;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017.3.18.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    private View layout;
    private TextView mTvSearch;
    private ImageView mImageView3;
    private RelativeLayout mLayoutHomeSearch;
    private RelativeLayout mLayoutHomeTopbar;
    private TextView mTvDiscount;
    private TextView mTvDiscountTitle;
    private TextView mTvDiscountGoodPercent;
    private ImageView mImgDiscountGood;
    private TextView mTvDiscountGood;
    private TextView mTvDiscountGoodPrice;
    private RelativeLayout mRelativeLayout1;
    private TextView mTvHour;
    private TextView mTvTimerDivider;
    private TextView mTvMinute;
    private TextView mTvTimerDivider2;
    private TextView mTvSecond;
    private RelativeLayout mLayoutTimer;
    private TextView mTvDiscountSubtitle;
    private ImageView mImageView1;
    private RelativeLayout mLayoutDiscount;
    private ImageView mImgDiscountGood2;
    private TextView mTvDiscountTitle2;
    private TextView mTvDiscountGood2;
    private RelativeLayout mLayoutDiscountPhone;
    private View mDiscountDivider;
    private View mDiscountDivider2;
    private ImageView mImgDiscountBanner;
    private View mDiscountDivider3;
    private TextView mTvRecom;
    private ImageView mImageView03;
    private ImageView mImageView4;
    private TextView mTvRecomTitle;
    private TextView mTvRecomSubtitle;
    private RelativeLayout mLayoutReco;
    private ImageView mImageView02;
    private TextView mTvRecomSubtitle2;
    private ImageView mImageView5;
    private TextView mTvRecomTitle2;
    private RelativeLayout mLayoutRecom2;
    private View mRecomDevider;
    private View mRecomDevider2;
    private TextView mTvSpecial;
    private TextView mTvSpecialTitle1;
    private RelativeLayout mLayoutSpecial;
    private TextView mTvSpecialTitle2;
    private RelativeLayout mLayoutSpecial2;
    private View mSpecialDevider2;

    private List<Goods.DataBean> goodsInfo = new ArrayList<>();
    private RelativeLayout mLayoutHotReco;
    private RelativeLayout mLayoutHotGoods;
    private RelativeLayout mLayoutHomeDiscount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_home, container, false);

        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initGoodsInfo();

    }

    private void initGoodsInfo() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
//        params.addHeader("token", "KkFTcsYCtyEesKh1");

//        httpUtils.send(HttpRequest.HttpMethod.POST, "http://192.168.137.1:8080/zfj/2013144232/slh/goods.php",
//                params, new RequestCallBack<String>() {
//                    @Override
//                    public void onFailure(HttpException e, String s) {
//                        Log.i("xx", "onFailure: ");
//                    }
//
//                    @Override
//                    public void onSuccess(ResponseInfo<String> responseInfo) {
//                        String result = responseInfo.result;
//                        Log.i("xx", "login: " + result);
//
//                        Gson gson = new Gson();
//                        Goods info = gson.fromJson(result, Goods.class);
//                        Log.i("xx", "onSuccess: " + info.getStatus());
//                        switch (info.getStatus()) {
//                            case UserConstants.CONNECT_SUCCESS:
//                                goodsInfo = info.getArr();
////                                Toast.makeText(getActivity(), "成功" + uid + psw, Toast.LENGTH_SHORT).show();
//
//                                break;
//                            case UserConstants.CONNECT_FILED:
////                                Log.i("xx", "失败: " + uid + " " + psw);
//
//                                break;
//                        }
//                    }
//                });
    }


    private void initView() {

        mTvSearch = (TextView) layout.findViewById(R.id.tv_search);
        mImageView3 = (ImageView) layout.findViewById(R.id.imageView3);
        mLayoutHomeSearch = (RelativeLayout) layout.findViewById(R.id.layout_home_search);
        mLayoutHomeTopbar = (RelativeLayout) layout.findViewById(R.id.layout_home_topbar);
        mTvDiscount = (TextView) layout.findViewById(R.id.tv_discount);
        mImgDiscountGood = (ImageView) layout.findViewById(R.id.img_discount_good);
        mTvDiscountGood = (TextView) layout.findViewById(R.id.tv_discount_good);
        mTvDiscountGoodPrice = (TextView) layout.findViewById(R.id.tv_discount_good_price);
        mRelativeLayout1 = (RelativeLayout) layout.findViewById(R.id.RelativeLayout1);
//        mTvTimerDivider = (TextView) layout.findViewById(R.id.tv_timer_divider);
        mLayoutTimer = (RelativeLayout) layout.findViewById(R.id.layout_timer);
        mTvDiscountSubtitle = (TextView) layout.findViewById(R.id.tv_discount_subtitle);
        mImageView1 = (ImageView) layout.findViewById(R.id.imageView1);
        mLayoutDiscount = (RelativeLayout) layout.findViewById(R.id.layout_discount);
        mTvDiscountGood2 = (TextView) layout.findViewById(R.id.tv_discount_good2);
        mLayoutDiscountPhone = (RelativeLayout) layout.findViewById(R.id.layout_discount_phone);
        mDiscountDivider = (View) layout.findViewById(R.id.discount_divider);
        mTvRecom = (TextView) layout.findViewById(R.id.tv_recom);
        mImageView03 = (ImageView) layout.findViewById(R.id.ImageView03);
        mImageView4 = (ImageView) layout.findViewById(R.id.imageView4);
        mTvRecomTitle = (TextView) layout.findViewById(R.id.tv_recom_title);
        mTvRecomSubtitle = (TextView) layout.findViewById(R.id.tv_recom_subtitle);
        mLayoutReco = (RelativeLayout) layout.findViewById(R.id.layout_reco);
        mLayoutReco.setOnClickListener(this);

        mImageView02 = (ImageView) layout.findViewById(R.id.ImageView02);
        mImageView5 = (ImageView) layout.findViewById(R.id.imageView5);
        mTvRecomTitle2 = (TextView) layout.findViewById(R.id.tv_recom_title2);
        mLayoutRecom2 = (RelativeLayout) layout.findViewById(R.id.layout_recom2);
        mRecomDevider = (View) layout.findViewById(R.id.recom_devider);
        mRecomDevider2 = (View) layout.findViewById(R.id.recom_devider2);
        mTvSpecial = (TextView) layout.findViewById(R.id.tv_special);
        mTvSpecialTitle1 = (TextView) layout.findViewById(R.id.tv_special_title1);
        mLayoutSpecial = (RelativeLayout) layout.findViewById(R.id.layout_special);
        mLayoutSpecial.setOnClickListener(this);
        mTvSpecialTitle2 = (TextView) layout.findViewById(R.id.tv_special_title2);
        mTvSpecialTitle2.setOnClickListener(this);
        mLayoutSpecial2 = (RelativeLayout) layout.findViewById(R.id.layout_special2);
        mLayoutSpecial2.setOnClickListener(this);
        mSpecialDevider2 = (View) layout.findViewById(R.id.special_devider2);

        mLayoutHomeDiscount = (RelativeLayout) layout.findViewById(R.id.layout_home_discount);
        mLayoutHomeDiscount.setOnClickListener(this);
        mLayoutHotReco = (RelativeLayout) layout.findViewById(R.id.layout_hot_reco);
        mLayoutHotReco.setOnClickListener(this);
        mLayoutRecom2 = (RelativeLayout) layout.findViewById(R.id.layout_recom2);
        mLayoutRecom2.setOnClickListener(this);
        mLayoutHotGoods = (RelativeLayout) layout.findViewById(R.id.layout_hot_goods);
        mLayoutHotGoods.setOnClickListener(this);
        mTvSearch.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_home_discount:
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("id", 4);
                startActivity(intent);
                break;
            case R.id.layout_reco:
                Intent intent2 = new Intent(getActivity(), DetailActivity.class);
                intent2.putExtra("id", 10);
                startActivity(intent2);
                break;
            case R.id.layout_recom2:
                Intent intent7 = new Intent(getActivity(), DetailActivity.class);
                intent7.putExtra("id", 5);
                startActivity(intent7);
                break;
            case R.id.layout_special:
                Intent intent3 = new Intent(getActivity(), DetailActivity.class);
                intent3.putExtra("id", 3);
                startActivity(intent3);
                break;
            case R.id.layout_special2:
                Intent intent4 = new Intent(getActivity(), DetailActivity.class);
                intent4.putExtra("id", 8);
                startActivity(intent4);
                break;
            case R.id.tv_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
        }

    }

    private void gotoDetail(int index) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("goodsinfo_to_detail", goodsInfo.toString());
        startActivity(intent);
    }
}
