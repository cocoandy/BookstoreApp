package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.adapter.OrderAdapter;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.modle.BookInfo;
import com.gxwz.wzxy.bookstoreapp.modle.OrderInfo;
import com.gxwz.wzxy.bookstoreapp.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class OrderActivity extends BaseActivity implements OrderAdapter.OrderClick {
    @BindView(R.id.recycle)
    RecyclerView recycle;
    OrderAdapter mAdapter;
    List<OrderInfo> orderInfos = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    int flag = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        flag = getIntent().getIntExtra("flag",-1);
        ButterKnife.bind(this);
        initRecycle();
        loadingOrder();
        toolbarBreak("订单列表");
    }

    private void loadingOrder() {
        BmobQuery<OrderInfo> query = new BmobQuery<>();
        query.include("bookInfo");
        query.order("-createdAt");
        query.addWhereEqualTo("userName", BmobUser.getCurrentUser().getUsername());
        if (flag>=0) query.addWhereEqualTo("flag",flag);
        query.findObjects(new FindListener<OrderInfo>() {
            @Override
            public void done(List<OrderInfo> list, BmobException e) {
                if(list != null) {
                    orderInfos.clear();
                    orderInfos.addAll(list);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 初始化数控数据列表
     */
    private void initRecycle() {
        mAdapter = new OrderAdapter(context, orderInfos);

        //创建默认的线性LayoutManager
        linearLayoutManager = new LinearLayoutManager(context);
        recycle.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recycle.setHasFixedSize(true);
        //创建并设置Adapter
        recycle.setAdapter(mAdapter);
        mAdapter.setOnOrderClick(this);
    }

    @Override
    public void onClick(int flag, final int position) {
        switch (flag){
            case 0:
                new  AlertDialog.Builder(context)
                        .setTitle("收货" )
                        .setMessage("确定收货吗？" )
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                OrderInfo orderInfo = orderInfos.get(position);
                                orderInfo.setFlag(2);
                                orderInfo.update(orderInfo.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            orderInfos.get(position).setFlag(2);
                                            mAdapter.notifyItemChanged(position);
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("否" , null)
                        .show();
                break;
            case 1:
                new  AlertDialog.Builder(context)
                        .setTitle("退款" )
                        .setMessage("确定退款？" )
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                OrderInfo orderInfo = orderInfos.get(position);
                                orderInfo.setFlag(3);
                                orderInfo.update(orderInfo.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            orderInfos.get(position).setFlag(3);
                                            mAdapter.notifyItemChanged(position);
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("否" , null)
                        .show();
                break;
            case 2:
                new  AlertDialog.Builder(context)
                        .setTitle("取消" )
                        .setMessage("确定取消退款？" )
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                OrderInfo orderInfo = orderInfos.get(position);
                                orderInfo.setFlag(2);
                                orderInfo.update(orderInfo.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            orderInfos.get(position).setFlag(2);
                                            mAdapter.notifyItemChanged(position);
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("否" , null)
                        .show();
                break;
        }
    }
}
