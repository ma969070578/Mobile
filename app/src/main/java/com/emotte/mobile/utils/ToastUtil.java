package com.emotte.mobile.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


/**
 * toast工具类
 *
 * @author sy
 */
public class ToastUtil {

    /**
     *
     */
    public static Toast myToast;

    public static void init(Context mcontext) {
        myToast = Toast.makeText(mcontext, "", Toast.LENGTH_SHORT);
    }

    public static void showShortToast(String msg) {
        if (myToast != null) {
            myToast.setDuration(Toast.LENGTH_SHORT);
            myToast.setText(msg);
            myToast.setGravity(Gravity.CENTER, 0, 0);
            myToast.show();
        }
    }

    public static void showLongToast(String msg) {
        if (myToast != null) {
            myToast.setDuration(Toast.LENGTH_LONG);
            myToast.setGravity(Gravity.CENTER, 0, 0);
            myToast.setText(msg);
            myToast.show();
        }
    }

    public static void showShortToast(int msg_id) {
        if (myToast != null) {
            myToast.setDuration(Toast.LENGTH_LONG);
            myToast.setGravity(Gravity.CENTER, 0, 0);
            myToast.setText(msg_id);
            myToast.show();
        }
    }

    public static void showLongToast(int msg_id) {
        if (myToast != null) {
            myToast.setDuration(Toast.LENGTH_LONG);
            myToast.setGravity(Gravity.CENTER, 0, 0);
            myToast.setText(msg_id);
            myToast.show();
        }
    }


}
