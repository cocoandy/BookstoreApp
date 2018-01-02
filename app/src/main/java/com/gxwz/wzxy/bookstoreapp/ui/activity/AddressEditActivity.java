package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.modle.AddressInfo;
import com.gxwz.wzxy.bookstoreapp.modle.JsonInfo;
import com.gxwz.wzxy.bookstoreapp.modle.UserInfo;
import com.gxwz.wzxy.bookstoreapp.utils.GetJsonDataUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class AddressEditActivity extends BaseActivity {
    @BindView(R.id.spinner_provice)
    Spinner spinnerP;
    @BindView(R.id.spinner_city)
    Spinner spinnerC;
    @BindView(R.id.spinner_area)
    Spinner spinnerA;
    @BindView(R.id.name)
    TextView mTvName;
    @BindView(R.id.phone)
    TextView mTvPhone;
    @BindView(R.id.detil_address)
    TextView mTvDetil;

    private ArrayList<JsonInfo> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    JsonInfo jsonInfo;
    JsonInfo.CityBean cityBean;
    String area;

    private List<String> provice_list = new ArrayList<>();
    private List<String> city_list = new ArrayList<>();
    private List<String> area_list = new ArrayList<>();

    private ArrayAdapter<String> p_adapter;
    private ArrayAdapter<String> c_adapter;
    private ArrayAdapter<String> a_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_edit);
        ButterKnife.bind(this);
        provice_list.add("省份");
        city_list.add("市级");
        area_list.add("地区");
        initP();
        initC();
        initA();
        initJsonData();
        toolbarBreak("新增地址");
    }

    public void initP() {
        //适配器
        p_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, provice_list);
        //设置样式
        p_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinnerP.setAdapter(p_adapter);
        spinnerP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jsonInfo = options1Items.get(i);
                cityBean = null;
                area = null;
                city_list.clear();
                for (JsonInfo.CityBean info : jsonInfo.getCityList()) {
                    city_list.add(info.getName());
                }
                c_adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void initC() {
        //适配器
        c_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, city_list);
        //设置样式
        c_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinnerC.setAdapter(c_adapter);
        spinnerC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cityBean = jsonInfo.getCityList().get(i);
                area = null;
                area_list.clear();
                for (String info : cityBean.getArea()) {
                    area_list.add(info);
                }
                a_adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    public void initA() {
        //适配器
        a_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, area_list);
        //设置样式
        a_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinnerA.setAdapter(a_adapter);
        spinnerA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                area = cityBean.getArea().get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @OnClick({R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (jsonInfo == null || cityBean == null || area == null) {
                    showShort("信息不完整");
                    return;
                }
                final AddressInfo addressInfo = new AddressInfo();
                addressInfo.setPhone(mTvPhone.getText().toString().trim());
                addressInfo.setName(mTvName.getText().toString().trim());
                addressInfo.setAddress(mTvDetil.getText().toString().trim());
                addressInfo.setProvice(jsonInfo.getName());
                addressInfo.setCity(cityBean.getName());
                addressInfo.setArea(area);
                addressInfo.setFlag((int) (System.currentTimeMillis()/1000));
                BmobUser bmobUser = BmobUser.getCurrentUser();
                UserInfo userInfo = new UserInfo();
                userInfo.setObjectId(bmobUser.getObjectId());
                addressInfo.setUserInfo(userInfo);
                addressInfo.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        finish();
                    }
                });
                break;
        }
    }


    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonInfo> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
        provice_list.clear();
        for (JsonInfo info : options1Items) {
            provice_list.add(info.getName());
        }
        p_adapter.notifyDataSetChanged();
    }

    public ArrayList<JsonInfo> parseData(String result) {//Gson 解析
        ArrayList<JsonInfo> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonInfo entity = gson.fromJson(data.optJSONObject(i).toString(), JsonInfo.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
}
