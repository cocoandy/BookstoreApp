package com.gxwz.wzxy.bookstoreapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.gxwz.wzxy.bookstoreapp.ui.activity.CommentEditActivity;
import com.gxwz.wzxy.bookstoreapp.ui.activity.PayActivity;
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
        final OrderInfo orderInfo = (OrderInfo) mDatas.get(position);
        if (position%2==0){
            holder.itemView.setBackgroundResource(R.color.whitesmoke);
        }else {
            holder.itemView.setBackgroundResource(R.color.white);
        }
        BookInfo info = orderInfo.getBookInfo();
        holder.name.setText(info.getName());
        holder.price.setText("价格：￥" + info.getPrice());
        holder.comment.setText("数量" + orderInfo.getNumber());
        Glide.with(context).load(info.getCover()).error(R.mipmap.ic_launcher).into(holder.cover);

        holder.order_id.setText(orderInfo.getObjectId());
        holder.order_time.setText(orderInfo.getCreatedAt());

        switch (orderInfo.getFlag()) {
            case 0:
                holder.contrl_pay.setVisibility(View.VISIBLE);
                break;
            case 1:
                holder.contrl_get.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.contrl_comm.setVisibility(View.VISIBLE);
                holder.contrl_back.setVisibility(View.VISIBLE);
                break;
            case 3:
                holder.contrl_cancel.setVisibility(View.VISIBLE);
                break;
        }
        holder.contrl_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PayActivity.class);
                intent.putExtra("orderInfo", orderInfo);
                context.startActivity(intent);
            }
        });
        holder.contrl_comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentEditActivity.class);
                intent.putExtra("orderInfo", orderInfo);
                context.startActivity(intent);
            }
        });
        holder.contrl_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentEditActivity.class);
                intent.putExtra("orderInfo", orderInfo);
                context.startActivity(intent);
            }
        });
        holder.contrl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentEditActivity.class);
                intent.putExtra("orderInfo", orderInfo);
                context.startActivity(intent);
            }
        });
        holder.contrl_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentEditActivity.class);
                intent.putExtra("orderInfo", orderInfo);
                context.startActivity(intent);
            }
        });

    }


    public class BookViewHolder extends BaseRecycleAdapter.ViewHolder {
        @BindView(R.id.item_order_id)
        TextView order_id;
        @BindView(R.id.item_order_time)
        TextView order_time;

        @BindView(R.id.item_book_name)
        public TextView name;
        @BindView(R.id.item_book_price)
        public TextView price;
        @BindView(R.id.item_book_comment)
        public TextView comment;
        @BindView(R.id.item_book_number)
        public TextView number;
        @BindView(R.id.item_book_cover)
        public ImageView cover;

        @BindView(R.id.contrl_pay)
        TextView contrl_pay;
        @BindView(R.id.contrl_get)
        TextView contrl_get;
        @BindView(R.id.contrl_back)
        TextView contrl_back;
        @BindView(R.id.contrl_comm)
        TextView contrl_comm;
        @BindView(R.id.contrl_cancel)
        TextView contrl_cancel;

        public BookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
