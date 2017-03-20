package com.emotte.mobile.module.user.presenter;

import android.content.Context;
import android.os.Handler;

import com.emotte.mobile.bean.Return;
import com.emotte.mobile.http.HttpUtils;
import com.emotte.mobile.module.user.iview.ILoginView;
import com.emotte.mobile.utils.MD5;
import com.emotte.mobile.utils.PreferencesHelper;
import com.emotte.mobile.utils.ProgressDlg;
import com.google.gson.Gson;
import com.lzy.okgo.callback.StringCallback;
import com.orhanobut.logger.Logger;

import java.util.TreeMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/2.
 */

public class LoginPresenter {

    private ILoginView iLoginView;

    public LoginPresenter(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
    }

    public void register(final Context context, final String name, final String password) {
        ProgressDlg.showDialog(context, "注册中...");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PreferencesHelper.putString("name", name);
                PreferencesHelper.putString("password", password);
                iLoginView.registerSuccess();
                ProgressDlg.dismissDialog();
            }
        }, 2000);
    }

    public void login(final Context context, final String name, final String password) {
        ProgressDlg.showDialog(context, "登录中...");

        TreeMap map = new TreeMap();
        map.put("loginName", name);
        map.put("loginPwd", MD5.str2MD5(password));

        HttpUtils.usernameLogin(map, new StringCallback() {

            @Override
            public void onSuccess(String s, Call call, Response response) {
                ProgressDlg.dismissDialog();
//                {"msg":"密码错误","code":"-1"}
                Logger.i(s.toString());
                Gson gson = new Gson();
                Return user = gson.fromJson(s, Return.class);
                if (user != null && Return.RET_SUCCESS == user.getCode()) {
                    iLoginView.loginSuccess();
                } else {
                    iLoginView.loginFail(user.getMsg());
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                iLoginView.loginFail("");
            }
        });

       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String n = PreferencesUtils.getString(context, "name");
                String p = PreferencesUtils.getString(context, "password");
                if (!name.equals(n)) {
                    iLoginView.loginFail("用户名错误");
                } else if (!password.equals(p)) {
                    iLoginView.loginFail("密码错误");
                } else {
                    iLoginView.loginSuccess();
                }
                ProgressDlg.dismissDialog();
            }
        }, 2000);*/
    }


}
