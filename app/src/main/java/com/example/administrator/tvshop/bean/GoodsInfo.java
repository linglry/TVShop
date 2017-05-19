package com.example.administrator.tvshop.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017.4.17.
 */

public class GoodsInfo implements Serializable{
    private String goodsId;//id
    private String goodsName; //商品名称
    private String goodsIcon; //商品图标
    private String goodsPrice; //商品价格
    private int goodsNum;//购物车中数量

//    public GoodsInfo(String goodsId, String goodsName, String goodsIcon, String goodsPrice, int goodsNum) {
//        this.goodsId = goodsId;
//        this.goodsName = goodsName;
//        this.goodsIcon = goodsIcon;
//        this.goodsPrice = goodsPrice;
//        this.goodsNum = goodsNum;
//    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsIcon() {
        return goodsIcon;
    }

    public void setGoodsIcon(String goodsIcon) {
        this.goodsIcon = goodsIcon;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }
}
