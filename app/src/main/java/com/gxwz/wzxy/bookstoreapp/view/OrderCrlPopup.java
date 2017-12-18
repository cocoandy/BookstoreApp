package com.gxwz.wzxy.bookstoreapp.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gxwz.wzxy.bookstoreapp.R;


/**
 * Created by Administrator on 2017/11/15.
 */

public class OrderCrlPopup extends PopupWindow {
    private int flag = 0; // 0：两个输入框  1：一个输入框
    private TextView mTvUpdata, mTvSet;
    private View mMenuView;
    private LinearLayout dialogContext;


    public OrderCrlPopup(final Activity context, final View.OnClickListener itemsOnClick) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.view_order_crl, null);
        mTvUpdata = (TextView) mMenuView.findViewById(R.id.popu_updata);
        mTvSet = (TextView) mMenuView.findViewById(R.id.popu_set);

        //取消按钮
        mTvUpdata.setOnClickListener(itemsOnClick);
        //设置按钮监听
        mTvSet.setOnClickListener(itemsOnClick);

        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        //设置弹框的大小
//        ViewGroup.LayoutParams layoutParams = dialogContext.getLayoutParams();
//        layoutParams.width = w * 5 / 6;
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xa0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
    }

    public void updataView(String updata, String set) {
        mTvUpdata.setText(updata);
        mTvSet.setText(set);
    }
    public void updataView(String updata) {
        mTvUpdata.setText(updata);
        mTvSet.setVisibility(View.GONE);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
