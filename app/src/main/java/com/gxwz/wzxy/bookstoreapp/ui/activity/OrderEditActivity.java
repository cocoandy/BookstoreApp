package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;
import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.adapter.OrderBooksAdapter;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.modle.AddressInfo;
import com.gxwz.wzxy.bookstoreapp.modle.OrderInfo;
import com.gxwz.wzxy.bookstoreapp.modle.ShopCarInfo;
import com.gxwz.wzxy.bookstoreapp.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SaveListener;

public class OrderEditActivity extends BaseActivity {
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.tv_total_money)
    TextView tvMoney;
    @BindView(R.id.order_edit_address)
    TextView address;

    AddressInfo addressInfo;
    String total;

    List<ShopCarInfo> mDatas = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    OrderBooksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_edit);
        List<ShopCarInfo> list = (List<ShopCarInfo>) getIntent().getSerializableExtra("ShopCarInfo");
        if (list != null) mDatas.addAll(list);
        ButterKnife.bind(this);
        DialogUIUtils.init(this);
        initRecycle();

    }

    /**
     * 初始化数控数据列表
     */
    private void initRecycle() {
        adapter = new OrderBooksAdapter(context, mDatas);

        //创建默认的线性LayoutManager
        linearLayoutManager = new LinearLayoutManager(context);
        recycle.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recycle.setHasFixedSize(true);
        //创建并设置Adapter
        recycle.setAdapter(adapter);
        recycle.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
    }
    @OnClick({R.id.order_edit_address})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.order_edit_address:
                startActivityForResult(new Intent(OrderEditActivity.this,AddressEditActivity.class),0);
                break;
        }
    }

    public void saveOrder() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setShopCarInfos(mDatas);
        orderInfo.setAddressInfo(addressInfo);
        orderInfo.setTotal(total);
        orderInfo.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                //第二种方式：v3.5.0开始提供
                BmobBatch batch = new BmobBatch();
                List<BmobObject> objects = new ArrayList<BmobObject>();
                for (ShopCarInfo info : mDatas) {
                    objects.add(info);
                }
                batch.deleteBatch(objects);
                batch.doBatch(new QueryListListener<BatchResult>() {

                    @Override
                    public void done(List<BatchResult> results, BmobException ex) {
                        if (ex == null) {
                        } else {
                        }
                    }
                });
            }
        });
    }


}
