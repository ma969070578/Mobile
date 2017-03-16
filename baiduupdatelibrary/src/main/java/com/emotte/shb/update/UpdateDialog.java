package com.emotte.shb.update;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Administrator on 2016/8/16.
 */
public class UpdateDialog extends Dialog {

    private ImageView iv_dialog_update_close;//关闭按钮
    private TextView tv_dialog_update_dos;//更新内容
    private TextView btn_dialog_update;//立即升级

    private int forceupdate = 0; // 0可取消、1 强制升级 该功能暂未实现

    public UpdateDialog(Context context) {
        super(context);
    }

    public UpdateDialog(Context context, int theme, int forceupdate) {
        super(context, theme);
        this.forceupdate = forceupdate;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update);
        initView();
    }

    private void initView() {
        iv_dialog_update_close = (ImageView) findViewById(R.id.iv_dialog_update_close);
        tv_dialog_update_dos = (TextView) findViewById(R.id.tv_dialog_update_dos);
        btn_dialog_update = (TextView) findViewById(R.id.btn_dialog_update);
        if (forceupdate == 1) {// 1不强制  2强制
            iv_dialog_update_close.setVisibility(View.VISIBLE);
        } else {
            iv_dialog_update_close.setVisibility(View.GONE);
        }
    }


    /**
     * 立即更新点击事件
     */
    public void setUpdataClick(View.OnClickListener listener) {
        btn_dialog_update.setOnClickListener(listener);
    }

    /**
     * 取消更新点击事件
     */
    public void setCloseClick(View.OnClickListener listener) {
        iv_dialog_update_close.setOnClickListener(listener);
    }

    /**
     * 设置更新内容
     */
    public void setDos(String dos) {
        if (!TextUtils.isEmpty(dos))
            tv_dialog_update_dos.setText(dos);
    }

    public void show(Dialog dialog) {
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }

}
