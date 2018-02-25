package com.xclib.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by xiongchang on 2017/7/12.
 */

public class FragmentVpAdapter extends FragmentPagerAdapter {
    //    这个是viewpager的填充视图
    private List<Fragment> views;
    //    这个是table导航条里面的内容填充
    private List<String> tabstrs;

    public FragmentVpAdapter(FragmentManager fm, List<Fragment> views, List<String> tabstrs) {
        super(fm);
        this.views = views;
        this.tabstrs = tabstrs;
    }

    @Override
    public Fragment getItem(int position) {
        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        if (tabstrs.size()!=0){
            return tabstrs.get(position);
        }
        return "";
    }
}
