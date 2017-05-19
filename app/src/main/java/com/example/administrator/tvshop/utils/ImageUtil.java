package com.example.administrator.tvshop.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2017.5.15.
 */

public class ImageUtil {
    private RequestQueue queue;

    public void VolleyImage(String url, Context context, final ImageView iv){
        queue = Volley.newRequestQueue(context);
        url = URLEncode.encode(url);
        Log.i("xx", "VolleyImage: "+ url);
        ImageRequest ir = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        iv.setImageBitmap(response);
                    }
                },
                0, 0,
                ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("xx", "onErrorResponse: ");
                    }
                }
        );
        queue.add(ir);
    }
}
