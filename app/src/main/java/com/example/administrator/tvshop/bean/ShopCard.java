package com.example.administrator.tvshop.bean;

/**
 * Created by Administrator on 2017.4.17.
 */

public class ShopCard{
    private int goodsId;//id
    private String goodsName; //商品名称
    private String goodsIcon; //商品图标
    private int goodsPrice; //商品价格
    private int goodsNum;//购物车中数量

    public ShopCard(){}

    public ShopCard(String goodsName, int goodsPrice, int goodsNum) {
//        this.goodsId = goodsId;
        this.goodsName = goodsName;
//        this.goodsIcon = goodsIcon;
        this.goodsPrice = goodsPrice;
        this.goodsNum = goodsNum;
    }
    public ShopCard(int goodsId, String goodsName, int goodsPrice, int goodsNum) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
//        this.goodsIcon = goodsIcon;
        this.goodsPrice = goodsPrice;
        this.goodsNum = goodsNum;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
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

    public int getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(int goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    @Override
    public String toString() {
        return "ShopCard{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsIcon='" + goodsIcon + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsNum=" + goodsNum +
                '}';
    }
}
