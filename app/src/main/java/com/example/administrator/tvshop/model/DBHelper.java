package com.example.administrator.tvshop.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.administrator.tvshop.bean.Goods;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017.5.8.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "shopGoods7.db", null, 1);

    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("xx", "oncreate is running ..... ");
        // 通常在此处进行创建表的操作
        /**
         * 通过execSQL方法执行参数中指定的sql语句 创建表的sql语句： create table if not exists 表名
         */
        sqLiteDatabase.execSQL("create table if not exists goods (_id integer primary key autoincrement, uid text ,name text, price integer,number integer, picture text)");
    }

    /**
     * 当数据库的版本号发生改变时会自动调用的方法
     *
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //增加数据
    public void addBySql(Goods.DataBean shopCard, String uid) {
        SQLiteDatabase db = getReadableDatabase();

		/*
         * 增加的sql语句
		 *
		 * execSQL方法的参数2：使用数组中的对应数据替代sql语句中？的值
		 * */
//        Global context = Global.get();
        db.execSQL("replace into goods(_id, uid, name,price,number,picture) values (?,?,?,?,?,?)", new Object[]{shopCard.getId(), uid, shopCard.getTitle(), shopCard.getPrice(), shopCard.getNum(), shopCard.getPicture()});

        //关闭数据库
        db.close();

    }

    //查询全表数据
    public ArrayList<Goods.DataBean> getAllData(String uid) {

        SQLiteDatabase db = getReadableDatabase();

        /**
         * 为了获取到查询结果，因此使用带有返回值的rawQuery方法执行sql语句
         * 方法参数：
         * 1. 要执行的sql语句
         * 2.  String[] ,如果sql语句中有？，那么用于替代？的值
         *
         * 查询的sql语句
         *
         */

        /**
         * 返回值为Cursor对象， 游标
         *  特点。：
         *  作用类似于一个指针小箭头，当得到查询结果后，Cursor默认指向结果数据的第一行上方
         *  必须一行一行挪动Cursor对象，当Cursor对象指向某一行数据后，该行内的所有数据就会存储在Cursor对象中
         */
        Cursor cursor = db.rawQuery("select * from `goods` where `uid` = " + "'" + uid +"'", null);

        ArrayList<Goods.DataBean> list = new ArrayList<Goods.DataBean>();
        //通过moveToNext方法让Cursor，向下挪动一行
        while (cursor.moveToNext()) {
            //挪动后，Cursor内拥有该行的所有数据
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
//            String uid = cursor.getString(cursor.getColumnIndex("uid"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int price = cursor.getInt(cursor.getColumnIndex("price"));
            int number = cursor.getInt(cursor.getColumnIndex("number"));
            String picture = cursor.getString(cursor.getColumnIndex("picture"));

            list.add(new Goods.DataBean(id, uid, name, price, number, picture));
        }

        db.close();
        return list;
    }

    public ArrayList<Goods.DataBean> getAllData() {

        SQLiteDatabase db = getReadableDatabase();

        /**
         * 为了获取到查询结果，因此使用带有返回值的rawQuery方法执行sql语句
         * 方法参数：
         * 1. 要执行的sql语句
         * 2.  String[] ,如果sql语句中有？，那么用于替代？的值
         *
         * 查询的sql语句
         *
         */

        /**
         * 返回值为Cursor对象， 游标
         *  特点。：
         *  作用类似于一个指针小箭头，当得到查询结果后，Cursor默认指向结果数据的第一行上方
         *  必须一行一行挪动Cursor对象，当Cursor对象指向某一行数据后，该行内的所有数据就会存储在Cursor对象中
         */
        Cursor cursor = db.rawQuery("select * from goods", null);

        ArrayList<Goods.DataBean> list = new ArrayList<Goods.DataBean>();
        //通过moveToNext方法让Cursor，向下挪动一行
        while (cursor.moveToNext()) {
            //挪动后，Cursor内拥有该行的所有数据
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
//            String uid = cursor.getString(cursor.getColumnIndex("uid"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int price = cursor.getInt(cursor.getColumnIndex("price"));
            int number = cursor.getInt(cursor.getColumnIndex("number"));
            String picture = cursor.getString(cursor.getColumnIndex("picture"));

            list.add(new Goods.DataBean(id, name, price, number, picture));
        }

        db.close();
        return list;
    }

    //删除数据
    public void deleteBySql(int id, String uid) {
        SQLiteDatabase db = getReadableDatabase();

        //删除表中所有数据
//		 db.execSQL("delete from stu");

        //删除指定数据
        db.execSQL("delete from goods where _id = " + id + " and `uid` = " + "'" + uid + "'");
        db.close();
    }

    public void deleteByUid(String uid) {
        SQLiteDatabase db = getReadableDatabase();

        //删除表中所有数据
//		 db.execSQL("delete from stu");

        //删除指定数据
        db.execSQL("delete from goods where 'uid' = " + "'" + uid + "'");
        db.close();
    }

//    //修改数据
//    public void updateBySql(ShopCard shopCard, int id) {
//        SQLiteDatabase db = getReadableDatabase();
//
//        db.execSQL("update goods set name = ?,price =?,number=? where _id = " + id,
//                new Object[]{shopCard.getGoodsName(), shopCard.getGoodsPrice(), shopCard.getGoodsPrice()});
//
//    }
}
