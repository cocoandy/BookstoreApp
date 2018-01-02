package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.modle.UserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class ResetPWDActivity extends BaseActivity {
    @BindView(R.id.pwd_old)
    EditText mTvOld;
    @BindView(R.id.pwd_set)
    EditText mTvSet;
    @BindView(R.id.pwd_sure)
    EditText mTvSure;
    @BindView(R.id.rejest_code)
    EditText etCode;

    @BindView(R.id.rejest_send_code)
    Button sendCode;

    int codeTime = 60;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        ButterKnife.bind(this);
        toolbarBreak("修改密码");
    }

    @OnClick({R.id.submit,R.id.rejest_send_code})
    public void onClick(View view) {
        String number = mTvOld.getText().toString();
        String set = mTvSet.getText().toString();
        String sure = mTvSure.getText().toString();
        String code = etCode.getText().toString();


        switch (view.getId()){
            case R.id.submit:
                if (code==null||"".equals(code)) {
                   showShort("请输入验证码");
                    return;
                }
                if (!set.equals(sure)) {
                   showShort("设置新密码输入不一致");
                    return;
                }
                updataPWD(set,code);
                break;
            case R.id.rejest_send_code:
                if (codeTime < 60) return;
                if (number==null||number.length()!=11) {
                   showShort("号码不正确");
                    return;
                }
                handler.sendEmptyMessage(0);
                sendCode(number);
                break;

        }




    }

    public void updataPWD(String pwd, String code) {
        BmobUser.resetPasswordBySMSCode(code, pwd, new UpdateListener() {

            @Override
            public void done(BmobException ex) {
                if (ex == null) {
                   showShort("密码修改成功，可以用新密码进行登录啦");
                    startActivity(new Intent(ResetPWDActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Log.e("TAG_BMOB_UPDATA",ex.getMessage());
                   showShort("密码修改失败");
                }
            }
        });
//
//        BmobUser.updateCurrentUserPassword(old, set,
//                new UpdateListener() {
//
//            @Override
//            public void done(BmobException e) {
//                if(e==null){
//                   showShort("密码修改成功，可以用新密码进行登录啦");
//                    startActivity(new Intent(ResetPWDActivity.this, LoginActivity.class));
//                    finish();
//                }else{
//                    Log.e("TAG_BMOB_UPDATA",e.getMessage());
//                   showShort("密码修改失败");
//                }
//            }
//
//        });
    }

    public void  sendCode(String number){
        BmobSMS.requestSMSCode(number,"一键注册", new QueryListener<Integer>() {

            @Override
            public void done(Integer smsId,BmobException ex) {
                if(ex==null){//验证码发送成功
                    Log.e("TAG_BMOB_UPDATA","用于查询本次短信发送详情");
                    Log.i("smile", "短信id："+smsId);//用于查询本次短信发送详情
                }
            }
        });

    }
}
