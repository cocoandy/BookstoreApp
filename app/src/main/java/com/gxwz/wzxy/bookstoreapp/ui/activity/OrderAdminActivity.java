package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.graphics.Color;
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
import com.gxwz.wzxy.bookstoreapp.adapter.OrderAdminAdapter;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.base.BaseRecycleAdapter;
import com.gxwz.wzxy.bookstoreapp.modle.OrderInfo;
import com.gxwz.wzxy.bookstoreapp.utils.StringUtils;
import com.gxwz.wzxy.bookstoreapp.view.FluidLayout;
import com.gxwz.wzxy.bookstoreapp.view.OrderCrlPopup;
import com.gxwz.wzxy.bookstoreapp.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class OrderAdminActivity extends BaseActivity implements OrderAdminAdapter.onRecycleClick {
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.screach_books)
    EditText screach;

    OrderAdminAdapter mAdapter;
    List<OrderInfo> orderInfos = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    FluidLayout fluidLayout;
    TextView select;

    List<String> typeInfos = new ArrayList<>();

    OrderCrlPopup popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_admin);
        ButterKnife.bind(this);
        popup = new OrderCrlPopup(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.popu_updata:
                        break;
                    case R.id.popu_set:
                        break;
                }
            }
        });
        initFluid();
        initRecycle();
        loadingOrder();
    }

    private void initFluid() {
        typeInfos.add(StringUtils.getContrlType(0));
        typeInfos.add(StringUtils.getContrlType(1));
        typeInfos.add(StringUtils.getContrlType(2));
        typeInfos.add(StringUtils.getContrlType(3));
        typeInfos.add(StringUtils.getContrlType(4));
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
        recycle.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        genTag(true);
        mAdapter.addHeaderView(view);
        mAdapter.setItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleAdapter.ViewHolder holder, int position) {
                popup.updataView("修改订单");
                popup.showAtLocation(holder.itemView, Gravity.CENTER | Gravity.CENTER_HORIZONTAL, -50, 0);
            }

            @Override
            public void onHeadClick(BaseRecycleAdapter.ViewHolder holder, int position) {

            }

            @Override
            public void onFootClick(BaseRecycleAdapter.ViewHolder holder, int position) {

            }
        });
    }

    private void loadingOrder() {
        BmobQuery<OrderInfo> query = new BmobQuery<>();
        query.include("bookInfo");
        query.findObjects(new FindListener<OrderInfo>() {
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

    @OnClick({R.id.books_scan})
    public void  onClick(View view){
        loadingBooksByName(screach.getText().toString());
    }
    private int gravity = Gravity.TOP;
    private boolean hasBg = true;
    private boolean isNormal = true;

    private void genTag(boolean hasBg) {
        fluidLayout.removeAllViews();
        fluidLayout.setGravity(gravity);
        for (int i = 0; i < typeInfos.size(); i++) {
            final int position = i;
            final String typeInfo = typeInfos.get(i);
            TextView tv = new TextView(this);
            tv.setText(typeInfo);
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
                    select.setText("你的选择： " + typeInfo);
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

    private void loadingBooksByName(String name) {
        BmobQuery<OrderInfo> eq3 = new BmobQuery<OrderInfo>();
        eq3.addWhereEqualTo("userName", name);
        BmobQuery<OrderInfo> eq4 = new BmobQuery<OrderInfo>();
        eq4.addWhereEqualTo("objectId", name);
        List<BmobQuery<OrderInfo>> queries = new ArrayList<BmobQuery<OrderInfo>>();
        queries.add(eq3);
        queries.add(eq4);
        BmobQuery<OrderInfo> mainQuery = new BmobQuery<OrderInfo>();
        BmobQuery<OrderInfo> or = mainQuery.or(queries);
        //最后组装完整的and条件
        List<BmobQuery<OrderInfo>> andQuerys = new ArrayList<BmobQuery<OrderInfo>>();
        andQuerys.add(or);

        BmobQuery<OrderInfo> queue = new BmobQuery<>();
        queue.and(andQuerys);
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

    @Override
    public void onClick(View view, int position) {
        switch (view.getId()){
            case R.id.activity_login_username:
                break;
        }
        mAdapter.notifyDataSetChanged();
    }
}
