package com.example.administrator.tvshop.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017.5.14.
 */

public class Goods implements Serializable {
    private int code;
    private String token;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    private List<DataBean> data;

    public static class DataBean implements Serializable{
        private int id;

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        private int gid;
        private String title;
        private int count;
        private int price;
        private int sprice;
        private String content;
        private String picture;
        private String uid;
        private String field1;
        private String field2;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        private int num;

        public DataBean(int id, String title, int count, int price, String picture) {
            this.id = id;
            this.title = title;
            this.count = count;
            this.price = price;
            this.picture = picture;
        }
        public DataBean(int id, String title, int count, int price, String picture, String field1, String field2) {
            this.id = id;
            this.title = title;
            this.count = count;
            this.price = price;
            this.picture = picture;
            this.field1 = field1;
            this.field2 = field2;

        }

        public DataBean(int id, String uid,String title, int price, int num, String picture) {
            this.id = id;
            this.uid = uid;
            this.title = title;
            this.num = num;
            this.price = price;
            this.picture = picture;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getSprice() {
            return sprice;
        }

        public void setSprice(int sprice) {
            this.sprice = sprice;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getField1() {
            return field1;
        }

        public void setField1(String field1) {
            this.field1 = field1;
        }

        public String getField2() {
            return field2;
        }

        public void setField2(String field2) {
            this.field2 = field2;
        }
    }
}
