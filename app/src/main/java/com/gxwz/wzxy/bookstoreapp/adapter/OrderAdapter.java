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
import com.gxwz.wzxy.bookstoreapp.modle.ShopCarInfo;

import java.util.List;

import butterknife.BindView;


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
    }


    public class BookViewHolder extends BaseRecycleAdapter.ViewHolder {
        @BindView(R.id.recycle)
        RecyclerView recycle;
        public BookViewHolder(View itemView) {
            super(itemView);
            
        }
    }
}
