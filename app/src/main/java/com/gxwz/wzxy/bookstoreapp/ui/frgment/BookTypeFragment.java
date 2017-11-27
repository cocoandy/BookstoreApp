package com.gxwz.wzxy.bookstoreapp.ui.frgment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.dou361.dialogui.DialogUIUtils;
import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.base.BaseFragment;
import com.gxwz.wzxy.bookstoreapp.modle.BookTypeInfo;
import com.gxwz.wzxy.bookstoreapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by crucy on 2017/10/28.
 */

public class BookTypeFragment extends BaseFragment {
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.updata_book_type)
    EditText typeEdit;

    private List<String> data_list = new ArrayList<>();
    private ArrayAdapter<String> arr_adapter;
    private List<BookTypeInfo> bookTypeInfos = new ArrayList<>();

    public BookTypeInfo info = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_type, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
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
                    typeEdit.setText("");
                } else {
                    info = bookTypeInfos.get(i);
                    typeEdit.setText(info.getType());
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

    @OnClick(R.id.book_type_submit)
    public void onClick(View view) {
        String typeStr = typeEdit.getText().toString().trim();
        if (info == null) {
            if (Utils.isEmpty(typeStr)) {
                DialogUIUtils.showToastCenterShort("请输入图书类型");
                return;
            } else {
                savaBookType(typeStr);
            }
        } else {
            updataBookType(typeStr);
        }
    }

    private void updataBookType(String type) {
        if (Utils.isEmpty(type)) {
            DialogUIUtils.showToastCenterShort("请输入图书类型");
            return;
        }
        DialogUIUtils.showLoadingHorizontal(context, "加载中...");
        info.setType(type);
        info.update(info.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                DialogUIUtils.dismiss();
                if (e == null) {
                    getBookTypes();
                    typeEdit.setText("");
                    info = null;
                    DialogUIUtils.showToastCenterShort("修改成功");
                } else {
                    DialogUIUtils.showToastCenterShort(e.getMessage());
                }
            }
        });
    }

    private void savaBookType(String type) {
        if (Utils.isEmpty(type)) {
            DialogUIUtils.showToastCenterShort("请输入图书类型");
            return;
        }
        DialogUIUtils.showLoadingHorizontal(context, "加载中...");
        BookTypeInfo bookTypeInfo = new BookTypeInfo();
        bookTypeInfo.setType(type);
        bookTypeInfo.setTypeId(String.valueOf(System.currentTimeMillis()));
        bookTypeInfo.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                DialogUIUtils.dismiss();
                if (e == null) {
                    getBookTypes();
                    typeEdit.setText("");
                    info = null;
                    DialogUIUtils.showToastCenterShort("添加成功");
                } else {
                    DialogUIUtils.showToastCenterShort(e.getMessage());
                }
            }
        });
    }


}
