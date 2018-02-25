package com.xiongch.mvvm.ui.index.adapter;

import com.xclib.adapter.MyBaseMultiAdapter;
import com.xclib.adapter.MyBaseViewHolder;
import com.xiongch.mvvm.R;
import com.xiongch.mvvm.databinding.IndexFragmentBannerBinding;
import com.xiongch.mvvm.databinding.IndexFragmentTextBinding;
import com.xiongch.mvvm.ui.index.bean.IndexItem;

import java.util.List;

/**
 * Created by xiongch on 2018/1/5.
 */

public class IndexAdapter extends MyBaseMultiAdapter<IndexItem, MyBaseViewHolder> {

    public IndexAdapter() {
        super(null);
        addItemType(IndexItem.CAROUSEL, R.layout.index_fragment_banner);
        addItemType(IndexItem.TEXT,R.layout.index_fragment_text);
    }

    @Override
    protected void bind(MyBaseViewHolder helper, IndexItem item) {
        switch (item.getItemType()){
            case IndexItem.CAROUSEL:
                IndexFragmentBannerBinding bannerItemBinding = (IndexFragmentBannerBinding) helper.getBinding();
                List<Integer> datas = (List<Integer>) item.getData();
                bannerItemBinding.bannerView.setPages(datas, () -> new BannerViewHolder());
                bannerItemBinding.bannerView.start();
                break;

            case IndexItem.TEXT:
                IndexFragmentTextBinding textItemBinding = (IndexFragmentTextBinding) helper.getBinding();
                String str = (String) item.getData();
                textItemBinding.setData(str);
                helper.addOnClickListener(textItemBinding.textView.getId());
                break;
        }
    }
}
