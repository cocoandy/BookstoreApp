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

public class ShopCardAdapter extends BaseRecycleAdapter<ShopCardAdapter.BookViewHolder> {
    UpdataBookNumber updataBookNumber;

    public void setUpdataBookNumber(UpdataBookNumber updataBookNumber) {
        this.updataBookNumber = updataBookNumber;
    }

    public ShopCardAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public BookViewHolder onCreateViewHolders(ViewGroup parent, int viewType) {
        BookViewHolder holder = new BookViewHolder(LayoutInflater.from(context).inflate(R.layout.item_shopcard, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolders(final BookViewHolder holder, final int position) {
        ShopCarInfo info = (ShopCarInfo) mDatas.get(position);
        BookInfo bookInfo = info.getBookInfo();
        holder.name.setText(bookInfo.getName());
        holder.actor.setText("作者：" + bookInfo.getAuthor());
        holder.price.setText("价格：￥" + bookInfo.getPrice());
        holder.number.setText(String.valueOf(info.getNumber()));
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (updataBookNumber != null)
                    updataBookNumber.add(position);
                holder.submit.setVisibility(View.VISIBLE);
            }
        });
        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (updataBookNumber != null)
                    updataBookNumber.sub(position);
                holder.submit.setVisibility(View.VISIBLE);
            }
        });
        holder.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (updataBookNumber != null)
                    updataBookNumber.onUpdataNumber(position);
                holder.submit.setVisibility(View.GONE);
            }
        });

        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (updataBookNumber != null)
                    updataBookNumber.select(position);
            }
        });

        holder.select.setImageResource(info.isSelect ? R.mipmap.icon_select : R.mipmap.icon_unselect);
        Glide.with(context).load(bookInfo.getCover()).error(R.mipmap.ic_launcher).into(holder.cover);
    }


    public class BookViewHolder extends BaseRecycleAdapter.ViewHolder {
        public TextView name;
        public TextView actor;
        public TextView price;
        public TextView number;
        public TextView submit;
        public TextView add;
        public TextView sub;
        public ImageView cover;
        public ImageView select;

        public BookViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_book_name);
            actor = (TextView) itemView.findViewById(R.id.item_book_actor);
            price = (TextView) itemView.findViewById(R.id.item_book_price);
            number = (TextView) itemView.findViewById(R.id.book_number);
            submit = (TextView) itemView.findViewById(R.id.book_buy);
            add = (TextView) itemView.findViewById(R.id.book_number_add);
            sub = (TextView) itemView.findViewById(R.id.book_number_sub);
            cover = (ImageView) itemView.findViewById(R.id.item_book_cover);
            select = (ImageView) itemView.findViewById(R.id.shop_card_select);
        }
    }

    public interface UpdataBookNumber {
        public void onUpdataNumber(int position);

        public void add(int position);

        public void sub(int position);

        public void select(int position);
    }
}
