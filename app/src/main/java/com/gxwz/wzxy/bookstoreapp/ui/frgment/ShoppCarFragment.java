package com.gxwz.wzxy.bookstoreapp.ui.frgment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;
import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.adapter.BookInfoAdapter;
import com.gxwz.wzxy.bookstoreapp.adapter.ShopCardAdapter;
import com.gxwz.wzxy.bookstoreapp.base.BaseFragment;
import com.gxwz.wzxy.bookstoreapp.base.BaseRecycleAdapter;
import com.gxwz.wzxy.bookstoreapp.modle.BookInfo;
import com.gxwz.wzxy.bookstoreapp.modle.ShopCarInfo;
import com.gxwz.wzxy.bookstoreapp.ui.activity.BookDetailsActivity;
import com.gxwz.wzxy.bookstoreapp.ui.activity.OrderEditActivity;
import com.gxwz.wzxy.bookstoreapp.utils.Constant;
import com.gxwz.wzxy.bookstoreapp.utils.NumUtil;
import com.gxwz.wzxy.bookstoreapp.utils.Utils;
import com.gxwz.wzxy.bookstoreapp.view.RecycleViewDivider;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by crucy on 2017/10/28.
 */

public class ShoppCarFragment extends BaseFragment implements ShopCardAdapter.UpdataBookNumber {

    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.tv_total_money)
    TextView tvMoney;
    @BindView(R.id.tv_total_buy)
    TextView tvMBuy;

    List<ShopCarInfo> mDatas = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    ShopCardAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shoppcar, container, false);
        ButterKnife.bind(this, view);
        DialogUIUtils.init(getActivity());
        receiver();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unregisterReceiver(receiver);
    }

    public void receiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.Broadcast.FRASH_CAR_DATA);
        context.registerReceiver(receiver, intentFilter);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getShopCardinfos();
        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycle();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getShopCardinfos();
        } else {
            // 相当于onpause()方法
        }
    }

    public void getShopCardinfos() {
        if (!isLogin()) return;
        BmobQuery<ShopCarInfo> query = new BmobQuery<>();
        query.addWhereEqualTo("username", BmobUser.getCurrentUser().getUsername());
        query.include("bookInfo");
        query.findObjects(new FindListener<ShopCarInfo>() {
            @Override
            public void done(List<ShopCarInfo> list, BmobException e) {
                Log.e(TAG, list.toString());
                mDatas.clear();
                if (!Utils.isEmpty(list)) {

                    tvMoney.setText("总价：￥" + new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP));
                    mDatas.clear();
                    mDatas.addAll(list);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }


    /**
     * 初始化数控数据列表
     */
    private void initRecycle() {
        adapter = new ShopCardAdapter(context, mDatas);
        adapter.setUpdataBookNumber(this);
        adapter.setItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleAdapter.ViewHolder holder, int position) {

                Intent intent = new Intent(context, BookDetailsActivity.class);
                intent.putExtra("bookId", mDatas.get(position).getBookInfo().getObjectId());
                startActivity(intent);
            }

            @Override
            public void onHeadClick(BaseRecycleAdapter.ViewHolder holder, int position) {

            }

            @Override
            public void onFootClick(BaseRecycleAdapter.ViewHolder holder, int position) {

            }
        });

        //创建默认的线性LayoutManager
        linearLayoutManager = new LinearLayoutManager(context);
        recycle.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recycle.setHasFixedSize(true);
        //创建并设置Adapter
        recycle.setAdapter(adapter);
        recycle.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
    }

    @Override
    public void onUpdataNumber(int position) {
        ShopCarInfo info = new ShopCarInfo();
        ;
        info.setNumber(mDatas.get(position).getNumber());
        info.update(mDatas.get(position).getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    DialogUIUtils.showToastShort("操作成功");
                } else {
                    getShopCardinfos();
                }
            }
        });
    }

    @Override
    public void add(int position) {
        int number = mDatas.get(position).getNumber();
        mDatas.get(position).setNumber(++number);
        adapter.notifyItemChanged(position);
        showMoney();
    }

    @Override
    public void sub(int position) {
        int number = mDatas.get(position).getNumber();
        if (number > 1) {
            mDatas.get(position).setNumber(--number);
            adapter.notifyItemChanged(position);
        }
        showMoney();
    }

    public void showMoney() {
        double money = 0;
        for (ShopCarInfo info : mDatas) {
            if (info.isSelect) {
                money = money + info.getNumber() * Double.parseDouble(info.getBookInfo().getPrice());
            }
        }
        tvMoney.setText("总价：￥" + new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    @Override
    public void select(int position) {
        boolean flag = mDatas.get(position).isSelect;
        mDatas.get(position).setSelect(!flag);
        adapter.notifyItemChanged(position);
        double money = 0;
        for (ShopCarInfo info : mDatas) {
            if (info.isSelect) {
                money = money + info.getNumber() * Double.parseDouble(info.getBookInfo().getPrice());
            }
        }
        tvMoney.setText("总价：￥" + new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    @OnClick(R.id.tv_total_buy)
    public void onClick(View view) {
        ArrayList<ShopCarInfo> books = new ArrayList();
        double money = 0;
        for (ShopCarInfo info : mDatas) {
            if (info.isSelect) {
                books.add(info);
                money = money + info.getNumber() * Double.parseDouble(info.getBookInfo().getPrice());
            }
        }
        Intent intent = new Intent(getActivity(), OrderEditActivity.class);
        intent.putExtra("ShopCarInfo", books);
        intent.putExtra("total", NumUtil.moneyFormatStr(money));
        startActivity(intent);

    }
}
