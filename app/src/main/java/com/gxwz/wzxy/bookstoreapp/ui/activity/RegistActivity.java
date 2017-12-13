package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.dou361.dialogui.DialogUIUtils;
import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.modle.UserInfo;
import com.gxwz.wzxy.bookstoreapp.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class RegistActivity extends BaseActivity {
    @BindView(R.id.rejest_number)
    EditText etNumber;
    @BindView(R.id.rejest_code)
    EditText etCode;
    @BindView(R.id.rejest_password)
    EditText etPassword;
    @BindView(R.id.rejest_repassword)
    EditText etConfirm;
    @BindView(R.id.rejest_nickname)
    EditText etNickname;
    @BindView(R.id.rejest_gender_man)
    RadioButton etGenderMan;
    @BindView(R.id.rejest_gender_women)
    RadioButton etGenderWomen;
    @BindView(R.id.rejest_send_code)
    Button sendCode;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (codeTime == 0) {
                handler.removeMessages(0);
                codeTime = 60;
                sendCode.setText("验证码");
                return;
            }
            sendCode.setText(codeTime + " (s)");
            codeTime--;
            sendEmptyMessageDelayed(0, 1000);
        }
    };
    int codeTime = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        toolbarBreak("注册");
    }
public boolean checkUser(String number){
    final boolean[] isUserExit = {false};
    //查找Person表里面id为6b6c11c537的数据
    BmobQuery<UserInfo> bmobQuery = new BmobQuery<UserInfo>();
    bmobQuery.getObject(number, new QueryListener<UserInfo>() {
        @Override
        public void done(UserInfo info, BmobException e) {
                if (info!=null) isUserExit[0] = true;
        }
    });
    return isUserExit[0];
}
    @OnClick({R.id.rejest_submit, R.id.rejest_send_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rejest_submit:
                regist();
                break;
            case R.id.rejest_send_code:
                if (codeTime < 60) return;
                String number = etNumber.getText().toString().trim();
                if (Utils.isEmpty(number) && number.length() != 11) {
                    DialogUIUtils.showToastLong("手机号有误！");
                    return;
                }
                handler.sendEmptyMessage(0);
                sendCode(number);
                break;
        }
    }

    public void sendCode(String number) {
        BmobSMS.requestSMSCode(number, "一键注册", new QueryListener<Integer>() {
            @Override
            public void done(Integer smsId, BmobException ex) {
                if (ex == null) {//验证码发送成功
                    DialogUIUtils.showToastLong("验证码发送成功！");
                } else {
                    DialogUIUtils.showToastLong("验证码发送失败！");
                }
            }
        });
    }

    public void regist() {

        String number = etNumber.getText().toString().trim();
        String code = etCode.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirm = etConfirm.getText().toString().trim();
        String nickname = etNickname.getText().toString().trim();
        int gender = 0;
        if (etGenderWomen.isChecked()) gender = 1;

        if (Utils.isEmpty(nickname)) {
            DialogUIUtils.showToastLong("昵称不能为空！");
            return;
        }
        if (Utils.isEmpty(number) && number.length() != 11) {
            DialogUIUtils.showToastLong("手机号有误！");
            return;
        }
        if (Utils.isEmpty(code) && code.length() != 6) {
            DialogUIUtils.showToastLong("验证码有误！");
            return;
        }
        if (Utils.isEmpty(password)) {
            DialogUIUtils.showToastLong("请输入密码！");
            return;
        }
        if (!password.equals(confirm)) {
            DialogUIUtils.showToastLong("密码不一致！");
            return;
        }

        if (checkUser(number)){
            DialogUIUtils.showToastLong("该手机号已经注册！");
            return;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setMobilePhoneNumber(number);
        userInfo.setPassword(password);
        userInfo.setNickname(nickname);
        userInfo.setGender(gender);
        userInfo.setCover("http://www.qqzhi.com/uploadpic/2014-09-12/183703637.jpg");
        userInfo.signOrLogin(code, new SaveListener<UserInfo>() {
            @Override
            public void done(UserInfo userInfo, BmobException e) {
                if (userInfo!=null){
                    DialogUIUtils.showToastLong("注册成功！");
                    finish();
                }else {
                    DialogUIUtils.showToastLong("注册失败！");
                }
            }
        });
    }
}
