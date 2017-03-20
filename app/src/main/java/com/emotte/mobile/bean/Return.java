package com.emotte.mobile.bean;


import java.io.Serializable;

public class Return extends EntityBase implements Serializable {
    private static final long serialVersionUID = 2278901437390104424L;
    public final static int RET_SUCCESS = 0;
    public final static int RET_FAILED = -1;
    public final static int RET_ERROR = -2;
    // 生成订单成功但生成结算单失败 页面应跳入我的订单界面
    public final static int RET_ACCOUNT_FAILED = -3;
    // 登录失败 页面需重新登录
    public final static int RET_LOFIN_FAILED = -4;
    // 验证sign失败 请求无效
    public final static int RET_SIGN_FAILED = -5;
    // 绑定礼品卡或优惠券失败
    public final static int RET_BIND_FAILED = -11;

    private int code;

    private String msg;

    public Return() {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
