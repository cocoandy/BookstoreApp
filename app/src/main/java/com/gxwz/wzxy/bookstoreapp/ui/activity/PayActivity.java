package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.modle.OrderInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class PayActivity extends AppCompatActivity {
    @BindView(R.id.pay_total)
    TextView mTvTotal;
    String objectId;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        objectId = getIntent().getStringExtra("objectId");
        BmobQuery<OrderInfo> query = new BmobQuery();
        query.getObject(objectId, new QueryListener<OrderInfo>() {
            @Override
            public void done(OrderInfo orderInfo, BmobException e) {
                mTvTotal.setText(orderInfo.getFlag());
            }
        });

    }

    @OnClick({R.id.submit})
    public void onClick(View view){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setObjectId(objectId);
        orderInfo.setFlag(2);
        orderInfo.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    startActivity(new Intent(PayActivity.this, MainActivity.class));
                    finish();
                }else {

                }
            }
        });
    }
}
