package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.adapter.BookInfoAdapter;
import com.gxwz.wzxy.bookstoreapp.adapter.BookTypeAdapter;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.modle.BookInfo;
import com.gxwz.wzxy.bookstoreapp.modle.BookTypeInfo;
import com.gxwz.wzxy.bookstoreapp.view.FluidLayout;
import com.gxwz.wzxy.bookstoreapp.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class BooksActivity extends BaseActivity {
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.screach_books)
    EditText screach;

    FluidLayout fluidLayout;
    TextView select;
    BookInfoAdapter mAdapter;
    List<BookInfo> bookInfos = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    List<BookTypeInfo> typeInfos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        ButterKnife.bind(this);
        initRecycle();
        loadingBooksByName("");
        getBookTypes();
    }

    private void loadingBooksByName(String name) {
        BmobQuery<BookInfo> eq3 = new BmobQuery<BookInfo>();
        eq3.addWhereEqualTo("name", name);
        BmobQuery<BookInfo> eq4 = new BmobQuery<BookInfo>();
        eq4.addWhereEqualTo("author", name);
        List<BmobQuery<BookInfo>> queries = new ArrayList<BmobQuery<BookInfo>>();
        queries.add(eq3);
        queries.add(eq4);
        BmobQuery<BookInfo> mainQuery = new BmobQuery<BookInfo>();
        BmobQuery<BookInfo> or = mainQuery.or(queries);
        //最后组装完整的and条件
        List<BmobQuery<BookInfo>> andQuerys = new ArrayList<BmobQuery<BookInfo>>();
        andQuerys.add(or);

        BmobQuery<BookInfo> queue = new BmobQuery<>();
        queue.and(andQuerys);
        queue.include("type");
        queue.findObjects(new FindListener<BookInfo>() {
            @Override
            public void done(List<BookInfo> list, BmobException e) {
                if (list != null) {
                    bookInfos.clear();
                    bookInfos.addAll(list);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnClick({R.id.books_scan})
    public void onClick(View view) {
        select.setText(screach.getText().toString());
        loadingBooksByName(screach.getText().toString());
    }

    private void loadingBooksByType(BookTypeInfo type) {
        BmobQuery<BookInfo> queue = new BmobQuery<>();
        queue.addWhereEqualTo("type", type);
        queue.include("type");
        queue.findObjects(new FindListener<BookInfo>() {
            @Override
            public void done(List<BookInfo> list, BmobException e) {
                if (list != null) {
                    bookInfos.clear();
                    bookInfos.addAll(list);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void getBookTypes() {
        BmobQuery<BookTypeInfo> query = new BmobQuery<>();
        query.findObjects(new FindListener<BookTypeInfo>() {
            @Override
            public void done(List<BookTypeInfo> list, BmobException e) {
                initData(list);
            }


        });
    }

    private void initData(List<BookTypeInfo> list) {
        typeInfos.clear();
        typeInfos.addAll(list);
//        adapter.notifyDataSetChanged();
        genTag(true);
    }

    /**
     * 初始化数控数据列表
     */
    private void initRecycle() {
        mAdapter = new BookInfoAdapter(context, bookInfos);
        //创建默认的线性LayoutManager
        linearLayoutManager = new LinearLayoutManager(context);
        recycle.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recycle.setHasFixedSize(true);
        //创建并设置Adapter
        recycle.setAdapter(mAdapter);
        recycle.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        View view = LayoutInflater.from(context).inflate(R.layout.heand_recyclerview, recycle, false);
        fluidLayout = (FluidLayout) view.findViewById(R.id.fluid_layout);
        select = (TextView) view.findViewById(R.id.tv_select);
        genTag(true);
        mAdapter.addHeaderView(view);
    }


    private int gravity = Gravity.TOP;
    private boolean hasBg = true;
    private boolean isNormal = true;

    private void genTag(boolean hasBg) {
        fluidLayout.removeAllViews();
        fluidLayout.setGravity(gravity);
        for (int i = 0; i < typeInfos.size(); i++) {
            final BookTypeInfo typeInfo = typeInfos.get(i);
            TextView tv = new TextView(this);
            tv.setText(typeInfo.getType());
            tv.setTextSize(13);

            if (i == 12) {
                if (!isNormal) {
                    tv.setHeight(100);
                    tv.setGravity(Gravity.CENTER);
                }
                tv.setBackgroundResource(R.drawable.text_bg_highlight);

            } else {
                if (hasBg) {
                    tv.setBackgroundResource(R.drawable.text_bg);
                }
            }

            tv.setTextColor(Color.parseColor("#FF0000"));
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectText(typeInfo);
                }
            });
            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(12, 12, 12, 12);
            fluidLayout.addView(tv, params);
        }
    }

    public void selectText(BookTypeInfo info) {
        select.setText("你的选择： " + info.getType());
        loadingBooksByType(info);
    }
}
