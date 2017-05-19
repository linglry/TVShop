package com.example.administrator.tvshop.utils;

import android.app.Application;
import android.util.Log;

import com.example.administrator.tvshop.bean.MyGrid;
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
 * Created by Administrator on 2017.5.14.
 */

public class Global extends Application {

    private static Global baseContext;

    public static Global get(){
        return baseContext;
    }

    public JSONObject getDatas() {
        return datas;
    }

    public void setDatas(JSONObject datas) {
        this.datas = datas;
    }

    private JSONObject datas;


    public ArrayList<String> getStrs() {
        return strs;
    }

    public void setStrs(ArrayList<String> strs) {
        this.strs = strs;
    }

    private ArrayList<String> strs;

    public ArrayList<MyGrid> getMyGrids() {
        return myGrids;
    }

    public void setMyGrids(ArrayList<MyGrid> myGrids) {
        this.myGrids = myGrids;
    }

    private ArrayList<MyGrid> myGrids = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        baseContext = this;
        getType();
    }

    private void getType() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();

        String token = new GetInfo().getToken(this);
        params.addHeader("token", token);
        httpUtils.configCurrentHttpCacheExpiry(0);

        httpUtils.send(HttpRequest.HttpMethod.GET, AppUrl.getFields,
                params, new RequestCallBack<String>() {
                    @Override
                    public void onFailure(HttpException e, String s) {
                        Log.i("xx", "onFailure: ");
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;

                        try {
                            //将字符串转换成jsonObject对象
                            JSONObject json = new JSONObject(result);
                            String data = json.getString("data");
                            datas = json.getJSONObject("data");

                            //遍历出所有键值
                            Iterator<String> keys = datas.keys();
                            strs = new ArrayList<String>();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                strs.add(key);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}
