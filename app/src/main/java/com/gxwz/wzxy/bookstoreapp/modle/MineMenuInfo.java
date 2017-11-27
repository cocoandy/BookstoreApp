package com.gxwz.wzxy.bookstoreapp.modle;

/**
 * Created by crucy on 2017/11/5.
 */

public class MineMenuInfo {

    String title;//名称
    int icon;//图标
    int page;//跳转标记

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public MineMenuInfo(String title, int icon, int page) {
        this.title = title;
        this.icon = icon;
        this.page = page;
    }
}
