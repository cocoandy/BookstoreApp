package com.gxwz.wzxy.bookstoreapp.modle;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/12/4.
 */

public class CommentInfo extends BmobObject {
    public String userName;
    public BookInfo bookInfo;
    public String context;
    public Float fating;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BookInfo getBookInfo() {
        return bookInfo;
    }

    public void setBookInfo(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Float getFating() {
        return fating;
    }

    public void setFating(Float fating) {
        this.fating = fating;
    }
}
