package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dou361.dialogui.DialogUIUtils;
import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.utils.AddressPickTask;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;

public class AddressEditActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_edit);
        ButterKnife.bind(this);
        DialogUIUtils.init(this);
    }

    @OnClick({R.id.address})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.address:
                selectAddress();
                break;
        }
    }

    public void  selectAddress(){
        AddressPickTask task = new AddressPickTask(this);
        task.setHideProvince(false);
        task.setHideCounty(false);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                DialogUIUtils.showToastShort("数据初始化失败");
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                if (county == null) {
                    DialogUIUtils.showToastShort(province.getAreaName() + city.getAreaName());
                } else {
                    DialogUIUtils.showToastShort(province.getAreaName() + city.getAreaName() + county.getAreaName());
                }
            }
        });
        task.execute("贵州", "毕节", "纳雍");
    }
}
