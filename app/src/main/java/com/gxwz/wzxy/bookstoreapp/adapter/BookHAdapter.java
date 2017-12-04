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

public class BookHAdapter extends BaseRecycleAdapter<BookHAdapter.BookViewHolder> {

    public BookHAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public BookViewHolder onCreateViewHolders(ViewGroup parent, int viewType) {
        BookViewHolder holder = new BookViewHolder(LayoutInflater.from(context).inflate(R.layout.item_h_books, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolders(BookViewHolder holder, int position) {
        ShopCarInfo shopCarInfo = (ShopCarInfo) mDatas.get(position);
        BookInfo info = shopCarInfo.getBookInfo();
        Glide.with(context).load(info.getCover()).error(R.mipmap.ic_launcher).into(holder.cover);
    }


    public class BookViewHolder extends BaseRecycleAdapter.ViewHolder {
        public ImageView cover;

        public BookViewHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.img_book_cover);
        }
    }
}
