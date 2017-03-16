package com.emotte.mobile.module.user.iview;

/**
 * Created by Administrator on 2017/3/2.
 */

public interface ILoginView {


    void registerSuccess();

    void registerFail();

    void loginSuccess();

    /**
     * @param msg 错误内容
     */
    void loginFail(String msg);

}
