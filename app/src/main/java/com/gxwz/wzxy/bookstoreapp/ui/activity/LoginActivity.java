package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.modle.UserInfo;
import com.gxwz.wzxy.bookstoreapp.utils.Constant;
import com.gxwz.wzxy.bookstoreapp.utils.GlideRoundTransformUtils;

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
    @BindView(R.id.log_user_cover)
    ImageView cover;
    String url = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1746951242,1205165846&fm=27&gp=0.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
        toolbarBreak("登录");
    }

    private void init() {
        Glide.with(context)
                .load(url).error(R.mipmap.ic_launcher)
                .transform(new CenterCrop(context), new GlideRoundTransformUtils(context, 15))
                .into(cover);
    }

    @OnClick({R.id.login_submit, R.id.login_regist, R.id.login_remember, R.id.login_regiest})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_submit:
                UserInfo userInfo = new UserInfo();
                userInfo.setUsername(mEtusername.getText().toString().trim());
                userInfo.setPassword(password.getText().toString().trim());
                userInfo.login(new SaveListener<UserInfo>() {
                    @Override
                    public void done(UserInfo userInfo, BmobException e) {
                        if (e == null) {
                            showShort("登录成功");
                            sendBroadcast(new Intent(Constant.Broadcast.LOGIN_SUCCESS));
                            finish();
                        } else {
                            showShort("登录失败");
                        }
                    }
                });
                break;
            case R.id.login_regiest:
                startActivity(new Intent(context, RegistActivity.class));
                break;
            case R.id.login_remember:
                break;
        }
    }
}
