package com.example.administrator.tvshop.bean;

/**
 * Created by Administrator on 2017.5.5.
 */

public class MyGrid {
    private String iId;
    private String iName;

    public MyGrid() {
    }

    public MyGrid(String iId, String iName) {
        this.iId = iId;
        this.iName = iName;
    }

    public String getiId() {
        return iId;
    }

    public String getiName() {
        return iName;
    }

    public void setiId(String iId) {
        this.iId = iId;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }
}
