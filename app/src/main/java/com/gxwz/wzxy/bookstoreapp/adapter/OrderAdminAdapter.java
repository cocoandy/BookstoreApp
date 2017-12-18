package com.gxwz.wzxy.bookstoreapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    onRecycleClick onRecycleClick;
    public int flag = -1;

    public void setOnRecycleClick(OrderAdminAdapter.onRecycleClick onRecycleClick) {
        this.onRecycleClick = onRecycleClick;
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
    public void onBindViewHolders(final BookViewHolder holder, final int position) {
        final OrderInfo orderInfo = (OrderInfo) mDatas.get(position);
        BookInfo info = orderInfo.getBookInfo();
        holder.name.setText(info.getName());
        holder.price.setText("价格：￥" + info.getPrice());
        holder.comment.setText("数量" + orderInfo.getNumber());
        Glide.with(context).load(info.getCover()).error(R.mipmap.ic_launcher).into(holder.cover);

        holder.order_id.setText(orderInfo.getObjectId());
        holder.order_time.setText(orderInfo.getCreatedAt());
        holder.order_user.setText(orderInfo.getUserName());

        holder.popu_updata.setText("修改订单");

        holder.order_statu.setText(StringUtils.getContrlType(orderInfo.getFlag()));

        holder.popu_updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecycleClick != null) onRecycleClick.onClick(v,position);
            }
        });
        holder.popu_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecycleClick != null) onRecycleClick.onClick(v,position);
            }
        });
        holder.popu_disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecycleClick != null) onRecycleClick.onClick(v,position);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = flag == position ? -1 : position;
                notifyDataSetChanged();
            }
        });

        if (flag == position) {
            holder.ll_crl.setVisibility(View.VISIBLE);
        } else {
            holder.ll_crl.setVisibility(View.GONE);
        }

    }


    public class BookViewHolder extends BaseRecycleAdapter.ViewHolder {
        @BindView(R.id.ll_crl)
        LinearLayout ll_crl;
        @BindView(R.id.item_order_id)
        TextView order_id;
        @BindView(R.id.order_statu)
        TextView order_statu;
        @BindView(R.id.item_order_time)
        TextView order_time;
        @BindView(R.id.order_user)
        TextView order_user;

        @BindView(R.id.popu_updata)
        TextView popu_updata;
        @BindView(R.id.popu_agree)
        TextView popu_agree;
        @BindView(R.id.popu_disagree)
        TextView popu_disagree;

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

        public BookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface onRecycleClick {
        public void onClick(View view, int position);
    }
}
