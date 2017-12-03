package com.gxwz.wzxy.bookstoreapp.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.base.BaseRecycleAdapter;
import com.gxwz.wzxy.bookstoreapp.modle.BookInfo;
import com.gxwz.wzxy.bookstoreapp.modle.OrderInfo;
import com.gxwz.wzxy.bookstoreapp.modle.ShopCarInfo;
import com.gxwz.wzxy.bookstoreapp.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by crucy on 2017/10/28.
 */

public class OrderAdapter extends BaseRecycleAdapter<OrderAdapter.BookViewHolder> {

    public OrderAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }


    @Override
    public BookViewHolder onCreateViewHolders(ViewGroup parent, int viewType) {
        BookViewHolder holder = new BookViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolders(BookViewHolder holder, int position) {
        OrderInfo orderInfo = (OrderInfo) mDatas.get(position);
        holder.freshList(orderInfo.getShopCarInfos());
        holder.order_id.setText(orderInfo.getObjectId());
        holder.order_stadus.setText(orderInfo.getFlag()+"");
        holder.order_time.setText(orderInfo.getCreatedAt());
        holder.order_total.setText(orderInfo.getTotal());
    }


    public class BookViewHolder extends BaseRecycleAdapter.ViewHolder {
        @BindView(R.id.recycle)
        RecyclerView recycle;
        @BindView(R.id.item_order_id)
        TextView order_id;
        @BindView(R.id.item_order_stadus)
        TextView order_stadus;
        @BindView(R.id.item_order_time)
        TextView order_time;
        @BindView(R.id.item_order_total)
        TextView order_total;
        OrderBooksAdapter mAdapter;
        List<ShopCarInfo> shopCarInfos = new ArrayList<>();

        public BookViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            initRecycle(shopCarInfos);
        }

        public void freshList(List<ShopCarInfo> infos) {
            shopCarInfos.clear();
            shopCarInfos.addAll(infos);
            mAdapter.notifyDataSetChanged();
        }

        /**
         * 初始化数控数据列表
         */
        private void initRecycle(List<ShopCarInfo> shopCarInfos) {
            mAdapter = new OrderBooksAdapter(context, shopCarInfos);
            //创建默认的线性LayoutManager
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            recycle.setLayoutManager(linearLayoutManager);
            //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
            recycle.setHasFixedSize(true);
            //创建并设置Adapter
            recycle.setAdapter(mAdapter);
            recycle.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        }
    }


}
