package com.gxwz.wzxy.bookstoreapp.ui.frgment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.adapter.OrderAdapter;
import com.gxwz.wzxy.bookstoreapp.base.BaseFragment;
import com.gxwz.wzxy.bookstoreapp.modle.OrderInfo;
import com.gxwz.wzxy.bookstoreapp.ui.activity.LoginActivity;
import com.gxwz.wzxy.bookstoreapp.utils.Constant;
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

/**
 * Created by crucy on 2017/10/28.
 */

public class OrderFragment extends BaseFragment implements OrderAdapter.OrderClick {
    @BindView(R.id.recycle)
    RecyclerView recycle;
    OrderAdapter mAdapter;
    List<OrderInfo> orderInfos = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);
        initRecycle();
        receiver();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadingOrder();
    }

    private void loadingOrder() {
        if (currentUser() == null)
            return;
        BmobQuery<OrderInfo> query = new BmobQuery<>();
        query.include("bookInfo");
        query.addWhereEqualTo("userName", BmobUser.getCurrentUser().getUsername());
        query.findObjects(new FindListener<OrderInfo>() {
            @Override
            public void done(List<OrderInfo> list, BmobException e) {
                Log.e("TAG_LIST", "list:" + list.size());
                if (list != null) {
                    orderInfos.clear();
                    orderInfos.addAll(list);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadingOrder();
        } else {
            // 相当于onpause()方法
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unregisterReceiver(receiver);
    }

    public void receiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.Broadcast.FRASH_CAR_DATA);
        intentFilter.addAction(Constant.Broadcast.FRASH_ORDER_DATA);
        context.registerReceiver(receiver, intentFilter);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("TAG_ACTION", intent.getAction());
            loadingOrder();
        }
    };


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
        switch (flag) {
            case 0:
                new AlertDialog.Builder(context)
                        .setTitle("收货")
                        .setMessage("确定收货吗？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                OrderInfo orderInfo = orderInfos.get(position);
                                orderInfo.setFlag(2);
                                orderInfo.update(orderInfo.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            loadingOrder();
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("否", null)
                        .show();
                break;
            case 1:
                new AlertDialog.Builder(context)
                        .setTitle("退款")
                        .setMessage("确定退款？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                OrderInfo orderInfo = orderInfos.get(position);
                                orderInfo.setFlag(3);
                                orderInfo.update(orderInfo.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            loadingOrder();
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("否", null)
                        .show();
                break;
            case 2:
                new AlertDialog.Builder(context)
                        .setTitle("取消")
                        .setMessage("确定取消退款？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                OrderInfo orderInfo = orderInfos.get(position);
                                orderInfo.setFlag(2);
                                orderInfo.update(orderInfo.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            loadingOrder();
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("否", null)
                        .show();
                break;
        }
    }
}
