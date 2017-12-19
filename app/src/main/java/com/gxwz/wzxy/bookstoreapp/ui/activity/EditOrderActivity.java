package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.modle.BookInfo;
import com.gxwz.wzxy.bookstoreapp.modle.OrderInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class EditOrderActivity extends BaseActivity {
    @BindView(R.id.submit)
    Button submit;

    @BindView(R.id.item_order_id)
    TextView order_id;
    @BindView(R.id.order_statu)
    TextView order_statu;
    @BindView(R.id.item_order_time)
    TextView order_time;
    @BindView(R.id.order_user)
    TextView order_user;

    @BindView(R.id.order_edit_name)
    TextView order_name;
    @BindView(R.id.order_edit_phone)
    TextView order_phone;
    @BindView(R.id.order_edit_address)
    TextView order_address;

    @BindView(R.id.item_book_name)
    public TextView name;
    @BindView(R.id.item_book_price)
    public TextView price;
    @BindView(R.id.item_book_comment)
    public TextView comment;
    @BindView(R.id.item_book_number)
    public TextView number;
    @BindView(R.id.item_book_cover)
    public ImageView cover;
    OrderInfo orderInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);
        ButterKnife.bind(this);

        orderInfo = (OrderInfo) getIntent().getSerializableExtra("orderInfo");
        showOrder();

    }

    public void showOrder() {
        BookInfo info = orderInfo.getBookInfo();
        name.setText(info.getName());
        price.setText("价格：￥" + info.getPrice());
        comment.setText("数量" + orderInfo.getNumber());
        Glide.with(context).load(info.getCover()).error(R.mipmap.ic_launcher).into(cover);

        order_id.setText(orderInfo.getObjectId());
        order_time.setText(orderInfo.getCreatedAt());
        order_user.setText(orderInfo.getUserName());

        order_name.setText(orderInfo.getName()+"");
        order_phone.setText(orderInfo.getPhone()+"");
        order_address.setText(orderInfo.getAddress()+"");

        if (orderInfo.getFlag()!=0){
            toolbarBreak("订单详情");
            submit.setVisibility(View.GONE);
            order_name.setFocusable(false);
            order_phone.setFocusable(false);
            order_address.setFocusable(false);
        }else {
            toolbarBreak("修改订单");
        }
    }

    @OnClick({R.id.submit})
    public void onClick(View view){
        updataOrder();
    }

    public void updataOrder() {
        orderInfo.setName(order_name.getText().toString());
        orderInfo.setPhone(order_phone.getText().toString());
        orderInfo.setAddress(order_address.getText().toString());
        orderInfo.update(orderInfo.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    finish();
                }
            }
        });
    }
}
