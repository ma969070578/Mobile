package com.emotte.mobile.update;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.emotte.mobile.R;
import com.emotte.mobile.bean.AppUpdateBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.request.BaseRequest;

import java.io.File;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by chenxw on 2016/8/16.
 * 本地服务器更新
 */
public class ServerUpdate {

    /***
     * 使用本地服务器更新
     */
    public static void updateByMyServer(final Context context, final int isCheckBySys,
                                        final AppUpdateBean.AppFile entity) {
        final UpdateDialog dialog = new UpdateDialog(context, R.style.WhiteDialog, entity
                .getForceUpdate());
        dialog.show(dialog);
        dialog.setCloseClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setUpdataClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localDownload(context, entity.getUrl());
                dialog.dismiss();
            }
        });
        dialog.setDos(entity.getDesc());
    }


    /***
     * 使用本地服务器更新下载
     *
     * @param updateUrl 软件更新的地址
     */

    private static void localDownload(final Context context, String updateUrl) {
        // 设置下载缓存到下载目录
        String folder = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS).getPath();
        // 文件名称
        String filename = getFileName(updateUrl);
        final String save_path = folder + "/" + filename;
        Log.i("====", save_path);


        OkGo.get(updateUrl)
                .execute(new FileCallback(filename) {
                    DownLoadingDialog mLoadingDialog;

                    @Override
                    public void onBefore(BaseRequest request) {
                        if (mLoadingDialog == null) {
                            mLoadingDialog = new DownLoadingDialog(context, R.style
                                    .WhiteDialog);
                        }
                        mLoadingDialog.show(mLoadingDialog);
                    }

                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                        mLoadingDialog.dismiss();
                        install(context, save_path);
                    }

                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        System.out.println("downloadProgress -- " + totalSize + "  " + currentSize + "  " + progress + "  " + networkSpeed);

//                        String downloadLength = Formatter.formatFileSize(App.getInstance(), currentSize);
//                        String totalLength = Formatter.formatFileSize(App.getInstance(), totalSize);

                        mLoadingDialog.setLoading((int) (progress * 100));
                    }

                    @Override
                    public void onError(Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(call, response, e);

                    }
                });

    }

    /**
     * install app
     *
     * @param context
     * @param fileName
     * @return whether apk exist
     */
    public static void install(Context context, String fileName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(fileName)),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    public static String getFileName(String path) {
        if (path != null && path.indexOf("/") > -1) {
            return path.substring(path.lastIndexOf("/") + 1);
        }
        return "gjb.apk";
    }


}
