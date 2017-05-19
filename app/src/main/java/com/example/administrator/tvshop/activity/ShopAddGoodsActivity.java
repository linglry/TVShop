package com.example.administrator.tvshop.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.tvshop.R;
import com.example.administrator.tvshop.UserConstants;
import com.example.administrator.tvshop.utils.AppUrl;
import com.example.administrator.tvshop.utils.GetInfo;
import com.example.administrator.tvshop.utils.URLEncode;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class ShopAddGoodsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImage;
    private static final int IMAGE = 1;
    private Button mBtCommit;
    private EditText mEtName;
    private EditText mEtPrice1;
    private EditText mEtPrice2;
    private EditText mEtDetail;
    private EditText mEtField1;
    private EditText mEtField2;
    private EditText mEtCount;
    private int id;
    private String pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_goods);
        initView();

    }


    private void initView() {
        mImage = (ImageView) findViewById(R.id.image);
        mImage.setOnClickListener(this);
        mBtCommit = (Button) findViewById(R.id.bt_commit);
        mBtCommit.setOnClickListener(this);

        mEtName = (EditText) findViewById(R.id.et_nickname);
        mEtCount = (EditText) findViewById(R.id.et_count);
        mEtPrice1 = (EditText) findViewById(R.id.et_price1);
        mEtPrice2 = (EditText) findViewById(R.id.et_price2);
        mEtDetail = (EditText) findViewById(R.id.et_detail);
        mEtField1 = (EditText) findViewById(R.id.et_field1);
        mEtField2 = (EditText) findViewById(R.id.et_field2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image:
                //调用相册
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE);
                break;
            case R.id.bt_commit://发布商品
                addGoods();
//                addPic();
                break;
        }
    }

    private void addPic(int id) {
        HttpUtils httpUtils = new HttpUtils();
        URLEncode urlEncode = new URLEncode(AppUrl.addPic);
        urlEncode.add("id", id);
//        urlEncode.add("pic", pic);
        Log.i("xx", "addPic: "+ urlEncode.out());
        RequestParams params = new RequestParams();
        params.addBodyParameter("pic", pic);

        String token = new GetInfo().getToken(this);
        params.addHeader("token", token);
        httpUtils.configCurrentHttpCacheExpiry(0);
        httpUtils.send(HttpRequest.HttpMethod.POST, urlEncode.out(), params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.i("xx", "pic: " + responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private void addGoods() {
        String name = mEtName.getText().toString();
        String count = mEtCount.getText().toString();
        String price1 = mEtPrice1.getText().toString();
        String price2 = mEtPrice2.getText().toString();
        String detail = mEtDetail.getText().toString();
        String field1 = mEtField1.getText().toString();
        String field2 = mEtField2.getText().toString();

        HttpUtils httpUtils = new HttpUtils();

        URLEncode urlEncode = new URLEncode(AppUrl.addgoods);
        urlEncode.add("title", name);
        urlEncode.add("count", count);
        urlEncode.add("price", price1);
        urlEncode.add("sprice", price2);
        urlEncode.add("content", detail);
        urlEncode.add("field1", field1);
        urlEncode.add("field2", field2);
        RequestParams params = new RequestParams();

        String token = new GetInfo().getToken(this);
        params.addHeader("token", token);
        httpUtils.configCurrentHttpCacheExpiry(0);
        httpUtils.send(HttpRequest.HttpMethod.GET, urlEncode.out(), params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Log.i("xx", "onSuccess: "+ result);
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt("code");
                    id = json.getInt("data");
                    addPic(id);
                    Log.i("xx", "onSuccess: "+ id);
                    switch (code){
                        case UserConstants.LOGIN_SUCCESS:
                            Toast.makeText(ShopAddGoodsActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(ShopAddGoodsActivity.this, json.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();
        }
    }

    //加载图片
    private void showImage(String imaePath) {
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        mImage.setImageBitmap(bm);
        pic = Bitmap2StrByBase64(bm);
    }

    /**
     * 通过Base32将Bitmap转换成Base64字符串
     * @param bit
     * @return
     */
    public String Bitmap2StrByBase64(Bitmap bit){
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 100, bos);//参数100表示不压缩
        byte[] bytes=bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }


}
