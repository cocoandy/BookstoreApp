package com.gxwz.wzxy.bookstoreapp.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.gxwz.wzxy.bookstoreapp.modle.UserInfo;
import com.gxwz.wzxy.bookstoreapp.utils.Utils;

/**
 * Created by crucy on 2017/10/31.
 */

public class AppUser {

    public static final String NAME_FILE = "userinro";

    public static final String UESR_USERNAME = "username";
    public static final String UESR_PASSWORD = "password";
    public static final String UESR_GENDER = "gender";
    public static final String UESR_NICKNAME = "nickname";
    public static final String UESR_COVER = "cover";
    public static final String UESR_BIRTHDAY = "birthday";

    public UserInfo getUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(NAME_FILE, Context.MODE_PRIVATE);
        String username = preferences.getString(UESR_USERNAME, "");
        int gender = preferences.getInt(UESR_GENDER, 0);
        String nickname = preferences.getString(UESR_NICKNAME, "");
        String cover = preferences.getString(UESR_COVER, "");
        String birthday = preferences.getString(UESR_BIRTHDAY, "");
        if (Utils.isEmpty(username)||
                Utils.isEmpty(username)||
                Utils.isEmpty(nickname)||
                Utils.isEmpty(cover)||
                Utils.isEmpty(birthday)){
            return null;
        }

        return null;
    }

    public void saveUser(Context context,UserInfo info,String pwd) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString(UESR_USERNAME, info.getUsername());
        editor.putInt(UESR_GENDER, info.getGender());
        editor.putString(UESR_NICKNAME, info.getNickname());
        editor.putString(UESR_COVER, info.getCover());
        editor.putString(UESR_BIRTHDAY, info.getBirthday());
        editor.putString(UESR_PASSWORD, pwd);
        editor.commit();//提交修改
    }


}
