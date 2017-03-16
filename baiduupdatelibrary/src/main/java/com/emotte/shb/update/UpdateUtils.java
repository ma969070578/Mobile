package com.emotte.shb.update;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.baidu.autoupdatesdk.AppUpdateInfo;
import com.baidu.autoupdatesdk.AppUpdateInfoForInstall;
import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.CPCheckUpdateCallback;
import com.baidu.autoupdatesdk.CPUpdateDownloadCallback;

/**
 * Created by chenxw on 2016/8/16.
 * 使用百度更新
 */
public class UpdateUtils {

    private static UpdateUtils instance;
    private UpdateDialog updateDialog;

    private Context context;
    private int forceupdata;
    private DownLoadingDialog mLoadingDialog;

    public static synchronized UpdateUtils getInit() {
        if (instance == null)
            instance = new UpdateUtils();
        return instance;
    }

    public UpdateUtils() {
    }


    /**
     * 使用百度更新
     */
    public void update(Context context, int forceupdata) {
        this.context = context;
        this.forceupdata = forceupdata;
        checkBaiDuUpdate(context);
    }

    /**
     * 检查百度服务器是否有文件
     */
    private void checkBaiDuUpdate(Context context) {
        BDAutoUpdateSDK.cpUpdateCheck(context, mCPCheckUpdateCallback);
    }

    CPCheckUpdateCallback mCPCheckUpdateCallback = new CPCheckUpdateCallback() {
        @Override
        public void onCheckUpdateCallback(AppUpdateInfo appUpdateInfo, AppUpdateInfoForInstall
                appUpdateInfoForInstall) {
            if (appUpdateInfo == null && appUpdateInfoForInstall == null) {
                Toast.makeText(context, "当前已经是最新版本！", Toast.LENGTH_SHORT).show();
            } else if (appUpdateInfo != null && appUpdateInfoForInstall == null) {
                showDialog(appUpdateInfo);
            } else {
                showDialog(appUpdateInfo);
            }
        }
    };

    /**
     * 第三方更新弹窗
     */
    private void showDialog(final AppUpdateInfo info) {
        updateDialog = new UpdateDialog(context, R.style.WhiteDialog, forceupdata);
        updateDialog.show(updateDialog);
        updateDialog.setUpdataClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadAPK(info);
                updateDialog.dismiss();
            }
        });
        updateDialog.setCloseClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog.dismiss();
            }
        });
        String appChangeLog = info.getAppChangeLog();
        if (!TextUtils.isEmpty(appChangeLog)) {
            appChangeLog = appChangeLog.replaceAll("<br>", "\n");
            updateDialog.setDos(appChangeLog);
        }
    }

    private void downloadAPK(AppUpdateInfo info) {
        BDAutoUpdateSDK.cpUpdateDownload(context, info, new CPUpdateDownloadCallback() {
            /*当cpUpdateDownload被调用时会触发回调该方法*/
            @Override
            public void onStart() {
                if (mLoadingDialog == null) {
                    mLoadingDialog = new DownLoadingDialog(context, R.style.WhiteDialog);
                }
                mLoadingDialog.show(mLoadingDialog);
            }

            /*下载进度通过该方法通知应用*/
            /*Percent：进度百分比 rcvLen：已下载文件大小 fileSize:文件总大小*/
            @Override
            public void onPercent(int percent, long rcvLen, long fileSize) {
                mLoadingDialog.setLoading(percent);
            }

            /*下载完成后本地的APK包路径回调接口*/
            @Override
            public void onDownloadComplete(String s) {
                baiDuInstall(s);
            }

            /*下载失败或者发送错误时回调此接口*/
            @Override
            public void onFail(Throwable throwable, String s) {

            }

            /*下载流程结束后统一调此接口*/
            @Override
            public void onStop() {
                mLoadingDialog.dismiss();
            }
        });
    }


    /**
     * @param path 本地安装包路径
     */
    private void baiDuInstall(String path) {
        BDAutoUpdateSDK.cpUpdateInstall(context, path);
    }


}
