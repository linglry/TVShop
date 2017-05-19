package com.example.administrator.tvshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.tvshop.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017.4.1.
 */

public class CateLVAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> strings = new ArrayList<>();
    private static int mPostition;

    public CateLVAdapter(Context context, ArrayList<String> strings){
        this.context =context;
        this.strings = strings;
    }
    @Override
    public int getCount() {
        if(strings.size() != 0){
            return strings.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return strings.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.listview_item, null);
        TextView tv = (TextView)view.findViewById(R.id.tv_category);
        mPostition = i;
        tv.setText(strings.get(i));
        return view;
    }
}
