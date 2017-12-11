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
import com.gxwz.wzxy.bookstoreapp.modle.BookTypeInfo;
import com.gxwz.wzxy.bookstoreapp.modle.ShopCarInfo;

import java.util.List;

/**
 * Created by crucy on 2017/10/28.
 */

public class BookTypeAdapter extends BaseRecycleAdapter<BookTypeAdapter.BookViewHolder> {

    public BookTypeAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public BookViewHolder onCreateViewHolders(ViewGroup parent, int viewType) {
        BookViewHolder holder = new BookViewHolder(LayoutInflater.from(context).inflate(R.layout.item_h_books, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolders(BookViewHolder holder, int position) {
        BookTypeInfo typeInfo = (BookTypeInfo) mDatas.get(position);
        holder.type.setText(typeInfo.getType());
    }


    public class BookViewHolder extends BaseRecycleAdapter.ViewHolder {
        public TextView type;

        public BookViewHolder(View itemView) {
            super(itemView);
            type = (TextView) itemView.findViewById(R.id.tv_book_type);
        }
    }
}
