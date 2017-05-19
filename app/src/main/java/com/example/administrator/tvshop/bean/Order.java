package com.example.administrator.tvshop.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017.5.17.
 */

public class Order {

    /**
     * data : [{"id":4,"gid":4,"uid":1,"guid":6,"count":22,"price":440,"address":"九寨沟","time":"2017-05-16T15:06:48.000Z","status":0},{"id":3,"gid":3,"uid":1,"guid":3,"count":11,"price":550,"address":"九寨沟","time":"2017-05-16T15:06:48.000Z","status":0},{"id":2,"gid":4,"uid":1,"guid":6,"count":22,"price":440,"address":"九寨沟","time":"2017-05-16T14:57:16.000Z","status":0},{"id":1,"gid":3,"uid":1,"guid":3,"count":11,"price":550,"address":"九寨沟","time":"2017-05-16T14:57:16.000Z","status":2}]
     * code : 200
     */

    private int code;
    private String token;
    private String msg;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * id : 4
     * gid : 4
     * uid : 1
     * guid : 6
     * count : 22
     * price : 440
     * address : 九寨沟
     * time : 2017-05-16T15:06:48.000Z
     * status : 0
     */

    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private int gid;
        private int uid;
        private int guid;
        private int count;
        private int price;
        private String times;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        private String title;
        private String picture;

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public DataBean(int id, int gid, int uid, int guid, int count, int price, String address, String times, int status, String title, String picture) {
            this.id = id;
            this.gid = gid;
            this.uid = uid;
            this.guid = guid;
            this.count = count;
            this.price = price;
            this.address = address;
            this.times = times;
            this.status = status;
            this.title = title;
            this.picture = picture;
        }

        private String address;
        private Date time;
        private int status;

        public DataBean(int id, int gid, int uid, int guid, int count, int price, int status) {
            this.id = id;
            this.gid = gid;
            this.uid = uid;
            this.guid = guid;
            this.count = count;
            this.price = price;
            this.status = status;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getGuid() {
            return guid;
        }

        public void setGuid(int guid) {
            this.guid = guid;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
