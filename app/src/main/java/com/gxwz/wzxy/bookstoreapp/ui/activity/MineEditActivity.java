package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.modle.UserInfo;
import com.gxwz.wzxy.bookstoreapp.utils.Constant;
import com.gxwz.wzxy.bookstoreapp.utils.GlideRoundTransformUtils;
import com.soundcloud.android.crop.Crop;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class MineEditActivity extends BaseActivity {
    @BindView(R.id.user_cover)
    ImageView cover;
    @BindView(R.id.mine_edit_nickname)
    EditText name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_edit);
        ButterKnife.bind(this);
        toolbarBreak("我的信息");
        userInfo = currentUser();
        Glide.with(context)
                .load(userInfo.getCover()).error(R.mipmap.ic_launcher)
                .transform(new CenterCrop(context), new GlideRoundTransformUtils(context, 15))
                .into(cover);
        name.setText(userInfo.getNickname());

    }

    @OnClick({R.id.user_cover, R.id.mine_edit_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_cover:
                Crop.pickImage(this);

                break;
            case R.id.mine_edit_submit:
                uploadblock();
                break;
        }
    }

    public void updataUser(String url) {
        UserInfo newUser = new UserInfo();
        if (url!=null)
            newUser.setCover(url);
        newUser.setNickname(name.getText().toString());
        BmobUser bmobUser = BmobUser.getCurrentUser();
        newUser.update(bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                closeProgress();
                if (e == null) {
                    showShort("更新用户信息成功");
                    sendBroadcast(new Intent(Constant.Broadcast.LOGIN_SUCCESS));
                } else {
                    showShort("更新用户信息失败");
                }
            }
        });
    }

    Uri uri;
    String url;
    public void uploadblock() {
        if (uri==null){
            updataUser(url);
            return;
        }
        showProgress();
        final BmobFile bmobFile = new BmobFile(new File(getRealFilePath(this,uri)));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    showShort("上传文件成功" );
                    updataUser(bmobFile.getFileUrl());

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            uri = Crop.getOutput(result);

            Glide.with(context)
                    .load(Crop.getOutput(result)).error(R.mipmap.ic_launcher)
                    .transform(new CenterCrop(context), new GlideRoundTransformUtils(context, 15))
                    .into(cover);
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
