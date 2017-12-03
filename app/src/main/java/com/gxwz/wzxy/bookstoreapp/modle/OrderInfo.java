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
    public String userName;
    public Integer flag;//0 未支付 1 已支付  2 代收 3 已收  5 退还中 6 已处理  4 带评论

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    @Override
    public String toString() {
        return super.toString();
    }
}
