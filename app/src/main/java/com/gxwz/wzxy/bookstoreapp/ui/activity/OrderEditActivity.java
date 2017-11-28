package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.dou361.dialogui.DialogUIUtils;
import com.google.gson.Gson;
import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.adapter.OrderBooksAdapter;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.modle.AddressInfo;
import com.gxwz.wzxy.bookstoreapp.modle.BookInfo;
import com.gxwz.wzxy.bookstoreapp.modle.JsonInfo;
import com.gxwz.wzxy.bookstoreapp.modle.OrderInfo;
import com.gxwz.wzxy.bookstoreapp.modle.ShopCarInfo;
import com.gxwz.wzxy.bookstoreapp.utils.GetJsonDataUtil;
import com.gxwz.wzxy.bookstoreapp.utils.Utils;
import com.gxwz.wzxy.bookstoreapp.view.RecycleViewDivider;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
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
        total = getIntent().getStringExtra("total");
        if (list != null) mDatas.addAll(list);
        ButterKnife.bind(this);
        DialogUIUtils.init(this);
        initRecycle();
        loadingAddr();

    }

    private void loadingAddr() {
        BmobQuery<AddressInfo> query = new BmobQuery<>();
        query.order("-flag");
        query.findObjects(new FindListener<AddressInfo>() {
            @Override
            public void done(List<AddressInfo> list, BmobException e) {
                if (!Utils.isEmpty(list)) {
                    addressInfo = list.get(0);
                    address.setText("名字：" + addressInfo.getName() + "\n电话：" + addressInfo.getPhone() + "\n地址：" + addressInfo.getProvice() + "-" + addressInfo.getCity() + "-" + addressInfo.getArea() + "\n详细地址：" + addressInfo.getAddress());
                }
            }
        });
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

    @OnClick({R.id.order_edit_address, R.id.tv_total_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_edit_address:
                startActivityForResult(new Intent(OrderEditActivity.this, AddressEditActivity.class), 0);
                break;
            case R.id.tv_total_buy:
                saveOrder();
                break;
        }
    }

    public void saveOrder() {
        OrderInfo orderInfo = new OrderInfo();
//        orderInfo.setShopCarInfos(mDatas);
//        orderInfo.setUserName(BmobUser.getCurrentUser().getUsername());
//        orderInfo.setAddressInfo(addressInfo);
//        orderInfo.setTotal("1");
        orderInfo.setFlag(0);
        orderInfo.save(new SaveListener<String>() {
            @Override
            public void done(final String objectId, BmobException e) {
                DialogUIUtils.showToastShort(objectId + "--" + e.getMessage());
                Log.e(TAG,"objectId:"+objectId+"-----"+"BmobException:"+ e.getMessage());
                BmobBatch batch = new BmobBatch();
                List<BmobObject> objects = new ArrayList<BmobObject>();
                for (ShopCarInfo info : mDatas) {
                    objects.add(info);
                }
                batch.deleteBatch(objects);
                batch.doBatch(new QueryListListener<BatchResult>() {

                    @Override
                    public void done(List<BatchResult> results, BmobException ex) {
                        Intent intent = new Intent(OrderEditActivity.this, PayActivity.class);
                        intent.putExtra("objectId",objectId);
                       startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //先判断是哪个页面返回过来的
        switch (requestCode) {
            case 0:

                //再判断返回过来的情况，是成功还是失败还是其它的什么……
                switch (resultCode) {
                    case 1:
                        addressInfo = (AddressInfo) data.getSerializableExtra("address");
                        address.setText(addressInfo.getName() + "\n" + addressInfo.getPhone() + "\n" + addressInfo.getProvice() + "-" + addressInfo.getCity() + "-" + addressInfo.getArea() + "\n" + addressInfo.getAddress());
                        break;
                }
                break;
            case 1:
                //同上……
                break;
        }
    }
}
