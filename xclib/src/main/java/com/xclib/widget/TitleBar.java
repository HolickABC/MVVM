package com.xclib.widget;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xclib.R;


public class TitleBar {
    TextView tv_title;
    TextView tv_left;
    TextView tv_right;
    Activity act;
    View rootView;
    ImageView iv_left;
    ImageView iv_right;
    LinearLayout ll_left;
    RelativeLayout ll_right;
    TextView tv_talk_num;
    RelativeLayout rl_titlebar;
    ImageView iv_alarm_tip, imgTelBook;
    String title;
    TextView tvFunction;
    View seperator;

    public void setTitlebar_background(int color) {
        this.rl_titlebar.setBackgroundColor(color);

    }

    public TitleBar(Activity act, View rootView, String title, int bgcolor, int titlecolor) {
        this.act = act;
        this.title = title;
        this.rootView = rootView;
        rl_titlebar = (RelativeLayout) this.rootView.findViewById(R.id.rl_titlebar);
        rl_titlebar.setBackgroundColor(bgcolor);
        tv_title = (TextView) this.rootView.findViewById(R.id.tv_title);
        tv_title.setText(title);
        tv_title.setTextColor(titlecolor);
    }

    public TitleBar(Activity act, View rootView, int id, int bgcolor, int titlecolor) {
        this.act = act;
        this.title = act.getString(id);
        this.rootView = rootView;
        rl_titlebar = (RelativeLayout) this.rootView.findViewById(R.id.rl_titlebar);
        rl_titlebar.setBackgroundColor(bgcolor);
        tv_title = (TextView) this.rootView.findViewById(R.id.tv_title);
        tv_title.setText(id);
        tv_title.setTextColor(titlecolor);
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

    public TextView getTv_talk_num() {
        return tv_talk_num;
    }

    public void setTv_talk_num(TextView tv_talk_num) {
        this.tv_talk_num = tv_talk_num;
    }

    public TextView getTv_left() {
        return tv_left;
    }

    public TextView getTv_right() {
        return tv_right;
    }

    public ImageView getIv_right() {
        return iv_right;
    }

    public TextView getTv_title() {
        return tv_title;
    }

    public RelativeLayout getRl_titlebar() {
        return rl_titlebar;
    }

    public void setIv_left(int resId, OnClickListener listener) {
        ll_left = (LinearLayout) rootView.findViewById(R.id.ll_left);
        iv_left = (ImageView) rootView.findViewById(R.id.iv_left);
        iv_left.setImageResource(resId);
        ll_left.setOnClickListener(listener);
    }

    public void setTv_Right(String text, int color, OnClickListener listener) {
        tvFunction = (TextView) rootView.findViewById(R.id.tvAddFunction);
        tvFunction.setText(text);
        tvFunction.setTextColor(color);
        tvFunction.setVisibility(View.VISIBLE);
        tvFunction.setOnClickListener(listener);
    }


    public void setIv_right(int resId, OnClickListener listener) {
        ll_right = (RelativeLayout) rootView.findViewById(R.id.ll_right);
        iv_right = (ImageView) rootView.findViewById(R.id.iv_right);
        iv_right.setImageResource(resId);
        ll_right.setOnClickListener(listener);
    }

    public void showAlarmTip() {
        if (iv_alarm_tip == null) {
            iv_alarm_tip = (ImageView) rootView.findViewById(R.id.iv_alarm_tip);
        }
        iv_alarm_tip.setVisibility(View.VISIBLE);
    }

    public void hideAlarmTip() {
        if (iv_alarm_tip == null) {
            iv_alarm_tip = (ImageView) rootView.findViewById(R.id.iv_alarm_tip);
        }
        iv_alarm_tip.setVisibility(View.INVISIBLE);
    }

    public ImageView getIvLeft(){
        iv_left = (ImageView) rootView.findViewById(R.id.iv_left);
        return iv_left;
    }

    public void setSepetatorLineVisible(boolean visible){
        seperator = rootView.findViewById(R.id.separator_line);
        if(visible){
            seperator.setVisibility(View.VISIBLE);
        }else {
            seperator.setVisibility(View.GONE);
        }
    }
}
