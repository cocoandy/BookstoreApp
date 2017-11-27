package com.gxwz.wzxy.bookstoreapp.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gavin on 2017/9/10.
 * 对recycleview adapter进行简单封装
 * 实现添加多个头部和底部
 * 实现点击功能
 * 实现长按功能
 */

public abstract class BaseRecycleAdapter<E extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<E> {
    public static final int TYPE_NORMAL = -99;
    public Context context;
    public List mDatas;
    ArrayList<View> headviews = new ArrayList();
    ArrayList<View> footviews = new ArrayList();
    OnItemClickListener itemClickListener;//点击事件
    OnItemLongClickListener itemLongClickListener;//长按事件

    public BaseRecycleAdapter(Context context, List mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public E onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType!=TYPE_NORMAL){
            if(headviews.size() > 0 && viewType < headviews.size()) return (E) new ViewHolder(headviews.get(viewType));
            if(footviews.size() > 0 && viewType >= (getItemCount()-footviews.size())) return (E) new ViewHolder(footviews.get(footviews.size()-(getItemCount()-viewType)));
        }
        return onCreateViewHolders(parent,viewType);
    }

    @Override
    public void onBindViewHolder(final E holder, final int position) {
        if (itemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getItemViewType(position)==TYPE_NORMAL){//数据内容
                        itemClickListener.onItemClick((ViewHolder) holder,getRealPosition(holder));
                    }else if (position<headviews.size()){//头部点击
                        itemClickListener.onHeadClick((ViewHolder) holder,position);
                    }else {
                        itemClickListener.onFootClick((ViewHolder) holder,footviews.size()-(getItemCount()-position));
                    }

                }
            });
        }
        if (itemLongClickListener!=null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (getItemViewType(position)==TYPE_NORMAL){//数据内容
                        return itemLongClickListener.onItemLongClick((ViewHolder) holder,getRealPosition(holder));
                    }else if (position<headviews.size()){//头部长按
                        return itemLongClickListener.onHeadLongClick((ViewHolder) holder,position);
                    }else {//底部长按
                        return itemLongClickListener.onFootLongClick((ViewHolder) holder,footviews.size()-(getItemCount()-position));
                    }

                }
            });

        }
        if(getItemViewType(position) != TYPE_NORMAL) return;
        onBindViewHolders(holder, getRealPosition(holder));
    }
    @Override
    public int getItemViewType(int position) {//数据类型//后面多布局的时候最好不要用数字来标记
        if(headviews.size() > position) return position;
        if(position >= (getItemCount()-footviews.size())) return position;
        return TYPE_NORMAL;
    }
    @Override
    public int getItemCount() {//设置数据源的大小 数据大小+头部+底部
        return mDatas.size() + headviews.size() + footviews.size();
    }

    /**
     * ViewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
//            if ((getRealPosition(this)+headviews.size())!=TYPE_NORMAL) return;
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return position - headviews.size();
    }
    public abstract E onCreateViewHolders(ViewGroup parent, int viewType);
    public abstract void onBindViewHolders(E holder, int position);
    public interface OnItemClickListener {
        void onItemClick(ViewHolder holder, int position);
        void onHeadClick(ViewHolder holder, int position);
        void onFootClick(ViewHolder holder, int position);

    }
    interface OnItemLongClickListener {
        boolean onItemLongClick(ViewHolder holder, int position);
        boolean onHeadLongClick(ViewHolder holder, int position);
        boolean onFootLongClick(ViewHolder holder, int position);
    }

    /**
     * 添加数据
     * @param datas
     */
    public void addDatas(List datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 点击事件
     * @param itemClickListener
     */
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    /**
     *长按事件
     */
    public void setItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    /**
     * 添加头部
     * @param headerView
     */
    public void addHeaderView(View headerView) {
        headviews.add(headerView);
        notifyItemInserted(headviews.size()-1);
    }

    /**
     * 添加底部
     * @param footView
     */
    public void addFootView(View footView) {
        footviews.add(footView);
        notifyItemInserted(getItemCount()-1);
    }
    /**
     * 添加刷新
     * @param position
     */
    public void notifyItemInsert(int position) {
        notifyItemInserted(headviews.size()+position);
    }

    /**
     * 移除刷新
     * @param position
     */
    public void notifyItemRemov(int position) {
        notifyItemRemoved(headviews.size()+position);
    }
    /**
     * 移除HeaderView
     * @param view
     */
    public void  removeHeadViewItem(View view){
        for (int i = 0;i<headviews.size();i++){
            if (headviews.get(i)==view){
                headviews.remove(i);
                this.notifyItemRemoved(i);
                return;
            }
        }
    }

    /**
     * 移除FootView
     * @param view
     */
    public void  removeFootViewItem(View view){
        for (int i = 0;i<footviews.size();i++){
            if (headviews.get(i)==view){
                headviews.remove(i);
                this.notifyItemRemoved(getItemCount()-footviews.size()+i);
                return;
            }
        }
    }
}
