package com.gxwz.wzxy.bookstoreapp.ui.frgment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.adapter.CommentAdapter;
import com.gxwz.wzxy.bookstoreapp.base.BaseFragment;
import com.gxwz.wzxy.bookstoreapp.modle.BookInfo;
import com.gxwz.wzxy.bookstoreapp.modle.CommentInfo;
import com.gxwz.wzxy.bookstoreapp.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by crucy on 2017/10/28.
 */

public class BookCommentFragment extends BaseFragment {
    @BindView(R.id.recycle)
    RecyclerView recycle;
    CommentAdapter mAdapter;
    List<CommentInfo> commentInfos = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    String bookId;
    BookInfo bookInfo;

    public void setBookInfo(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_comm, container, false);
        ButterKnife.bind(this, view);
        initRecycle();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loading(bookId);
    }

    private void loading(String id) {
        BookInfo info = new BookInfo();
        info.setObjectId(id);
        BmobQuery<CommentInfo> query = new BmobQuery<>();
        query.include("user,bookInfo");
        query.addWhereEqualTo("bookInfo", bookInfo);
        query.findObjects(new FindListener<CommentInfo>() {
            @Override
            public void done(List<CommentInfo> list, BmobException e) {
                if (e == null) {
                    commentInfos.clear();
                    for (CommentInfo info : list) {
                        Log.e("TAG_OOO","User:---"+info.getUser().toString());
                        commentInfos.add(info);
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
        mAdapter = new CommentAdapter(context, commentInfos, false);

        //创建默认的线性LayoutManager
        linearLayoutManager = new LinearLayoutManager(context);
        recycle.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recycle.setHasFixedSize(true);
        //创建并设置Adapter
        recycle.setAdapter(mAdapter);
        recycle.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
    }

    public void setBookId(String setBookId) {
        this.bookId = setBookId;
    }
}
