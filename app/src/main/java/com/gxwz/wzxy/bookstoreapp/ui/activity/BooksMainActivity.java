package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.adapter.BookInfoAdapter;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.base.BaseRecycleAdapter;
import com.gxwz.wzxy.bookstoreapp.modle.BookInfo;
import com.gxwz.wzxy.bookstoreapp.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class BooksMainActivity extends BaseActivity {
    @BindView(R.id.recycle)
    RecyclerView recycle;

    List<BookInfo> bookInfos = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    BookInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_main);
        ButterKnife.bind(this);
        initRecycle();
        loadingBooks();
    }

    private void loadingBooks() {
        BmobQuery<BookInfo> queue = new BmobQuery<>();
        queue.include("type");
        queue.findObjects(new FindListener<BookInfo>() {
            @Override
            public void done(List<BookInfo> list, BmobException e) {
                if (list != null) {
                    bookInfos.clear();
                    bookInfos.addAll(list);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 初始化数控数据列表
     */
    private void initRecycle() {
        adapter = new BookInfoAdapter(context, bookInfos);
        adapter.setItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleAdapter.ViewHolder holder, int position) {

                Intent intent = new Intent(context, BookDetailsActivity.class);
                intent.putExtra("bookId", bookInfos.get(position).getObjectId());
                startActivity(intent);
            }

            @Override
            public void onHeadClick(BaseRecycleAdapter.ViewHolder holder, int position) {

            }

            @Override
            public void onFootClick(BaseRecycleAdapter.ViewHolder holder, int position) {

            }
        });

        //创建默认的线性LayoutManager
        linearLayoutManager = new LinearLayoutManager(context);
        recycle.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recycle.setHasFixedSize(true);
        //创建并设置Adapter
        recycle.setAdapter(adapter);
        recycle.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
    }
}
