package com.gxwz.wzxy.bookstoreapp.ui.frgment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.dou361.dialogui.DialogUIUtils;
import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.base.BaseFragment;
import com.gxwz.wzxy.bookstoreapp.modle.BookInfo;
import com.gxwz.wzxy.bookstoreapp.modle.BookTypeInfo;
import com.gxwz.wzxy.bookstoreapp.modle.TypeInfo;
import com.gxwz.wzxy.bookstoreapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by crucy on 2017/10/28.
 */

public class BookAddFragment extends BaseFragment {
    @BindView(R.id.img_cover)
    ImageView imgCover;
    @BindView(R.id.et_book_name)
    EditText tvName;
    @BindView(R.id.et_book_author)
    EditText tvAuthor;
    @BindView(R.id.et_book_press)
    EditText tvPress;
    @BindView(R.id.et_book_price)
    EditText imgPrice;
    @BindView(R.id.et_book_menu)
    EditText tvDirectory;
    @BindView(R.id.et_book_intra)
    EditText tvIntroduction;
    @BindView(R.id.et_book_resume)
    EditText tvResume;
    @BindView(R.id.spinner)
    Spinner spinner;


    private List<String> data_list = new ArrayList<>();
    private ArrayAdapter<String> arr_adapter;
    private List<BookTypeInfo> bookTypeInfos = new ArrayList<>();

    public BookTypeInfo info = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_add, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @OnClick(R.id.book_type_submit)
    public void onClick(View view) {
        Log.e(TAG, tvDirectory.getText().toString());
        saveBook();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBookTypes();
    }

    public void init() {
        //适配器
        arr_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (data_list.size() - 1 == i) {
                    info = null;
                } else {
                    info = bookTypeInfos.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void initData(List<BookTypeInfo> bookTypeInfos) {
        if (bookTypeInfos != null) {
            this.bookTypeInfos.clear();
            data_list.clear();
            data_list.add("选择图书类型");
            info = null;
            for (BookTypeInfo info : bookTypeInfos) {
                this.bookTypeInfos.add(info);
                data_list.add(info.getType());
            }
        }
        arr_adapter.notifyDataSetChanged();
        spinner.setSelection(0, true);
    }

    public void getBookTypes() {
        BookTypeInfo info = new BookTypeInfo();
        BmobQuery<BookTypeInfo> query = new BmobQuery<>();
        query.findObjects(new FindListener<BookTypeInfo>() {
            @Override
            public void done(List<BookTypeInfo> list, BmobException e) {
                initData(list);
            }
        });
    }


    public void saveBook() {
        String bookId = System.currentTimeMillis() + "";//编号
        String cover = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=243137637,94874995&fm=27&gp=0.jpg";//封面
        String name = tvName.getText().toString().trim();//书名
        String author = tvAuthor.getText().toString().trim();//作者
        String press = tvName.getText().toString().trim();//出版社
        String price = imgPrice.getText().toString().trim();//出版社
        String reume = tvResume.getText().toString().trim();//作者简介
        String introduction = tvIntroduction.getText().toString().trim();//作者简介
        String directory = tvDirectory.getText().toString().trim();//mu
        if (Utils.isEmpty(name)) {
            DialogUIUtils.showToastShort("书名不能为空");
        }
        if (Utils.isEmpty(author)) {
            DialogUIUtils.showToastShort("作者不能为空");
        }
        if (Utils.isEmpty(press)) {
            DialogUIUtils.showToastShort("出版社不能为空");
        }
        if (Utils.isEmpty(reume)) {
            DialogUIUtils.showToastShort("作者简介不能为空");
        }
        if (Utils.isEmpty(introduction)) {
            DialogUIUtils.showToastShort("图书简介不能为空");
        }
        if (Utils.isEmpty(directory)) {
            DialogUIUtils.showToastShort("目录不能为空");
        }
        if (info == null) {
            DialogUIUtils.showToastShort("类型未选择");
        }

        BookInfo bookInfo = new BookInfo(bookId, cover, name, author, press, 0.0, price, info, 0, 0, reume, introduction, directory);
        bookInfo.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    DialogUIUtils.showToastShort("添加成功");
                }
            }
        });
    }
}
