package com.example.administrator.tvshop.utils;

/**
 * Created by Administrator on 2017.5.13.
 */

public class AppUrl {
//    private static int  host = 3000;
private static int  host = 4003;
    private static String url = "http://jp.pch18.cn:";
    public static String login = "http://jp.pch18.cn:" + host + "/account/login";
    public static String shopLogin = "http://jp.pch18.cn:" + host + "/account/Slogin";
    public static String reg = "http://jp.pch18.cn:" + host + "/account/reg";
    public static String getAddress = "http://jp.pch18.cn:" + host + "/account/getInfo";
    public static String setInfo = "http://jp.pch18.cn:" + host + "/account/setInfo";
    public static String updateAddress = "http://jp.pch18.cn:" + host + "/account/setInfo";
    public static String updatePasswprd = url + host + "/account/changePassword";
    public static String getFields = "http://jp.pch18.cn:" + host + "/goods/getFields";

    //收藏
    public static String addFav = url + host + "/collect/add";
    public static String getFav = url + host + "/collect/get";
    public static String delFav = url + host + "/collect/del";

    //搜索
    public static String search = url + host + "/goods/search";

    //商家部分
    public static String getMyGoods = url + host + "/goods/getMyGoods";
    public static String addgoods = "http://jp.pch18.cn:" + host + "/goods/add";
    public static String updateMyGoods = url + host + "/goods/rewrite";
    public static String delMyGoods = url + host + "/goods/del";
    public static String addPic = url + host + "/goods/addPic";

    //订单部分
    public static String setOrders = url + host + "/orders/submit";
    public static String getOrders = url + host + "/orders/Umy";
    public static String getSOrders = url + host + "/orders/Smy";
    public static String setFH = url + host + "/orders/setFH";
    public static String setSH = url + host + "/orders/setSH";


}

