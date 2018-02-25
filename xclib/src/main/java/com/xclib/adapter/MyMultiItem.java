package com.xclib.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by xiongch on 2018/1/5.
 */

public class MyMultiItem implements MultiItemEntity {

    private int itemType;
    private Object data;

    public MyMultiItem(Object data, int itemType){
        this.data = data;
        this.itemType = itemType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
