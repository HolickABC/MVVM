package com.xiongch.mvvm.ui.index.bean;

import com.xclib.adapter.MyMultiItem;

/**
 * Created by xiongch on 2018/1/5.
 */

public class IndexItem extends MyMultiItem {

    public static final int CAROUSEL = 0;
    public static final int TEXT = 1;

    public IndexItem(Object data, int itemType) {
        super(data, itemType);
    }
}
