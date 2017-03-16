package com.emotte.mobile.dialog;

import android.app.ProgressDialog;
import android.content.Context;

import com.emotte.mobile.R;


/**
 * 加载数据时显示进度对话框 Activity是在一个TabHostActivity中，需要getparent（）
 *
 * @author maq
 */
public class ProgressDlg extends ProgressDialog {
    private static ProgressDlg dialog;

    public ProgressDlg(Context context) {
        super(context, R.style.progressDlg);
    }

    public ProgressDlg(Context context, int theme) {
        super(context, theme);
    }


    public static void showDlg(Context context, String message) {
        if (dialog == null) {
            dialog = new ProgressDlg(context);
        }
        dialog.setMessage(message);
        dialog.show();
    }

    /**
     * @param state 点击屏幕外是否可以消失
     */
    public static void showDlg(Context context, String message, boolean state) {
        if (dialog == null) {
            dialog = new ProgressDlg(context, R.style.progressDlg);
        }
        dialog.setCanceledOnTouchOutside(state);
        dialog.setMessage(message);
        dialog.show();
    }

    public static void cancleDlg(Context context) {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        dialog = null;
    }

}