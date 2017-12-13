package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.modle.BookInfo;
import com.gxwz.wzxy.bookstoreapp.modle.CommentInfo;
import com.gxwz.wzxy.bookstoreapp.modle.OrderInfo;
import com.gxwz.wzxy.bookstoreapp.modle.UserInfo;
import com.gxwz.wzxy.bookstoreapp.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class CommentEditActivity extends BaseActivity {
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.comment_context)
    EditText comment;
    @BindView(R.id.book_cover)
    ImageView cover;
    @BindView(R.id.book_name)
    TextView name;
    BookInfo bookInfo;
    OrderInfo orderInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_edit);
        ButterKnife.bind(this);
        orderInfo = (OrderInfo) getIntent().getSerializableExtra("orderInfo");
        setTitle("评论");
        if (orderInfo != null) {
            bookInfo = orderInfo.getBookInfo();
            Glide.with(context).load(bookInfo.getCover()).error(R.mipmap.ic_launcher).into(cover);
            name.setText(bookInfo.getName());
        }
        toolbarBreak("发表评论");
    }
         
    @OnClick({R.id.submit})
    public void onClick(View view) {
        float number = ratingBar.getRating();
        String commStr = comment.getText().toString();
        if (BmobUser.getCurrentUser() == null) {
            startActivity(new Intent(context, LoginActivity.class));
            return;
        }
        CommentInfo commentInfo = new CommentInfo();
        BmobUser user =  BmobUser.getCurrentUser();
        UserInfo userInfo = new UserInfo();
        userInfo.setObjectId(user.getObjectId());
        commentInfo.setUser(userInfo);
        commentInfo.setBookInfo(bookInfo);
        commentInfo.setContext(commStr);
        commentInfo.setFating(number);
        commentInfo.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    orderInfo.setFlag(4);
                    orderInfo.update(orderInfo.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            startActivity(new Intent(context,MainActivity.class));
                            context.sendBroadcast(new Intent(Constant.Broadcast.FRASH_ORDER_DATA));
                            context.sendBroadcast(new Intent(Constant.Broadcast.FRASH_CAR_DATA));
                            finish();
                        }
                    });
                }
            }
        });
    }
}
