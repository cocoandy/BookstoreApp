package com.gxwz.wzxy.bookstoreapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.modle.UserInfo;
import com.gxwz.wzxy.bookstoreapp.view.DProgressDialog;

import cn.bmob.v3.BmobUser;

/**
 * Created by crucy on 2017/10/28.
 */

public class BaseActivity extends AppCompatActivity {
    public Context context;
    public Toolbar toolbar;
    public UserInfo userInfo;
    public static final String TAG = "TAG_BOOKAPP";
    public Toast mToast;
    private DProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);
        context = this;
        userInfo = currentUser();
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public void showShort(CharSequence message) {
        if (mToast == null) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }

    public boolean isLogin() {
        BmobUser bmobUser = BmobUser.getCurrentUser();
        if (bmobUser != null) {
            return true;
        }
        return false;
    }

    public void toolbarBreak(String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarBreak(toolbar, null);
        setTitle(title);
    }

    public void toolbarBreak(Toolbar toolbar, String title) {
        setSupportActionBar(toolbar);
        //关键下面两句话，设置了回退按钮，及点击事件的效果
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(title == null ? "" : title);
        setTitle(title == null ? "" : title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public UserInfo currentUser() {
        UserInfo userInfo = BmobUser.getCurrentUser(UserInfo.class);
        return userInfo;
    }



    /**
     * 开启进度条
     */
    public void showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = new DProgressDialog(context);
            mProgressDialog.show();
        }
    }

    /**
     * 开启进度条
     */
    public void showTextProgress(String text) {
        mProgressDialog = new DProgressDialog(context);
        mProgressDialog.setText(text);
        mProgressDialog.show();
    }

    /**
     * 关闭进度条
     */
    public void closeProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
