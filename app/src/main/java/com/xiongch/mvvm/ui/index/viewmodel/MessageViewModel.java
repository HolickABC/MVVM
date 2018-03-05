package com.xiongch.mvvm.ui.index.viewmodel;

import com.xclib.mvvm.BaseViewModel;
import com.xiongch.mvvm.ui.index.model.MessageModel;

/**
 * Created by xiongch on 2018/3/5.
 */

public class MessageViewModel extends BaseViewModel {

    private MessageModel messageModel;

    public MessageViewModel(MessageModel messageModel) {
        this.messageModel = messageModel;
    }
}
