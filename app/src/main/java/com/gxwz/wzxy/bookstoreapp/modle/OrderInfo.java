package com.gxwz.wzxy.bookstoreapp.modle;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by crucy on 2017/11/8.
 */

public class OrderInfo extends BmobObject{

    public List<ShopCarInfo> shopCarInfos;
    public AddressInfo addressInfo;
    public String total;

    public List<ShopCarInfo> getShopCarInfos() {
        return shopCarInfos;
    }

    public void setShopCarInfos(List<ShopCarInfo> shopCarInfos) {
        this.shopCarInfos = shopCarInfos;
    }

    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
