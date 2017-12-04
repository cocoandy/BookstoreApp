package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;
import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.modle.OrderInfo;
import com.gxwz.wzxy.bookstoreapp.utils.NumUtil;

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
import cn.bmob.v3.listener.UpdateListener;

public class PayActivity extends AppCompatActivity {
    @BindView(R.id.pay_total)
    TextView mTvTotal;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
    OrderInfo orderInfo;
    ArrayList<OrderInfo> orderInfos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        orderInfo = (OrderInfo) getIntent().getSerializableExtra("orderInfo");
        orderInfos = (ArrayList<OrderInfo>) getIntent().getSerializableExtra("orderInfos");
        if (orderInfo != null) {
            String priceStr = orderInfo.getBookInfo().getPrice();
            int number = orderInfo.getNumber();
            double price = Double.parseDouble(priceStr);
            double total = price * number;
            mTvTotal.setText(NumUtil.moneyFormat(total) + "");
            orderInfos.add(orderInfo);

        } else {
            mTvTotal.setText(getIntent().getStringExtra("total"));
        }
    }

    @OnClick({R.id.submit})
    public void onClick(View view) {
        List<BmobObject> infos = new ArrayList<>();

        for (OrderInfo orderInfo : orderInfos) {
            orderInfo.setFlag(1);
            Log.e("TAG_III",orderInfo.getObjectId()+"------");
            infos.add(orderInfo);
        }
        BmobBatch batch = new BmobBatch();
        batch.updateBatch(infos);
        batch.doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                if (e == null) {
                    DialogUIUtils.showToastShort("支付成功");
                    startActivity(new Intent(PayActivity.this, MainActivity.class));
                    finish();
                } else {
                    DialogUIUtils.showToastShort("失败");
                }
            }
        });
    }
}
