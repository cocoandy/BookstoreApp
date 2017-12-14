package com.gxwz.wzxy.bookstoreapp.adapter;

import android.content.Context;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by crucy on 2017/10/28.
 */

public class BookInfoAdapter extends BaseRecycleAdapter<BookInfoAdapter.BookViewHolder> {

    public BookInfoAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public BookViewHolder onCreateViewHolders(ViewGroup parent, int viewType) {
        BookViewHolder holder = new BookViewHolder(LayoutInflater.from(context).inflate(R.layout.item_books, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolders(BookViewHolder holder, int position) {
        BookInfo info = (BookInfo) mDatas.get(position);
        holder.name.setText(info.getName());
        holder.actor.setText("作者："+info.getAuthor());
        holder.press.setText("出版社："+info.getPress());
        holder.price.setText("￥"+info.getPrice());
        holder.comment.setText(info.getComment()+"条评论");
        Glide.with(context).load(info.getCover()).error(R.mipmap.ic_launcher).into(holder.cover);
    }


    public class BookViewHolder extends BaseRecycleAdapter.ViewHolder {
        public TextView name;
        public TextView actor;
        public TextView price;
        public TextView press;
        public TextView comment;
        public ImageView cover;

        public BookViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_book_name);
            actor = (TextView) itemView.findViewById(R.id.item_book_actor);
            price = (TextView) itemView.findViewById(R.id.item_book_price);
            press = (TextView) itemView.findViewById(R.id.item_book_press);
            comment = (TextView) itemView.findViewById(R.id.item_book_comment);
            cover = (ImageView) itemView.findViewById(R.id.item_book_cover);
        }
    }
}
