package com.emotte.mobile.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emotte.mobile.R;
import com.emotte.mobile.base.BaseActivity;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SpringViewActivity extends BaseActivity {


    @Bind(R.id.rlv_spring)
    RecyclerView mRlvSpring;
    @Bind(R.id.sv_spring)
    SpringView mSvSpring;


    private List<String> dataList;
    private SpringViewAdapter adapter;


    public static void start(Context context) {
        Intent starter = new Intent(context, SpringViewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spring_view);
        ButterKnife.bind(this);
        Logger.i("测试logger");
        initView();
    }

    @Override
    protected void initView() {
        super.initView();

        mSvSpring.setType(SpringView.Type.FOLLOW);
        mSvSpring.setHeader(new DefaultHeader(this));
        mSvSpring.setFooter(new DefaultFooter(this));
        mSvSpring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData(true);
                    }
                }, 2000);

            }

            @Override
            public void onLoadmore() {
                //加载更多
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData(false);
                    }
                }, 2000);

            }
        });
        dataList = new ArrayList<>();
        mRlvSpring.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SpringViewAdapter(dataList);
        mRlvSpring.setAdapter(adapter);
        initData(false);
    }


    /**
     * 模拟数据
     *
     * @param isRefresh 是否是刷新
     */
    private void initData(boolean isRefresh) {
        if (isRefresh) {
            dataList.clear();
        }
        for (int i = 0; i < 20; i++) {
            String s = "测试数据" + i;
            dataList.add(s);
        }
        adapter.notifyDataSetChanged();
        mSvSpring.onFinishFreshAndLoad();
    }

    private class SpringViewAdapter extends RecyclerView.Adapter<SpringViewAdapter
            .SpringViewHolder> {
        private List<String> list;

        public SpringViewAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public SpringViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(SpringViewActivity.this).inflate(R.layout
                    .item_spring_text, parent, false);
            return new SpringViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SpringViewHolder holder, int position) {
            holder.tv_item_spring.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            if (list.size() <= 0) {
                return 0;
            }
            return list.size();
        }

        class SpringViewHolder extends RecyclerView.ViewHolder {
            private TextView tv_item_spring;

            public SpringViewHolder(View itemView) {
                super(itemView);
                tv_item_spring = (TextView) itemView.findViewById(R.id.tv_item_spring);
            }
        }
    }


}
