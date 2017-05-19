package com.example.administrator.tvshop.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017.5.13.
 */

public class URLEncode {

    String url;
    private String ENCODE = "UTF-8";

    public URLEncode(String inputUrl) {
        url = inputUrl + "?";
    }

    public void add(String a, String b) {
        try {
            a = URLEncoder.encode(a, ENCODE);
            b = URLEncoder.encode(b, ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        url += a + "=" + b + "&";
    }

    public void add(int a, int b) {
//        try {
////            a = URLEncoder.encode(a, ENCODE);
////            b = URLEncoder.encode(b, ENCODE);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        url += a + "=" + b + "&";
    }

    public void add(String a, int b) {
        try {
            a = URLEncoder.encode(a, ENCODE);
//            b = URLEncoder.encode(b, ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        url += a + "=" + b + "&";
    }

    public String out() {
        return url;
    }

    public static String encode(String url) {

        try {

            Matcher matcher = Pattern.compile("[\\u4e00-\\u9fa5]").matcher(url);
            int count = 0;
            while (matcher.find()) {
                //System.out.println(matcher.group());
                String tmp = matcher.group();
                url = url.replaceAll(tmp, java.net.URLEncoder.encode(tmp, "utf-8"));
            }
            // System.out.println(count);
            //url = java.net.URLEncoder.encode(url,"gbk");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return url;
    }
}
