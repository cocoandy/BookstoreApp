package com.gxwz.wzxy.bookstoreapp.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.gxwz.wzxy.bookstoreapp.ui.activity.CommentEditActivity;
import com.gxwz.wzxy.bookstoreapp.ui.activity.PayActivity;
import com.gxwz.wzxy.bookstoreapp.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by crucy on 2017/10/28.
 */

public class OrderAdminAdapter extends BaseRecycleAdapter<OrderAdminAdapter.BookViewHolder> {
    OrderClick onOrderClick;

    public void setOnOrderClick(OrderClick onOrderClick) {
        this.onOrderClick = onOrderClick;
    }

    public OrderAdminAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }


    @Override
    public BookViewHolder onCreateViewHolders(ViewGroup parent, int viewType) {
        BookViewHolder holder = new BookViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order_admin, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolders(BookViewHolder holder, final int position) {
        final OrderInfo orderInfo = (OrderInfo) mDatas.get(position);
        if (position % 2 == 0) {
            holder.itemView.setBackgroundResource(R.color.whitesmoke);
        } else {
            holder.itemView.setBackgroundResource(R.color.white);
        }
        BookInfo info = orderInfo.getBookInfo();
        holder.name.setText(info.getName());
        holder.price.setText("价格：￥" + info.getPrice());
        holder.comment.setText("数量" + orderInfo.getNumber());
        Glide.with(context).load(info.getCover()).error(R.mipmap.ic_launcher).into(holder.cover);

        holder.order_id.setText(orderInfo.getObjectId());
        holder.order_time.setText(orderInfo.getCreatedAt());

        holder.contrl_pay.setVisibility(View.GONE);
        holder.contrl_get.setVisibility(View.GONE);
        holder.contrl_comm.setVisibility(View.GONE);
        holder.contrl_back.setVisibility(View.GONE);
        holder.contrl_cancel.setVisibility(View.GONE);
        holder.order_statu.setText(StringUtils.getContrlType(orderInfo.getFlag()));

    }


    public class BookViewHolder extends BaseRecycleAdapter.ViewHolder {
        @BindView(R.id.item_order_id)
        TextView order_id;
        @BindView(R.id.order_statu)
        TextView order_statu;
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

    public interface OrderClick {
        public void onClick(int flag, int position);
    }
}
