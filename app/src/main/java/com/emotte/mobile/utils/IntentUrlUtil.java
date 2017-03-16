package com.emotte.mobile.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Administrator on 2016/11/23.
 */

public class IntentUrlUtil {


    public boolean handerTask(Activity mActivity, String url) {
        if (url.startsWith("tel:")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            mActivity.startActivity(intent);
            return true;
        } else if (url.startsWith("smsto:")) {
            Intent it = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
            it.putExtra("sms_body", "");
            mActivity.startActivity(it);
            return true;
        } else if (url.startsWith("mailto:")) {
            Intent it = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
            mActivity.startActivity(it);
            return true;
        } else if (url.startsWith("mqqwpa:")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            mActivity.startActivity(intent);
            return true;
        } else if (url.startsWith("http://") || url.startsWith("https://")) {
//            mActivity.getWebView().loadUrl(url);
        }

        return false;
    }
}
