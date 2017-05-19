package com.example.administrator.tvshop.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Administrator on 2017.5.13.
 */

public class GetInfo {
    private String token;
    private String uid;
    public String getToken(Context context){
        SharedPreferences sp = context.getSharedPreferences("info_account",
                Context.MODE_PRIVATE);
        token = sp.getString("token", "");
        return token;
//        uid = sp.getString("username", "");
    }

    public String getUid(Context context){
        SharedPreferences sp = context.getSharedPreferences("info_account",
                Context.MODE_PRIVATE);
        uid = sp.getString("username", "");
        return uid;
    }

}
