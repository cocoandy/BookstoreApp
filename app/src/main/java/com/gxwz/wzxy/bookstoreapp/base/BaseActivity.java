package com.gxwz.wzxy.bookstoreapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dou361.dialogui.DialogUIUtils;

import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

/**
 * Created by crucy on 2017/10/28.
 */

public class BaseActivity extends AppCompatActivity {
    public Context context;
    public static final String TAG = "TAG_BOOKAPP";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DialogUIUtils.init(this);
//        ButterKnife.bind(this);
        context = this;
    }

    public boolean isLogin(){
        BmobUser bmobUser = BmobUser.getCurrentUser();
        if (bmobUser!=null){
            return true;
        }
        return false;
    }

}
