package com.emotte.mobile.module.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.emotte.mobile.R;
import com.emotte.mobile.module.user.iview.ILoginView;
import com.emotte.mobile.module.user.presenter.LoginPresenter;
import com.emotte.mobile.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/2.
 */

public class LoginActivity extends AppCompatActivity implements ILoginView {


    @Bind(R.id.et_name)
    private TextInputEditText etName;
    @Bind(R.id.et_password)
    private TextInputEditText etPassword;
    @Bind(R.id.input_name)
    private TextInputLayout inputName;
    @Bind(R.id.input_password)
    private TextInputLayout inputPassword;
    @Bind(R.id.btn_login_register)
    private Button btnLoginRegister;
    @Bind(R.id.btn_login_login)
    private Button btnLoginLogin;


    private LoginPresenter mPresenter;

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mPresenter = new LoginPresenter(this);
    }


    private boolean checkInput() {
        if (TextUtils.isEmpty(etName.getText().toString().toString())) {
            inputName.setError("请输入用户名");
            return false;
        }
        if (etName.getText().toString().length() >= 13) {
            inputName.setError("请勿超过13个字！");
            return false;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString().toString())) {
            inputPassword.setError("请输入密码");
            return false;
        }
        if (etPassword.getText().toString().length() >= 10) {
            inputPassword.setError("请勿超过10个字！");
            return false;
        }

        return true;
    }

    @Override
    public void registerSuccess() {
        ToastUtil.showShortToast("注册成功");
        etName.setText("");
        etPassword.setText("");
    }

    @Override
    public void registerFail() {
        ToastUtil.showShortToast("注册失败");
    }

    @Override
    public void loginSuccess() {
        ToastUtil.showShortToast("登录成功");
        finish();
    }

    @Override
    public void loginFail(String msg) {
        ToastUtil.showShortToast(msg);
    }


    @OnClick({R.id.btn_login_register, R.id.btn_login_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_register:
                if (checkInput()) {
                    mPresenter.register(this, etName.getText().toString(), etPassword.getText().toString());
                }
                break;
            case R.id.btn_login_login:
                if (checkInput()) {
                    mPresenter.login(this, etName.getText().toString(), etPassword.getText().toString());
                }
                break;
        }
    }
}
