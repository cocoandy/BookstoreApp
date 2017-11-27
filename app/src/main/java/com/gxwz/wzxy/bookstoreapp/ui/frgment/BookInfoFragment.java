package com.gxwz.wzxy.bookstoreapp.ui.frgment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.base.BaseFragment;
import com.gxwz.wzxy.bookstoreapp.modle.BookInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by crucy on 2017/10/28.
 */

public class BookInfoFragment extends BaseFragment {
    @BindView(R.id.into_author_detail)
    TextView authorDetail;
    @BindView(R.id.into_book_detail)
    TextView bookDetail;
    @BindView(R.id.into_author_directory)
    TextView directory;

    String bookId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    public void showBook(BookInfo info) {
        authorDetail.setText(info.getReume() + "");
        bookDetail.setText(info.getIntroduction() + "");
        directory.setText(info.getDirectory() + "");
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
