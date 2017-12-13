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
import com.gxwz.wzxy.bookstoreapp.modle.AddressInfo;
import com.gxwz.wzxy.bookstoreapp.modle.BookInfo;

import java.util.List;

/**
 * Created by crucy on 2017/10/28.
 */

public class AddressAdapter extends BaseRecycleAdapter<AddressAdapter.BookViewHolder> {

    public AddressAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public BookViewHolder onCreateViewHolders(ViewGroup parent, int viewType) {
        BookViewHolder holder = new BookViewHolder(LayoutInflater.from(context).inflate(R.layout.item_address, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolders(BookViewHolder holder, int position) {
        AddressInfo addressInfo = (AddressInfo) mDatas.get(position);
        holder.address.setText("名字：" + addressInfo.getName() + "\n电话：" + addressInfo.getPhone() + "\n地址：" + addressInfo.getProvice() + "-" + addressInfo.getCity() + "-" + addressInfo.getArea() + "\n详细地址：" + addressInfo.getAddress());
    }


    public class BookViewHolder extends BaseRecycleAdapter.ViewHolder {
        public TextView address;

        public BookViewHolder(View itemView) {
            super(itemView);
            address = (TextView) itemView.findViewById(R.id.order_edit_address);
        }
    }
}
