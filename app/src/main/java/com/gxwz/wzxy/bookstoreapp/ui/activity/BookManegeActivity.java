package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.ui.frgment.BookAddFragment;
import com.gxwz.wzxy.bookstoreapp.ui.frgment.BookListFragment;
import com.gxwz.wzxy.bookstoreapp.ui.frgment.BookTypeFragment;
import com.soundcloud.android.crop.Crop;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookManegeActivity extends BaseActivity {

    Fragment mContent;
    FragmentManager fm;
    BookTypeFragment bookTypeFragment;
    BookAddFragment bookAddFragment;
    BookListFragment bookListFragment;
    String[] tags = new String[]{"type", "add", "list"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manege);
        ButterKnife.bind(this);
        init();
        toolbarBreak("图书管理");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
    }

    public void beginCrop(Uri data) {
        if (bookAddFragment != null)
            bookAddFragment.beginCrop(data);
    }

    public void handleCrop(int resultCode, Intent data) {
        if (bookAddFragment != null)
            bookAddFragment.handleCrop(resultCode, data);
    }

    private void init() {
        //加载默认界面
        fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        bookTypeFragment = new BookTypeFragment();
        bookAddFragment = new BookAddFragment();
        bookListFragment = new BookListFragment();
        mContent = bookAddFragment;
        transaction.replace(R.id.bookmanege_frame_mian, bookAddFragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_book_add:
                switchContent(mContent, bookAddFragment, 0);
                break;
            case R.id.menu_book_addtype:
                switchContent(mContent, bookTypeFragment, 1);
                break;
            case R.id.menu_book_list:
                switchContent(mContent, bookListFragment, 2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * fragment 切换
     *
     * @param from
     * @param to
     */
    public void switchContent(Fragment from, Fragment to, int position) {
        if (mContent != to) {
            mContent = to;
            FragmentTransaction transaction = fm.beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(from)
                        .add(R.id.bookmanege_frame_mian, to, tags[position]).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }


}
