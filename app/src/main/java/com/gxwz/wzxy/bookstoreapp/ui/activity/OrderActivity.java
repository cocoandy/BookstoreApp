package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.adapter.OrderAdapter;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.modle.OrderInfo;
import com.gxwz.wzxy.bookstoreapp.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class OrderActivity extends BaseActivity {
    @BindView(R.id.recycle)
    RecyclerView recycle;
    OrderAdapter mAdapter;
    List<OrderInfo> bookInfos = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        initRecycle();
        loadingOrder();
    }

    private void loadingOrder() {
        BmobQuery<OrderInfo> query = new BmobQuery<>();
        query.findObjects(new FindListener<OrderInfo>() {
            @Override
            public void done(List<OrderInfo> list, BmobException e) {
                if(list != null) {
                    bookInfos.clear();
                    bookInfos.addAll(list);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 初始化数控数据列表
     */
    private void initRecycle() {
        mAdapter = new OrderAdapter(context, bookInfos);

        //创建默认的线性LayoutManager
        linearLayoutManager = new LinearLayoutManager(context);
        recycle.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recycle.setHasFixedSize(true);
        //创建并设置Adapter
        recycle.setAdapter(mAdapter);
        recycle.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
    }

}
