package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.adapter.BookAdminAdapter;
import com.gxwz.wzxy.bookstoreapp.adapter.BookInfoAdapter;
import com.gxwz.wzxy.bookstoreapp.adapter.UserAdapter;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.modle.BookInfo;
import com.gxwz.wzxy.bookstoreapp.modle.BookTypeInfo;
import com.gxwz.wzxy.bookstoreapp.modle.UserInfo;
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

public class UserListActivity extends BaseActivity {
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.screach_books)
    EditText screach;

    UserAdapter mAdapter;
    List<UserInfo> bookInfos = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_list);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        ButterKnife.bind(this);
        initRecycle();
        loadingBooksByName(null);
    }

    private void loadingBooksByName(String name) {
        BmobQuery<UserInfo> queue = new BmobQuery<>();
        if (name != null && !"".equals(name))
            queue.addWhereEqualTo("username", name);
        queue.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> list, BmobException e) {
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
        loadingBooksByName(screach.getText().toString());
    }



    /**
     * 初始化数控数据列表
     */
    private void initRecycle() {
        mAdapter = new UserAdapter(context, bookInfos);
        //创建默认的线性LayoutManager
        linearLayoutManager = new LinearLayoutManager(context);
        recycle.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recycle.setHasFixedSize(true);
        //创建并设置Adapter
        recycle.setAdapter(mAdapter);
        recycle.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
    }


}
