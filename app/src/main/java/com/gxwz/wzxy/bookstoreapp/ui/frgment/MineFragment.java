package com.gxwz.wzxy.bookstoreapp.ui.frgment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.adapter.BookInfoAdapter;
import com.gxwz.wzxy.bookstoreapp.adapter.MineMenuAdapter;
import com.gxwz.wzxy.bookstoreapp.base.BaseFragment;
import com.gxwz.wzxy.bookstoreapp.base.BaseRecycleAdapter;
import com.gxwz.wzxy.bookstoreapp.modle.BookInfo;
import com.gxwz.wzxy.bookstoreapp.modle.MineMenuInfo;
import com.gxwz.wzxy.bookstoreapp.modle.OrderInfo;
import com.gxwz.wzxy.bookstoreapp.ui.activity.BookDetailsActivity;
import com.gxwz.wzxy.bookstoreapp.ui.activity.BookManegeActivity;
import com.gxwz.wzxy.bookstoreapp.ui.activity.LoginActivity;
import com.gxwz.wzxy.bookstoreapp.ui.activity.MainActivity;
import com.gxwz.wzxy.bookstoreapp.ui.activity.MineEditActivity;
import com.gxwz.wzxy.bookstoreapp.ui.activity.PayActivity;
import com.gxwz.wzxy.bookstoreapp.ui.activity.MainActivity;
import com.gxwz.wzxy.bookstoreapp.ui.activity.OrderActivity;
import com.gxwz.wzxy.bookstoreapp.ui.activity.PayActivity;
import com.gxwz.wzxy.bookstoreapp.utils.Constant;
import com.gxwz.wzxy.bookstoreapp.view.RecycleViewDivider;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import butterknife.OnClick;

/**
 * Created by crucy on 2017/10/28.
 */

public class MineFragment extends BaseFragment {
    @BindView(R.id.recycle)
    RecyclerView recycle;

    @BindView(R.id.ming_order_nopay)
    TextView mTvNoPay;
    @BindView(R.id.ming_order_pay)
    TextView mTvPay;
    @BindView(R.id.ming_order_comm)
    TextView mTvComm;
    @BindView(R.id.ming_order_back)
    TextView mTvBack;
    @BindView(R.id.user_login)
    TextView mTvLogin;

    @BindView(R.id.user_name)
    TextView mTvName;
    @BindView(R.id.user_cover)
    ImageView mImgCover;
    @BindView(R.id.user_genter)
    ImageView mImgGenter;
    @BindView(R.id.submit)
    Button mBtnLoginout;

    MineMenuAdapter adapter;
    GridLayoutManager layoutManager;
    List<MineMenuInfo> menuInfos = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void initData() {
        BmobUser bmobUser = BmobUser.getCurrentUser();
        mTvName.setText(bmobUser == null ? "" : bmobUser.getUsername());
        mTvNoPay.setText("0");
        mTvPay.setText("0");
        mTvComm.setText("0");
        mTvBack.setText("0");
        mTvLogin.setVisibility(isLogin() ? View.GONE : View.VISIBLE);
        mBtnLoginout.setVisibility(!isLogin() ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycle();
        initData();
        registBrocast();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            showNumber(0);
            showNumber(1);
            showNumber(2);
            showNumber(3);
        } else {
        }
    }

    public void showNumber(final int flag) {
        BmobQuery<OrderInfo> query = new BmobQuery<>();
        query.addWhereEqualTo("userName", BmobUser.getCurrentUser() == null ? "" : BmobUser.getCurrentUser().getUsername());
        query.addWhereEqualTo("flag", flag);
        query.findObjects(new FindListener<OrderInfo>() {
            @Override
            public void done(List<OrderInfo> list, BmobException e) {

                switch (flag) {
                    case 0:
                        mTvNoPay.setText("待付款(" + list.size() + ")");
                        break;
                    case 1:
                        mTvPay.setText("待收货(" + list.size() + ")");
                        break;
                    case 2:
                        mTvComm.setText("待评价(" + list.size() + ")");
                        break;
                    case 3:
                        mTvBack.setText("售后(" + list.size() + ")");
                        break;
                }

            }
        });
    }

    @OnClick({R.id.ming_order_nopay, R.id.ming_order_pay, R.id.ming_order_comm, R.id.ming_order_back, R.id.userinfo_edit, R.id.submit, R.id.user_login})
    public void onClick(View view) {
        if (!isLogin() && view.getId() != R.id.user_login) {
            Toast.makeText(context, "清先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.userinfo_edit:
                if (isLogin()) {
                    startActivity(new Intent(context, MineEditActivity.class));
                } else {
                    startActivity(new Intent(context, LoginActivity.class));
                }
                return;
            case R.id.ming_order_nopay:
                intent.setClass(context, OrderActivity.class);
                intent.putExtra("flag", 0);
                break;
            case R.id.ming_order_pay:
                intent.setClass(context, OrderActivity.class);
                intent.putExtra("flag", 1);
                break;
            case R.id.ming_order_comm:
                intent.setClass(context, OrderActivity.class);
                intent.putExtra("flag", 2);
                break;
            case R.id.ming_order_back:
                intent.setClass(context, OrderActivity.class);
                intent.putExtra("flag", 3);
                break;
            case R.id.submit:
                BmobUser.logOut();   //清除缓存用户对象
                initData();
                return;
            case R.id.user_login:
                intent.setClass(context, LoginActivity.class);
                break;
        }

        startActivity(intent);
    }

    public void registBrocast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.Broadcast.LOGIN_SUCCESS);
        context.registerReceiver(receiver, filter);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            showNumber(0);
            showNumber(1);
            showNumber(2);
            showNumber(3);
            initData();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unregisterReceiver(receiver);
    }

    /**
     * 初始化数控数据列表
     */
    private void initRecycle() {
        menuInfos.add(new MineMenuInfo("我的评论", R.mipmap.icon_phone, 1));
        menuInfos.add(new MineMenuInfo("图书管理", R.mipmap.icon_phone, 2));
        menuInfos.add(new MineMenuInfo("用户管理", R.mipmap.icon_phone, 3));
        menuInfos.add(new MineMenuInfo("123123", R.mipmap.icon_phone, 1));
        menuInfos.add(new MineMenuInfo("123123", R.mipmap.icon_phone, 1));
        menuInfos.add(new MineMenuInfo("123123", R.mipmap.icon_phone, 1));
        menuInfos.add(new MineMenuInfo("123123", R.mipmap.icon_phone, 1));
        adapter = new MineMenuAdapter(context, menuInfos);
        adapter.setItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleAdapter.ViewHolder holder, int position) {
                if (!isLogin()) {
                    Toast.makeText(context, "清先登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                switch (menuInfos.get(position).getPage()) {
                    case 1:
                        startActivity(new Intent(context, BookManegeActivity.class));
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                }

            }

            @Override
            public void onHeadClick(BaseRecycleAdapter.ViewHolder holder, int position) {

            }

            @Override
            public void onFootClick(BaseRecycleAdapter.ViewHolder holder, int position) {

            }
        });

        //创建默认的线性LayoutManager
        layoutManager = new GridLayoutManager(context, 3);
        recycle.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recycle.setHasFixedSize(true);
        //创建并设置Adapter
        recycle.setAdapter(adapter);
        recycle.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
    }
}
