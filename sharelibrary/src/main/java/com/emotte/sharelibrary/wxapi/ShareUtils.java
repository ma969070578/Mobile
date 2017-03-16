package com.emotte.sharelibrary.wxapi;

import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.emotte.sharelibrary.R;

import cn.sharesdk.framework.ShareSDK;
import onekeyshare.OnekeyShare;

/**
 * Created by chenxw on 2016/5/30.
 * <p/>
 * sharesdk分享工具类
 */
public class ShareUtils {
    /**
     * 简单分享
     */
    public static void easyShare(Context context) {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("分享");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getResources().getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(context);
    }

    /**
     * 自定义分享界面
     *
     * @param context
     * @param title     title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
     * @param titleUrl  titleUrl是标题的网络链接，仅在人人网和QQ空间使用
     * @param imagePath 图片地址
     * @param text      text是分享文本，所有平台都需要这个字段
     * @param url       url仅在微信（包括好友和朋友圈）中使用
     * @param site      site是分享此内容的网站名称，仅在QQ空间使用
     * @param siteurl   siteUrl是分享此内容的网站地址，仅在QQ空间使用
     */
    public static void customShare(Context context, String title, String titleUrl, String imagePath, String text, String url, String site, String siteurl) {
        ShareSDK.initSDK(context);
        ShareDialog dialog = new ShareDialog(context, title, titleUrl, imagePath, text, url, site, siteurl);
        dialog.setCanceledOnTouchOutside(true);// 设置点击屏幕Dialog消失
        dialog.show();
        Window win = dialog.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        win.setAttributes(lp);
    }

}
