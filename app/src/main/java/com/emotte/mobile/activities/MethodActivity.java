package com.emotte.mobile.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.emotte.mobile.R;
import com.emotte.mobile.base.BaseActivity;
import com.emotte.mobile.http.HttpUtils;
import com.emotte.mobile.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class MethodActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(com.emotte.mobile.R.id.gridView)
    GridView gridView;

    private String[] methods = {"GET",
            "POST", "PUT\n用法同POST主要用于创建资源"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_method);

        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setTitle("请求方法演示");
        gridView.setAdapter(new MyAdapter());
        gridView.setOnItemClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                TreeMap map = new TreeMap();
//                map.put("oauthCode", OauthCod);
//                map.put("likeCode", likeCode);
//                map.put("targetId", targetId);

                HttpUtils.testget(map, new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ToastUtil.showShortToast(s.toString());
                    }
                });
                break;
            case 1:
                TreeMap map1 = new TreeMap();
                HttpUtils.testpost(map1, new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ToastUtil.showShortToast(s.toString());
                    }
                });
                break;
            case 2:
                break;

        }
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return methods.length;
        }

        @Override
        public String getItem(int position) {
            return methods[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new TextView(getApplicationContext());
            }
            TextView textView = (TextView) convertView;
            textView.setGravity(Gravity.CENTER);
            textView.setHeight(200);
            textView.setText(getItem(position));
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(16);

            return textView;
        }
    }
}