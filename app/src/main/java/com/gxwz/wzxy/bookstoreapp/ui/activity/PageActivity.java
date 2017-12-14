package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gxwz.wzxy.bookstoreapp.R;

public class PageActivity extends AppCompatActivity {
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startActivity(new Intent(PageActivity.this,MainActivity.class));
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        handler.sendEmptyMessageDelayed(0,3*1000);

    }
}
