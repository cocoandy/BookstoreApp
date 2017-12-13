package com.gxwz.wzxy.bookstoreapp.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dou361.dialogui.DialogUIUtils;
import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.modle.UserInfo;
import com.gxwz.wzxy.bookstoreapp.ui.activity.LoginActivity;
import com.gxwz.wzxy.bookstoreapp.ui.activity.RegistActivity;

import cn.bmob.v3.BmobUser;

/**
 * Created by crucy on 2017/10/28.
 */

public class BaseFragment extends Fragment{
    public Context context;
    public static final String TAG = "TAG_BOOKAPP";
    public UserInfo userInfo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        DialogUIUtils.init(context);
        userInfo = currentUser();
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (!isLogin()){
//            startActivity(new Intent(context, LoginActivity.class));
//        }
    }

    public boolean isLogin(){
        BmobUser bmobUser = BmobUser.getCurrentUser();
        if (bmobUser!=null){
            return true;
        }
        return false;
    }

    public UserInfo currentUser(){
        UserInfo userInfo = BmobUser.getCurrentUser(UserInfo.class);
        return userInfo;
    }
}
