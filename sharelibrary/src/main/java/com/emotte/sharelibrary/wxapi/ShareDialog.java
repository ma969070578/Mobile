package com.emotte.sharelibrary.wxapi;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emotte.sharelibrary.R;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import onekeyshare.OnekeyShare;

/**
 * Created by Administrator on 2016/5/30.
 */
public class ShareDialog extends Dialog implements View.OnClickListener {
    private LinearLayout ll_share_wechatmoments, ll_share_wechat, ll_share_qq, ll_share_qzone,
            ll_share_sinaweibo;
    private Context mContext;
    private TextView tv_share_cancel;
    private String title;
    private String titleUrl;
    private String text;
    private String url;
    private String site;
    private String siteurl;
    private String imageUrl;


    public ShareDialog(Context context, String title, String titleUrl, String imageUrl, String
            text, String url, String site, String siteurl) {
        super(context);
        windowDeploy(0, 0);
        this.mContext = context;
        this.title = title;
        this.titleUrl = titleUrl;
        this.imageUrl = imageUrl;
        this.text = text;
        this.url = url;
        this.site = site;
        this.siteurl = siteurl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.share_bottom_view);
        initView();
    }

    private void initView() {
        ll_share_wechatmoments = (LinearLayout) findViewById(R.id.ll_share_wechatmoments);
        ll_share_wechat = (LinearLayout) findViewById(R.id.ll_share_wechat);
        ll_share_qq = (LinearLayout) findViewById(R.id.ll_share_qq);
        ll_share_qzone = (LinearLayout) findViewById(R.id.ll_share_qzone);
        ll_share_sinaweibo = (LinearLayout) findViewById(R.id.ll_share_sinaweibo);
        tv_share_cancel = (TextView) findViewById(R.id.tv_share_cancel);

        ll_share_sinaweibo.setOnClickListener(this);
        ll_share_qzone.setOnClickListener(this);
        ll_share_qq.setOnClickListener(this);
        ll_share_wechat.setOnClickListener(this);
        ll_share_wechatmoments.setOnClickListener(this);
        tv_share_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    //设置窗口显示
    public void windowDeploy(int x, int y) {
        Window window = getWindow(); //得到对话框
        window.setWindowAnimations(R.style.popwin_anim_bottomtotop); //设置窗口弹出动画
        window.setBackgroundDrawableResource(R.color.transparent); //设置对话框背景为透明
        WindowManager.LayoutParams wl = window.getAttributes();
        //根据x，y坐标设置窗口需要显示的位置
        wl.x = x; //x小于0左移，大于0右移
        wl.y = y; //y小于0上移，大于0下移
        window.setAttributes(wl);
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.ll_share_wechatmoments) {
            showShare(WechatMoments.NAME);

        } else if (i == R.id.ll_share_wechat) {
            showShare(Wechat.NAME);

        } else if (i == R.id.ll_share_qq) {
            showShare(QQ.NAME);

        } else if (i == R.id.ll_share_qzone) {
            showShare(QZone.NAME);

        } else if (i == R.id.ll_share_sinaweibo) {
            showShare(SinaWeibo.NAME);

        }
        dismiss();
    }


    private void showShare(String platform) {
        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(true);
        oks.setPlatform(platform);
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();
        if (!TextUtils.isEmpty(title))
            oks.setTitle(title);
        if (!TextUtils.isEmpty(titleUrl))
            oks.setTitleUrl(titleUrl);
        if (!TextUtils.isEmpty(text))
            oks.setText(text);
        if (!TextUtils.isEmpty(imageUrl))
            oks.setImageUrl(imageUrl);
        if (!TextUtils.isEmpty(url))
            oks.setUrl(url); //微信不绕过审核分享链接
        if (!TextUtils.isEmpty(site))
            oks.setSite(site);  //QZone分享完之后返回应用时提示框上显示的名称
        if (!TextUtils.isEmpty(siteurl))
            oks.setSiteUrl(siteurl);//QZone分享参数
        oks.setCallback(paListener);
        // 启动分享
        oks.show(mContext);

    }


    PlatformActionListener paListener = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            Toast.makeText(getContext(), "分享成功", Toast.LENGTH_LONG);
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            String expName = throwable.getClass().getSimpleName();
            //判断有没有安装客户端
            if ("WechatClientNotExistException".equals(expName)
                    || "WechatTimelineNotSupportedException".equals(expName)
                    || "WechatFavoriteNotSupportedException".equals(expName)) {

                Toast.makeText(getContext(), "目前您的微信版本过低或未安装微信，需要安装微信才能使用", Toast.LENGTH_LONG);
            } else if ("QQClientNotExistException".equals(expName)) {

                Toast.makeText(getContext(), "QQ 版本过低或者没有安装，需要升级或安装QQ才能使用！", Toast.LENGTH_LONG);
            } else {
                Toast.makeText(getContext(), "分享出错了", Toast.LENGTH_LONG);
                Log.d("gjb", "分享出错了" + throwable.toString());
            }
        }

        @Override
        public void onCancel(Platform platform, int i) {
            Toast.makeText(getContext(), "取消了分享", Toast.LENGTH_LONG);
        }
    };

}
