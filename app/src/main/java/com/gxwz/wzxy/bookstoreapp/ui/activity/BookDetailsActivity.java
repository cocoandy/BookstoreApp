package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.adapter.TextTabAdapter;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.modle.BookInfo;
import com.gxwz.wzxy.bookstoreapp.modle.ShopCarInfo;
import com.gxwz.wzxy.bookstoreapp.ui.frgment.BookCommentFragment;
import com.gxwz.wzxy.bookstoreapp.ui.frgment.BookDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class BookDetailsActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_title_detail)
    TabLayout mTabLayout;
    @BindView(R.id.book_number)
    TextView mTvNumber;

    @BindView(R.id.book_name)
    public TextView name;
    @BindView(R.id.book_actor)
    public TextView actor;
    @BindView(R.id.book_price)
    public TextView price;
    @BindView(R.id.book_press)
    public TextView press;
    @BindView(R.id.book_comment)
    public TextView comment;
    @BindView(R.id.book_cover)
    public ImageView cover;

    public String bookId;
    public BookInfo book;

    public void setBookInfo(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
    }

    BookDetailsFragment bookDetailsFragment;
    BookCommentFragment bookCommentFragment;

    private List<String> titles = new ArrayList<>(); //tab名称列表
    private List<Fragment> fragments = new ArrayList<>();//定义要装fragment的列表
    private FragmentPagerAdapter fAdapter; //定义adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        ButterKnife.bind(this);
        bookId = getIntent().getStringExtra("bookId");
        book = (BookInfo) getIntent().getSerializableExtra("book");
        init();
        toolbarBreak("商品详情");
    }

    private void init() {
        //设置TabLayout的模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.addTab(mTabLayout.newTab().setText("详情"));
        mTabLayout.addTab(mTabLayout.newTab().setText("评论"));

        bookDetailsFragment = new BookDetailsFragment();
        bookDetailsFragment.setBookId(bookId);
        bookCommentFragment = new BookCommentFragment();
        bookCommentFragment.setBookId(bookId);
        bookCommentFragment.setBookInfo(book);
        titles.add("详情");
        titles.add("评论");

        fragments.add(bookDetailsFragment);
        fragments.add(bookCommentFragment);

        fAdapter = new TextTabAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mViewPager.setAdapter(fAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        getBookInfo(bookId);
    }

    public void getBookInfo(String id) {
        BmobQuery<BookInfo> query = new BmobQuery<>();
        query.getObject(id, new QueryListener<BookInfo>() {
            @Override
            public void done(BookInfo bookInfo, BmobException e) {
                if (bookInfo != null)
                    showBook(bookInfo);
            }
        });
    }

    @OnClick({R.id.book_number_add, R.id.book_number_sub, R.id.book_buy})
    public void onClick(View view) {
        int number = Integer.parseInt(mTvNumber.getText().toString().trim());
        switch (view.getId()) {
            case R.id.book_number_add:
                number++;
                mTvNumber.setText(String.valueOf(number));
                break;
            case R.id.book_number_sub:
                if (number > 1) {
                    number--;
                    mTvNumber.setText(String.valueOf(number));
                }
                break;
            case R.id.book_buy:
                addGoods();
                break;
        }
    }

    BookInfo bookInfo;

    public void showBook(BookInfo info) {
        bookInfo = info;
        name.setText(info.getName());
        actor.setText("作者：" + info.getAuthor());
        press.setText("出版社：" + info.getPress());
        price.setText("价格：￥" + info.getPrice());
        comment.setText(info.getComment() + "条评论");
        Glide.with(context).load(info.getCover()).error(R.mipmap.ic_launcher).into(cover);
    }

    /**
     * 添加到购物车
     */
    public void addGoods() {
        if (!isLogin()) {
            startActivity(new Intent(context,LoginActivity.class));
            return;
        }
        final boolean[] isExit = {false};
        BmobQuery<ShopCarInfo> query = new BmobQuery<>();
        query.addWhereEqualTo("bookInfo", bookInfo);
        query.include("bookInfo");
        query.findObjects(new FindListener<ShopCarInfo>() {
            @Override
            public void done(List<ShopCarInfo> list, BmobException e) {
                if (list == null || list.isEmpty()) {

                    ShopCarInfo shopCarInfo = new ShopCarInfo(bookInfo, Integer.parseInt(mTvNumber.getText().toString().trim()), BmobUser.getCurrentUser().getUsername());
                    shopCarInfo.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                showShort("已经添加到购物车");
                            }
                        }
                    });
                } else {
                    showShort("该商品已经在购物车中");
                }
            }
        });
    }

}
