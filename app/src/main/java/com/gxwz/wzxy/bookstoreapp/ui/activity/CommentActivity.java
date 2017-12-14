package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.adapter.CommentAdapter;
import com.gxwz.wzxy.bookstoreapp.adapter.OrderAdapter;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.modle.CommentInfo;
import com.gxwz.wzxy.bookstoreapp.modle.OrderInfo;
import com.gxwz.wzxy.bookstoreapp.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CommentActivity extends BaseActivity {
    @BindView(R.id.recycle)
    RecyclerView recycle;
    CommentAdapter mAdapter;
    List<CommentInfo> commentInfos = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        initRecycle();
        loading();
        toolbarBreak("评论列表");
    }

    private void loading() {
        BmobQuery<CommentInfo> query = new BmobQuery<>();
        query.addWhereEqualTo("user", BmobUser.getCurrentUser());
        query.include("user,bookInfo");
        query.findObjects(new FindListener<CommentInfo>() {
            @Override
            public void done(List<CommentInfo> list, BmobException e) {
                if (e == null){
                    commentInfos.clear();
                    for (CommentInfo info:list){
                        commentInfos.add(info);
                        Log.e("TAG_OOO","User:---"+info.getUser().toString());
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

    }


    /**
     * 初始化数控数据列表
     */
    private void initRecycle() {
        mAdapter = new CommentAdapter(context, commentInfos,true);

        //创建默认的线性LayoutManager
        linearLayoutManager = new LinearLayoutManager(context);
        recycle.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recycle.setHasFixedSize(true);
        //创建并设置Adapter
        recycle.setAdapter(mAdapter);
    }
}
