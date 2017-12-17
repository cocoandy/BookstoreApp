package com.gxwz.wzxy.bookstoreapp.ui.frgment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.adapter.OrderAdapter;
import com.gxwz.wzxy.bookstoreapp.adapter.OrderAdminAdapter;
import com.gxwz.wzxy.bookstoreapp.base.BaseFragment;
import com.gxwz.wzxy.bookstoreapp.modle.BookInfo;
import com.gxwz.wzxy.bookstoreapp.modle.BookTypeInfo;
import com.gxwz.wzxy.bookstoreapp.modle.OrderInfo;
import com.gxwz.wzxy.bookstoreapp.modle.UserInfo;
import com.gxwz.wzxy.bookstoreapp.view.FluidLayout;

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

public class BookListFragment extends BaseFragment {
    @BindView(R.id.recycle)
    RecyclerView recycle;
    OrderAdminAdapter mAdapter;
    List<OrderInfo> orderInfos = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    FluidLayout fluidLayout;
    TextView select;

    List<BookTypeInfo> typeInfos = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        ButterKnife.bind(this, view);
        initRecycle();
        loadingOrder();
        initFluid();
        return view;
    }

    private void initFluid() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 初始化数控数据列表
     */
    private void initRecycle() {
        mAdapter = new OrderAdminAdapter(context, orderInfos);

        //创建默认的线性LayoutManager
        linearLayoutManager = new LinearLayoutManager(context);
        recycle.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recycle.setHasFixedSize(true);
        //创建并设置Adapter
        recycle.setAdapter(mAdapter);
        View view = LayoutInflater.from(context).inflate(R.layout.heand_recyclerview, recycle, false);
        fluidLayout = (FluidLayout) view.findViewById(R.id.fluid_layout);
        select = (TextView) view.findViewById(R.id.tv_select);
        genTag(true);
        mAdapter.addHeaderView(view);
    }

    private void loadingOrder() {
        BmobQuery<OrderInfo> query = new BmobQuery<>();
        query.include("bookInfo");
        query.findObjects(new FindListener<OrderInfo>() {
            @Override
            public void done(List<OrderInfo> list, BmobException e) {
                if(list != null) {
                    orderInfos.clear();
                    orderInfos.addAll(list);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private int gravity = Gravity.TOP;
    private boolean hasBg = true;
    private boolean isNormal = true;

    private void genTag(boolean hasBg) {
        fluidLayout.removeAllViews();
        fluidLayout.setGravity(gravity);
        for (int i = 0; i < typeInfos.size(); i++) {
            final int position = i;
            final BookTypeInfo typeInfo = typeInfos.get(i);
            TextView tv = new TextView(getActivity());
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
                    select.setText("你的选择： " + typeInfo.getType());
                    selectText(position);
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

    public void selectText(int info) {

        loadingBooksByType(info);
    }

    private void loadingBooksByType(int flag) {
        BmobQuery<OrderInfo> queue = new BmobQuery<>();
        queue.addWhereEqualTo("flag", flag);
        queue.include("addressInfo,bookInfo,userInfo");
        queue.findObjects(new FindListener<OrderInfo>() {
            @Override
            public void done(List<OrderInfo> list, BmobException e) {
                if (list != null) {
                    orderInfos.clear();
                    orderInfos.addAll(list);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

}
