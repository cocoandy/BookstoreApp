package com.gxwz.wzxy.bookstoreapp.ui.frgment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.dou361.dialogui.DialogUIUtils;
import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.base.BaseFragment;
import com.gxwz.wzxy.bookstoreapp.modle.BookInfo;
import com.gxwz.wzxy.bookstoreapp.modle.BookTypeInfo;
import com.gxwz.wzxy.bookstoreapp.modle.TypeInfo;
import com.gxwz.wzxy.bookstoreapp.utils.GlideRoundTransformUtils;
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
import cn.bmob.v3.listener.UploadFileListener;

import static android.app.Activity.RESULT_OK;

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

    @OnClick({R.id.book_type_submit, R.id.img_cover})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.book_type_submit:
                Log.e(TAG, tvDirectory.getText().toString());
                uploadblock();
                break;
            case R.id.img_cover:
                Crop.pickImage(getActivity());
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
            DialogUIUtils.showToastShort("书名不能为空");
            return;
        }
        if (Utils.isEmpty(author)) {
            DialogUIUtils.showToastShort("作者不能为空");
            return;
        }
        if (Utils.isEmpty(press)) {
            DialogUIUtils.showToastShort("出版社不能为空");
            return;
        }
        if (Utils.isEmpty(reume)) {
            DialogUIUtils.showToastShort("作者简介不能为空");
            return;
        }
        if (Utils.isEmpty(introduction)) {
            DialogUIUtils.showToastShort("图书简介不能为空");
            return;
        }
        if (Utils.isEmpty(directory)) {
            DialogUIUtils.showToastShort("目录不能为空");
            return;
        }
        if (info == null) {
            DialogUIUtils.showToastShort("类型未选择");
            return;
        }

        DialogUIUtils.showToastCenterLong("请稍后...");
        final BmobFile bmobFile = new BmobFile(new File(getRealFilePath(getActivity(), uri)));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    DialogUIUtils.showToastShort("上传文件成功");
                    saveBook(bmobFile.getFileUrl());

                } else {
                    DialogUIUtils.showToastShort("上传文件失败：" + e.getMessage());
                    DialogUIUtils.dismiss();
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });
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
        Uri destination = Uri.fromFile(new File(getActivity().getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(getActivity());
    }

    public void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            uri = Crop.getOutput(result);
            Glide.with(context)
                    .load(Crop.getOutput(result)).error(R.mipmap.ic_launcher)
                    .into(imgCover);
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(getActivity(), Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
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


    public void saveBook(String url) {
        String bookId = System.currentTimeMillis() + "";//编号
        String cover = url;//封面
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
