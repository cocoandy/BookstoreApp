package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.modle.BookInfo;
import com.gxwz.wzxy.bookstoreapp.modle.BookTypeInfo;
import com.gxwz.wzxy.bookstoreapp.utils.Utils;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class EditBookActivity extends BaseActivity {
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
    BookInfo bookInfo;

    private List<String> data_list = new ArrayList<>();
    private ArrayAdapter<String> arr_adapter;
    private List<BookTypeInfo> bookTypeInfos = new ArrayList<>();

    public BookTypeInfo info = null;
    public BookTypeInfo defInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        ButterKnife.bind(this);
        bookInfo = (BookInfo) getIntent().getSerializableExtra("bookInfo");
        showBook();
        Log.e("TAghjkldsgj",bookInfo.toString());
        init();
        getBookTypes();
        toolbarBreak("更新图书");
    }

    @OnClick({R.id.book_type_submit, R.id.img_cover})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.book_type_submit:
                Log.e(TAG, tvDirectory.getText().toString());
                if (uri != null) {
                    uploadblock();
                } else {

                }

                break;
            case R.id.img_cover:
                Crop.pickImage(this);
                break;
        }

    }

    public void uploadblock() {
        if (uri == null) {
            return;
        }

        String name = tvName.getText().toString().trim();//书名
        String author = tvAuthor.getText().toString().trim();//作者
        String press = tvName.getText().toString().trim();//出版社
        String price = imgPrice.getText().toString().trim();//出版社
        String reume = tvResume.getText().toString().trim();//作者简介
        String introduction = tvIntroduction.getText().toString().trim();//作者简介
        String directory = tvDirectory.getText().toString().trim();//mu
        if (Utils.isEmpty(name)) {
            showShort("书名不能为空");
            return;
        }
        if (Utils.isEmpty(author)) {
            showShort("作者不能为空");
            return;
        }
        if (Utils.isEmpty(press)) {
            showShort("出版社不能为空");
            return;
        }
        if (Utils.isEmpty(reume)) {
            showShort("作者简介不能为空");
            return;
        }
        if (Utils.isEmpty(introduction)) {
            showShort("图书简介不能为空");
            return;
        }
        if (Utils.isEmpty(directory)) {
            showShort("目录不能为空");
            return;
        }
        if (info == null) {
            showShort("类型未选择");
            return;
        }

       showProgress();
        final BmobFile bmobFile = new BmobFile(new File(getRealFilePath(this, uri)));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    showShort("上传文件成功");
                    saveBook(bmobFile.getFileUrl());

                } else {
                    showShort("上传文件失败：" + e.getMessage());
                   closeProgress();
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });
    }

    public void showBook() {

        Glide.with(context)
                .load(bookInfo.getCover()).error(R.mipmap.ic_launcher)
                .into(imgCover);
        tvName.setText(bookInfo.getName());
        tvAuthor.setText(bookInfo.getAuthor());
        tvPress.setText(bookInfo.getPress());
        imgPrice.setText(bookInfo.getPrice());
        tvDirectory.setText(bookInfo.getDirectory());
        tvIntroduction.setText(bookInfo.getIntroduction());
        tvResume.setText(bookInfo.getReume());
        defInfo = bookInfo.getType();

    }

    Uri uri;
    String path;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
    }

    public void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(this.getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    public void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            uri = Crop.getOutput(result);
            Glide.with(context)
                    .load(Crop.getOutput(result)).error(R.mipmap.ic_launcher)
                    .into(imgCover);
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
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
        for (int i = 0; i < data_list.size(); i++) {
            if (data_list.get(i).equals(defInfo.getType())) {
                spinner.setSelection(i, true);
                return;
            }
        }

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


    public void saveBook(String url) {
        String bookId = System.currentTimeMillis() + "";//编号
        String cover = url == null ? bookInfo.getCover() : url;//封面
        String name = tvName.getText().toString().trim();//书名
        String author = tvAuthor.getText().toString().trim();//作者
        String press = tvName.getText().toString().trim();//出版社
        String price = imgPrice.getText().toString().trim();//出版社
        String reume = tvResume.getText().toString().trim();//作者简介
        String introduction = tvIntroduction.getText().toString().trim();//作者简介
        String directory = tvDirectory.getText().toString().trim();//mu
        if (Utils.isEmpty(name)) {
            showShort("书名不能为空");
        }
        if (Utils.isEmpty(author)) {
            showShort("作者不能为空");
        }
        if (Utils.isEmpty(press)) {
            showShort("出版社不能为空");
        }
        if (Utils.isEmpty(reume)) {
            showShort("作者简介不能为空");
        }
        if (Utils.isEmpty(introduction)) {
            showShort("图书简介不能为空");
        }
        if (Utils.isEmpty(directory)) {
            showShort("目录不能为空");
        }
        if (info == null) {
            showShort("类型未选择");
        }

        BookInfo bookInfo = new BookInfo(bookId, cover, name, author, press, 0.0, price, info, 0, 0, reume, introduction, directory);
        bookInfo.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    showShort("修改成功");
                }
            }
        });
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
