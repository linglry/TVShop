package com.example.administrator.tvshop.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017.5.4.
 */

public class TypeBean {

    /**
     * status : 0
     * arr : [{"0":"3","1":"衣","2":"男装","id":"3","cate_first":"衣","cate_second":"男装"},{"0":"4","1":"衣","2":"女装","id":"4","cate_first":"衣","cate_second":"女装"},{"0":"5","1":"衣","2":"童装","id":"5","cate_first":"衣","cate_second":"童装"},{"0":"6","1":"衣","2":"女鞋","id":"6","cate_first":"衣","cate_second":"女鞋"},{"0":"7","1":"衣","2":"凉鞋女","id":"7","cate_first":"衣","cate_second":"凉鞋女"},{"0":"8","1":"衣","2":"皮鞋男","id":"8","cate_first":"衣","cate_second":"皮鞋男"},{"0":"9","1":"衣","2":"西裤男","id":"9","cate_first":"衣","cate_second":"西裤男"},{"0":"10","1":"衣","2":"松糕鞋","id":"10","cate_first":"衣","cate_second":"松糕鞋"},{"0":"11","1":"食","2":"大闸蟹","id":"11","cate_first":"食","cate_second":"大闸蟹"},{"0":"12","1":"食","2":"家常菜","id":"12","cate_first":"食","cate_second":"家常菜"},{"0":"13","1":"住","2":"酒店","id":"13","cate_first":"住","cate_second":"酒店"},{"0":"14","1":"住","2":"旅馆","id":"14","cate_first":"住","cate_second":"旅馆"},{"0":"15","1":"行","2":"景点","id":"15","cate_first":"行","cate_second":"景点"},{"0":"16","1":"住","2":"旅行社","id":"16","cate_first":"住","cate_second":"旅行社"},{"0":"21","1":"娱乐","2":"KTV","id":"21","cate_first":"娱乐","cate_second":"KTV"}]
     */

    private String status;
    /**
     * 0 : 3
     * 1 : 衣
     * 2 : 男装
     * id : 3
     * cate_first : 衣
     * cate_second : 男装
     */

    private List<ArrBean> arr;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ArrBean> getArr() {
        return arr;
    }

    public void setArr(List<ArrBean> arr) {
        this.arr = arr;
    }

    public static class ArrBean {
        @SerializedName("0")
        private String value0;
        @SerializedName("1")
        private String value1;
        @SerializedName("2")
        private String value2;
        private String id;
        private String cate_first;
        private String cate_second;

        public String getValue0() {
            return value0;
        }

        public void setValue0(String value0) {
            this.value0 = value0;
        }

        public String getValue1() {
            return value1;
        }

        public void setValue1(String value1) {
            this.value1 = value1;
        }

        public String getValue2() {
            return value2;
        }

        public void setValue2(String value2) {
            this.value2 = value2;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCate_first() {
            return cate_first;
        }

        public void setCate_first(String cate_first) {
            this.cate_first = cate_first;
        }

        public String getCate_second() {
            return cate_second;
        }

        public void setCate_second(String cate_second) {
            this.cate_second = cate_second;
        }
    }
}
