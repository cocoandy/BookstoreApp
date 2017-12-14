package com.gxwz.wzxy.bookstoreapp.modle;

import cn.bmob.v3.BmobUser;

/**
 * Created by crucy on 2017/10/29.
 */

public class UserInfo extends BmobUser {

    Integer gender;//性别
    String nickname;//昵称
    String cover;//头像
    String birthday;//生日
    Integer root;

    public Integer getRoot() {
        return root;
    }

    public void setRoot(Integer root) {
        this.root = root;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "gender=" + gender +
                ", nickname='" + nickname + '\'' +
                ", cover='" + cover + '\'' +
                ", birthday='" + birthday + '\'' +
                ", root=" + root +
                '}';
    }
}
