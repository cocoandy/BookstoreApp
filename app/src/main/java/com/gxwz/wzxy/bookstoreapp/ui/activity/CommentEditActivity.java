package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.adapter.BookHAdapter;
import com.gxwz.wzxy.bookstoreapp.adapter.OrderBooksAdapter;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.modle.BookInfo;
import com.gxwz.wzxy.bookstoreapp.modle.CommentInfo;
import com.gxwz.wzxy.bookstoreapp.modle.OrderInfo;
import com.gxwz.wzxy.bookstoreapp.modle.ShopCarInfo;
import com.gxwz.wzxy.bookstoreapp.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class CommentEditActivity extends BaseActivity {
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.comment_context)
    EditText comment;
    BookInfo bookInfo;
    OrderInfo orderInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_edit);
        ButterKnife.bind(this);
        orderInfo = (OrderInfo) getIntent().getSerializableExtra("orderInfo");
        if (bookInfo != null) bookInfo = orderInfo.getBookInfo();
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
        commentInfo.setUserName(BmobUser.getCurrentUser().getUsername());
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
                            finish();
                        }
                    });
                }
            }
        });
    }
}
