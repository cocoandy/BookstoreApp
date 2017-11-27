package com.gxwz.wzxy.bookstoreapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.base.BaseRecycleAdapter;
import com.gxwz.wzxy.bookstoreapp.modle.BookInfo;
import com.gxwz.wzxy.bookstoreapp.modle.ShopCarInfo;

import java.util.List;

/**
 * Created by crucy on 2017/10/28.
 */

public class OrderBooksAdapter extends BaseRecycleAdapter<OrderBooksAdapter.BookViewHolder> {

    public OrderBooksAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public BookViewHolder onCreateViewHolders(ViewGroup parent, int viewType) {
        BookViewHolder holder = new BookViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order_books, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolders(BookViewHolder holder, int position) {
        ShopCarInfo shopCarInfo = (ShopCarInfo) mDatas.get(position);
        BookInfo info = shopCarInfo.getBookInfo();
        holder.name.setText(info.getName());
        holder.price.setText("价格：￥" + info.getPrice());
        holder.comment.setText("数量" + shopCarInfo.getNumber());
        Glide.with(context).load(info.getCover()).error(R.mipmap.ic_launcher).into(holder.cover);
    }


    public class BookViewHolder extends BaseRecycleAdapter.ViewHolder {
        public TextView name;
        public TextView price;
        public TextView comment;
        public TextView number;
        public ImageView cover;

        public BookViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_book_name);
            price = (TextView) itemView.findViewById(R.id.item_book_price);
            comment = (TextView) itemView.findViewById(R.id.item_book_comment);
            number = (TextView) itemView.findViewById(R.id.item_book_number);
            cover = (ImageView) itemView.findViewById(R.id.item_book_cover);
        }
    }
}
