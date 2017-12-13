package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.adapter.AddressAdapter;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.base.BaseRecycleAdapter;
import com.gxwz.wzxy.bookstoreapp.modle.AddressInfo;
import com.gxwz.wzxy.bookstoreapp.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class AddressActivity extends BaseActivity {
    @BindView(R.id.recycle)
    RecyclerView recycle;
    AddressAdapter mAdapter;
    List<AddressInfo> addressInfos = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        initRecycle();

    }

    private void loadingAddr(){
        BmobQuery<AddressInfo> query = new BmobQuery<>();
        query.addWhereEqualTo("userInfo", BmobUser.getCurrentUser());
        query.include("userInfo");
        query.findObjects(new FindListener<AddressInfo>() {
            @Override
            public void done(List<AddressInfo> list, BmobException e) {
                addressInfos.clear();
                if (e==null){
                    addressInfos.addAll(list);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick(R.id.add_address)
    public void onClick(View view){
        startActivityForResult(new Intent(AddressActivity.this, AddressEditActivity.class), 0);
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadingAddr();
    }

    /**
     * 初始化数控数据列表
     */
    private void initRecycle() {
        mAdapter = new AddressAdapter(context, addressInfos);

        //创建默认的线性LayoutManager
        linearLayoutManager = new LinearLayoutManager(context);
        recycle.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recycle.setHasFixedSize(true);
        //创建并设置Adapter
        recycle.setAdapter(mAdapter);
        recycle.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        mAdapter.setItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleAdapter.ViewHolder holder, int position) {
                Intent intent = new Intent();
                intent.putExtra("address", addressInfos.get(position));
                setResult(1, intent);
                finish();
            }

            @Override
            public void onHeadClick(BaseRecycleAdapter.ViewHolder holder, int position) {

            }

            @Override
            public void onFootClick(BaseRecycleAdapter.ViewHolder holder, int position) {

            }
        });
    }
}
