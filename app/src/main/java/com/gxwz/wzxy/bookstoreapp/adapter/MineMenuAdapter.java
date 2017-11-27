package com.gxwz.wzxy.bookstoreapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.base.BaseRecycleAdapter;
import com.gxwz.wzxy.bookstoreapp.modle.BookInfo;
import com.gxwz.wzxy.bookstoreapp.modle.MineMenuInfo;

import java.util.List;

/**
 * Created by crucy on 2017/10/28.
 */

public class MineMenuAdapter extends BaseRecycleAdapter<MineMenuAdapter.MineViewHolder> {

    public MineMenuAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public MineViewHolder onCreateViewHolders(ViewGroup parent, int viewType) {
        MineViewHolder holder = new MineViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mine_menu, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolders(MineViewHolder holder, int position) {
        MineMenuInfo info = (MineMenuInfo) mDatas.get(position);
        holder.title.setText(info.getTitle());
        holder.icon.setImageResource(info.getIcon());
    }


    public class MineViewHolder extends BaseRecycleAdapter.ViewHolder {
        public TextView title;
        public ImageView icon;

        public MineViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_menu_title);
            icon = (ImageView) itemView.findViewById(R.id.item_menu_icon);
        }
    }
}
