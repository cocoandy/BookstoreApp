package com.gxwz.wzxy.bookstoreapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.gxwz.wzxy.bookstoreapp.modle.UserInfo;
import com.gxwz.wzxy.bookstoreapp.view.DProgressDialog;

import cn.bmob.v3.BmobUser;

/**
 * Created by crucy on 2017/10/28.
 */

public class BaseFragment extends Fragment{
    public Context context;
    public static final String TAG = "TAG_BOOKAPP";
    public UserInfo userInfo;
    public Toast mToast;
    private DProgressDialog mProgressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
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
