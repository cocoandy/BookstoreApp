package com.gxwz.wzxy.bookstoreapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.base.BaseRecycleAdapter;
import com.gxwz.wzxy.bookstoreapp.modle.AddressInfo;
import com.gxwz.wzxy.bookstoreapp.modle.UserInfo;
import com.gxwz.wzxy.bookstoreapp.utils.GlideRoundTransformUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by crucy on 2017/10/28.
 */

public class UserAdapter extends BaseRecycleAdapter<UserAdapter.BookViewHolder> {

    public UserAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public BookViewHolder onCreateViewHolders(ViewGroup parent, int viewType) {
        BookViewHolder holder = new BookViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolders(BookViewHolder holder, int position) {
        UserInfo userInfo = (UserInfo) mDatas.get(position);
        holder.name.setText(userInfo.getNickname());
        holder.username.setText(userInfo.getUsername());
        holder.brith.setText(userInfo.getCreatedAt());
        Glide.with(context)
                .load(userInfo.getCover()).error(R.mipmap.ic_launcher)
                .transform(new CenterCrop(context), new GlideRoundTransformUtils(context, 15))
                .into(holder.cover);
    }


    public class BookViewHolder extends BaseRecycleAdapter.ViewHolder {
        @BindView(R.id.admin_user_name)
        public TextView name;
        @BindView(R.id.admin_user_brith)
        public TextView brith;
        @BindView(R.id.admin_user_username)
        public TextView username;
        @BindView(R.id.admin_user_cover)
        public ImageView cover;

        public BookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
