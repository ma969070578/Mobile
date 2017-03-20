package com.emotte.mobile.update;

import android.content.Context;

import com.emotte.mobile.bean.AppUpdateBean;
import com.emotte.mobile.bean.Return;
import com.emotte.mobile.dialog.ProgressDlg;
import com.emotte.mobile.http.HttpUtils;
import com.emotte.mobile.utils.DeviceCommon;
import com.emotte.mobile.utils.ToastUtil;
import com.emotte.shb.update.UpdateUtils;
import com.google.gson.Gson;
import com.lzy.okgo.callback.StringCallback;
import com.orhanobut.logger.Logger;

import java.util.TreeMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author maq
 *         <p/>
 *         检查更新
 */

public class AutoUpdater {

    private Context context;
    /* 手动更新 ， 自动更新 ， 强制更新*/
    public static final int CHECK_MANUAL = 0, CHECK_AUTO = 1, CHECK_FORCE = 2;
    public static int UPDATE_TYPE = 0;
    private static AutoUpdater autoUpdater;

    private final int GET_SUCCESS = 11;
    private final int GET_FAIL = 12;

    public AutoUpdater(Context context, int updateTpype) {
        super();
        // TODO Auto-generated constructor stub
        this.context = context;
        this.UPDATE_TYPE = updateTpype;

    }

    public static void checkForUpdate(Context context, int mtype) {
        autoUpdater = new AutoUpdater(context, mtype);
        autoUpdater.checkForUpdate();
    }

    /**
     * 根据版本号 查询有无更新
     */
    private void checkForUpdate() {
        String version = DeviceCommon.getAppVersion(context);
        TreeMap map = new TreeMap();
        map.put("version", version);
        map.put("channel", DeviceCommon.getChannelName(context, "UMENG_CHANNEL"));
        HttpUtils.checkUpdate(map, callBack);

        if (UPDATE_TYPE == CHECK_MANUAL) {
            ProgressDlg.showDlg(context, "检查新版本...");
        }
    }

    StringCallback callBack = new StringCallback() {

        @Override
        public void onAfter(String s, Exception e) {
            super.onAfter(s, e);
            ProgressDlg.cancleDlg(context);
        }

        @Override
        public void onError(Call call, Response response, Exception e) {
            if (UPDATE_TYPE == CHECK_MANUAL) {
                ToastUtil.showShortToast("暂无更新！");
            }
        }

        @Override
        public void onSuccess(String s, Call call, Response response) {

            Logger.i(s.toString());
            Gson gson = new Gson();
            AppUpdateBean appFile = gson.fromJson(s, AppUpdateBean.class);
            if (appFile != null && Return.RET_SUCCESS==appFile.getCode() && appFile
                    .getData() != null) {
                int type = appFile.getData().getType();
                if (type == 1) {// 第三方更新  需要选择对应Module
                    UpdateUtils.getInit().update(context, 1);
                } else {// 通过本地服务器更新
                    ServerUpdate.updateByMyServer(context, 1, appFile.getData());
                }
            }
        }
    };
}
  /*  CommonCallback<String> callBack = new CommonCallback<String>() {

        @Override
        public void onSuccess(String result) {
            // TODO Auto-generated method stub
            LogUtil.i("result:" + result);
            if (TextUtils.isEmpty(result)) {
                Message msg = handler.obtainMessage();
                msg.what = GET_FAIL;
                LogUtil.i("查询新版本时result为null");
                handler.sendMessage(msg);
                return;
            }

            Gson gson = new Gson();
            AppUpdateBean appFile = gson.fromJson(result, AppUpdateBean.class);
            if (appFile != null && Return.RET_SUCCESS.equals(appFile.getCode()) && appFile
                    .getData() != null) {

                if (appFile.getData().getIsUpdate() == 2) {
                    Message msg = handler.obtainMessage();
                    msg.what = GET_SUCCESS;
                    msg.obj = appFile.getData();
                    handler.sendMessage(msg);
                } else {
                    Message msg = handler.obtainMessage();
                    msg.what = GET_FAIL;
                    handler.sendMessage(msg);
                }

            } else {
                Message msg = handler.obtainMessage();
                msg.what = GET_FAIL;
                LogUtil.i("更新版本时数据解析出错");
                handler.sendMessage(msg);
            }
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            // TODO Auto-generated method stub
            ProgressDlg.cancleDlg(context);
            Message msg = handler.obtainMessage();
            msg.what = GET_FAIL;
            LogUtil.i("查询新版本失败");
            handler.sendMessage(msg);

        }

        @Override
        public void onCancelled(CancelledException cex) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onFinished() {
            // TODO Auto-generated method stub
            ProgressDlg.cancleDlg(context);
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_SUCCESS:// 有新版本
                    AppUpdateBean.AppFile mentity = (AppUpdateBean.AppFile) msg.obj;
                    int type = mentity.getType();
                    if (type == 1) {// 第三方更新  需要选择对应Module
                        UpdateUtils.getInit().update(context, mentity.getForceUpdate());
                    } else {// 通过本地服务器更新
                        ServerUpdate.updateByMyServer(context, isCheckBySys, mentity);
                    }
                    break;

                case GET_FAIL:// 没有新版本

                    if (!isCheckBySys) {
                        ToastUtil.showShortToast("暂无更新！");
                    }
                    break;
            }
        }

    };*/

