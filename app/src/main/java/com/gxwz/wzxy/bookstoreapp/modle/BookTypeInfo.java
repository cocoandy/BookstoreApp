package com.gxwz.wzxy.bookstoreapp.modle;

import cn.bmob.v3.BmobObject;

/**
 * Created by crucy on 2017/10/28.
 */

public class BookTypeInfo extends BmobObject {
    String type;//类型
    String typeId;//编号

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "BookTypeInfo{" +
                "type='" + type + '\'' +
                ", typeId='" + typeId + '\'' +
                '}';
    }
}
