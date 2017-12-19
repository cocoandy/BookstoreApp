package com.gxwz.wzxy.bookstoreapp.adapter;

import android.content.Context;
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
import com.gxwz.wzxy.bookstoreapp.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by crucy on 2017/10/28.
 */

public class BookAdminAdapter extends BaseRecycleAdapter<BookAdminAdapter.BookViewHolder> {
    onRecycleClick onRecycleClick;
    public int flag = -1;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setOnRecycleClick(BookAdminAdapter.onRecycleClick onRecycleClick) {
        this.onRecycleClick = onRecycleClick;
    }

    public BookAdminAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }


    @Override
    public BookViewHolder onCreateViewHolders(ViewGroup parent, int viewType) {
        BookViewHolder holder = new BookViewHolder(LayoutInflater.from(context).inflate(R.layout.item_book_admin, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolders(final BookViewHolder holder, final int position) {
        BookInfo info = (BookInfo) mDatas.get(position);
        holder.name.setText(info.getName());
        holder.actor.setText("作者："+info.getAuthor());
        holder.press.setText("出版社："+info.getPress());
        holder.price.setText("￥"+info.getPrice());
        holder.comment.setText(info.getComment()+"条评论");
        Glide.with(context).load(info.getCover()).error(R.mipmap.ic_launcher).into(holder.cover);

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

        if (info.getStatus()==0){
            holder.statu.setVisibility(View.GONE);
            holder.popu_agree.setVisibility(View.GONE);
        }else {
            holder.statu.setVisibility(View.VISIBLE);
            holder.popu_disagree.setVisibility(View.GONE);
        }

    }


    public class BookViewHolder extends BaseRecycleAdapter.ViewHolder {
        @BindView(R.id.ll_crl)
        LinearLayout ll_crl;
        @BindView(R.id.popu_updata)
        TextView popu_updata;
        @BindView(R.id.popu_agree)
        TextView popu_agree;
        @BindView(R.id.popu_disagree)
        TextView popu_disagree;

        @BindView(R.id.item_book_name)
        public TextView name;
        @BindView(R.id.item_book_actor)
        public TextView actor;
        @BindView(R.id.item_book_price)
        public TextView price;
        @BindView(R.id.item_book_press)
        public TextView press;
        @BindView(R.id.item_book_comment)
        public TextView comment;
        @BindView(R.id.item_book_cover)
        public ImageView cover;
        @BindView(R.id.book_statu)
        public ImageView statu;

        public BookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface onRecycleClick {
        public void onClick(View view, int position);
    }
}
