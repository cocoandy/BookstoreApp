package com.gxwz.wzxy.bookstoreapp.modle;

import cn.bmob.v3.BmobObject;

/**
 * Created by crucy on 2017/11/8.
 */

public class ShopCarInfo extends BmobObject{

    public BookInfo bookInfo;
    public Integer number;
    public String username;
    public boolean isSelect = false;

    public ShopCarInfo() {
    }

    public ShopCarInfo(BookInfo bookInfo, Integer number, String username) {
        this.bookInfo = bookInfo;
        this.number = number;
        this.username = username;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public BookInfo getBookInfo() {
        return bookInfo;
    }

    public void setBookInfo(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ShopCarInfo{" +
                "bookInfo=" + bookInfo +
                ", number=" + number +
                '}';
    }
}
