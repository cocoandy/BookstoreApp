package com.gxwz.wzxy.bookstoreapp.ui.frgment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.adapter.BookInfoAdapter;
import com.gxwz.wzxy.bookstoreapp.base.BaseFragment;
import com.gxwz.wzxy.bookstoreapp.base.BaseRecycleAdapter;
import com.gxwz.wzxy.bookstoreapp.modle.BookInfo;
import com.gxwz.wzxy.bookstoreapp.ui.activity.BookDetailsActivity;
import com.gxwz.wzxy.bookstoreapp.ui.activity.BooksActivity;
import com.gxwz.wzxy.bookstoreapp.ui.activity.BooksMainActivity;
import com.gxwz.wzxy.bookstoreapp.ui.activity.CommentActivity;
import com.gxwz.wzxy.bookstoreapp.ui.activity.LoginActivity;
import com.gxwz.wzxy.bookstoreapp.ui.activity.MainActivity;
import com.gxwz.wzxy.bookstoreapp.ui.activity.PayActivity;
import com.gxwz.wzxy.bookstoreapp.utils.GlideImageLoader;
import com.gxwz.wzxy.bookstoreapp.view.RecycleViewDivider;
import com.youth.banner.Banner;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by crucy on 2017/10/28.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.recycle)
    RecyclerView recycle;
    Banner mBanner;

    List<BookInfo> bookInfos = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    BookInfoAdapter adapter;

    String[] url = new String[]{
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3711681965,2673607050&fm=27&gp=0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513137685245&di=628c86912edeeeefde629d50c50f2526&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0194c857e62cee0000012e7ef24a0e.jpg%40900w_1l_2o_100sh.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513137685256&di=594bb2187b68a062a3c91f7d7140707f&imgtype=0&src=http%3A%2F%2Fpic.qiantucdn.com%2F58pic%2F19%2F63%2F89%2F00V58PICwQa_1024.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3308251966,730227294&fm=27&gp=0.jpg"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iniData();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadingBooks();
        } else {
        }
    }

    @OnClick({R.id.screach_books})
    public void onClick(View view) {

        context.startActivity(new Intent(context, BooksActivity.class));
//        new BlurPopupWindow.Builder(v.getContext())
//                .setContentView(R.layout.layout_dialog_like)
//                .bindClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(v.getContext(), "Click Button", Toast.LENGTH_SHORT).show();
//                    }
//                }, R.id.dialog_like_bt)
//                .setGravity(Gravity.CENTER)
//                .setScaleRatio(0.2f)
//                .setBlurRadius(10)
//                .setTintColor(0x30000000)
//                .build()
//                .show();

//        startActivity(new Intent(context, BooksMainActivity.class));
    }

    private void iniData() {
        initRecycle();
        carouselPicture(url);
    }

    private void loadingBooks() {
        BmobQuery<BookInfo> queue = new BmobQuery<>();
        queue.include("type");
        queue.addWhereEqualTo("status",0);
        queue.findObjects(new FindListener<BookInfo>() {
            @Override
            public void done(List<BookInfo> list, BmobException e) {
                if (list != null) {
                    bookInfos.clear();
                    bookInfos.addAll(list);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 初始化数控数据列表
     */
    private void initRecycle() {
        adapter = new BookInfoAdapter(context, bookInfos);
        adapter.setItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecycleAdapter.ViewHolder holder, int position) {

                Intent intent = new Intent(context, BookDetailsActivity.class);
                intent.putExtra("bookId", bookInfos.get(position).getObjectId());
                intent.putExtra("book", bookInfos.get(position));
                startActivity(intent);
            }

            @Override
            public void onHeadClick(BaseRecycleAdapter.ViewHolder holder, int position) {

            }

            @Override
            public void onFootClick(BaseRecycleAdapter.ViewHolder holder, int position) {

            }
        });

        //创建默认的线性LayoutManager
        linearLayoutManager = new LinearLayoutManager(context);
        recycle.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recycle.setHasFixedSize(true);
        //创建并设置Adapter
        recycle.setAdapter(adapter);
        recycle.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        setHeadView();
    }

    /**
     * 加载轮播图片
     *
     * @param urls
     */
    private void carouselPicture(String[] urls) {
        // 拼接地址并保存到成员中
        if (urls != null && urls.length > 0) {
            //设置图片加载器
            mBanner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            mBanner.setImages(Arrays.asList(urls));
            //banner设置方法全部调用完毕时最后调用
            mBanner.start();
        }
    }

    public void setHeadView() {
        View head = LayoutInflater.from(getActivity()).inflate(R.layout.view_banner, recycle, false);
        mBanner = (Banner) head.findViewById(R.id.banner);
        adapter.addHeaderView(head);
    }


}
