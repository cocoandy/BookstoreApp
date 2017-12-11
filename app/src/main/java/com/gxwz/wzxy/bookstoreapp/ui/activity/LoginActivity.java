package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.dou361.dialogui.DialogUIUtils;
import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.modle.UserInfo;
import com.gxwz.wzxy.bookstoreapp.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.activity_login_username)
    EditText mEtusername;
    @BindView(R.id.login_password)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

    }

    @OnClick({R.id.login_submit, R.id.login_regist, R.id.login_remember,R.id.login_regiest})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_submit:
                UserInfo userInfo = new UserInfo();
                userInfo.setUsername(mEtusername.getText().toString().trim());
                userInfo.setPassword(password.getText().toString().trim());
                userInfo.login(new SaveListener<UserInfo>() {
                    @Override
                    public void done(UserInfo userInfo, BmobException e) {
                        if(e==null){
                            DialogUIUtils.showToastLong("登录成功:");
                            sendBroadcast(new Intent(Constant.Broadcast.LOGIN_SUCCESS));
                            finish();
                        }else{
                            DialogUIUtils.showToastLong("登录失败:");
                        }
                    }
                });
            break;
            case R.id.login_regiest:
                startActivity(new Intent(context,RegistActivity.class));
            break;
            case R.id.login_remember:
            break;
        }
    }
}
