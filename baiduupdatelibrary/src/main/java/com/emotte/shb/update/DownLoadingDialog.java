package com.emotte.shb.update;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * Created by chenxw on 2016/8/16.
 * 更新进度
 */
public class DownLoadingDialog extends Dialog {
    private ProgressBar pb_download_loading;
    private TextView tv_download_percent;

    public DownLoadingDialog(Context context) {
        super(context);
    }

    public DownLoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_download_update);
        initView();
    }

    private void initView() {
        pb_download_loading = (ProgressBar) findViewById(R.id.pb_download_loading);
        tv_download_percent = (TextView) findViewById(R.id.tv_download_percent);
    }

    public void show(DownLoadingDialog dialog) {
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }


    /**
     * 设置进度条
     */
    public void setLoading(final int percent) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                pb_download_loading.setProgress(percent);
                tv_download_percent.setText(percent + "/" + 100);
            }
        });
    }
}
