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
import com.gxwz.wzxy.bookstoreapp.modle.CommentInfo;

import java.util.List;

/**
 * Created by crucy on 2017/10/28.
 */

public class CommentAdapter extends BaseRecycleAdapter<CommentAdapter.CommentViewHolder> {
    boolean flag;
    public CommentAdapter(Context context, List mDatas,boolean flag) {
        super(context, mDatas);
        this.flag = flag;
    }

    @Override
    public CommentViewHolder onCreateViewHolders(ViewGroup parent, int viewType) {
        CommentViewHolder holder = new CommentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolders(CommentViewHolder holder, int position) {
        if (position%2==0){
            holder.itemView.setBackgroundResource(R.color.white);
        }else {
            holder.itemView.setBackgroundResource(R.color.whitesmoke);
        }

        CommentInfo commentInfo = (CommentInfo) mDatas.get(position);
        if (flag){
            Glide.with(context).load(commentInfo.getBookInfo().getCover()).error(R.mipmap.ic_launcher).into(holder.cover);
            holder.name.setText(commentInfo.getBookInfo().getName());
        }else{
            Glide.with(context).load(commentInfo.getUser().getCover()).error(R.mipmap.ic_launcher).into(holder.cover);
            holder.name.setText(commentInfo.getUser().getUsername());
        }
        holder.rating.setText(commentInfo.getFating()+" 分");
        holder.context.setText( commentInfo.getContext());
        holder.time.setText(commentInfo.getCreatedAt());
    }


    public class CommentViewHolder extends BaseRecycleAdapter.ViewHolder {
        public TextView name;
        public TextView rating;
        public TextView context;
        public TextView time;
        public ImageView cover;

        public CommentViewHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.item_comm_caver);
            name = (TextView) itemView.findViewById(R.id.item_comm_username);
            rating = (TextView) itemView.findViewById(R.id.item_comm_rating);
            context = (TextView) itemView.findViewById(R.id.item_comm_context);
            time = (TextView) itemView.findViewById(R.id.item_comm_time);
        }
    }
}
