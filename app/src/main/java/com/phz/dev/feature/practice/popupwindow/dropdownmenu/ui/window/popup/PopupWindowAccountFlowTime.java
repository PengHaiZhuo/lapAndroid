package com.phz.dev.feature.practice.popupwindow.dropdownmenu.ui.window.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.phz.common.util.ActivityManagerKtx;
import com.phz.dev.R;
import com.phz.dev.app.MyApplication;
import com.phz.dev.feature.practice.popupwindow.dropdownmenu.config.Constant;
import com.phz.dev.feature.practice.popupwindow.dropdownmenu.ui.widget.DateSelector;
import com.phz.dev.feature.practice.popupwindow.dropdownmenu.util.TimeUtil;
import com.phz.dev.feature.practice.popupwindow.dropdownmenu.util.Util;

import java.util.Date;

/**
 * @author phz
 * @introduction 账户流水 菜单弹出框
 */
public class PopupWindowAccountFlowTime extends PopupWindow {
    private TextView tvTimeStart;
    private TextView tvTimeEnd;
    private Button btCancel;
    private Button btSure;
    private View rootView;

    private SelectTimeFinishListener selectTimeFinishListener;

    private Context mContext;
    //开始日期
    private String stringBeginDate;
    //结束日期
    private String stringEndDate;
    /**
     * 开始日期选择器
     */
    private DateSelector dateSelectorBegin;
    /**
     * 结束日期选择器
     */
    private DateSelector dateSelectorEnd;

    public PopupWindowAccountFlowTime(Context context, SelectTimeFinishListener selectTimeFinishListener) {
        super(context);
        this.mContext=context;
        this.selectTimeFinishListener=selectTimeFinishListener;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.popup_account_flow_time, null);
        this.setWidth(Util.getScreenWidth(MyApplication.instance));
        this.setHeight(Util.getScreenHeight(MyApplication.instance) / 3);
        this.setContentView(rootView);
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setOutsideTouchable(true);
        this.setFocusable(true);
        initView();
    }

    private void initView() {
        tvTimeStart=rootView.findViewById(R.id.tv_time_start);
        tvTimeEnd=rootView.findViewById(R.id.tv_time_end);
        btCancel=rootView.findViewById(R.id.bt_cancel);
        btSure=rootView.findViewById(R.id.bt_sure);
        tvTimeStart.setOnClickListener(v -> {
            dateSelectorBegin = new DateSelector(ActivityManagerKtx.INSTANCE.getCurrentActivity(), time -> {
                tvTimeStart.setText(time);
                stringBeginDate = time;
            }, Constant.START_TIME, Constant.END_TIME, TimeUtil.format(new Date(),"yyyy-MM-dd"));
            if (TextUtils.isEmpty(stringBeginDate)) {
                dateSelectorBegin.show(TimeUtil.format(new Date(),"yyyy-MM-dd"));
            } else {
                dateSelectorBegin.show(stringBeginDate);
            }
        });
        tvTimeEnd.setOnClickListener(v -> {
            dateSelectorEnd = new DateSelector(ActivityManagerKtx.INSTANCE.getCurrentActivity(), time -> {
                tvTimeEnd.setText(time);
                stringEndDate = time;
            }, Constant.START_TIME, Constant.END_TIME,TimeUtil.format(new Date(),"yyyy-MM-dd"));
            if (TextUtils.isEmpty(stringEndDate)) {
                dateSelectorEnd.show(TimeUtil.format(new Date(),"yyyy-MM-dd"));
            } else {
                dateSelectorEnd.show(stringEndDate);
            }
        });
        btSure.setOnClickListener(v -> {
            if (TextUtils.isEmpty(tvTimeStart.getText().toString().trim())){
                Toast.makeText(mContext,"请选择开始日期",Toast.LENGTH_LONG).show();
            }else if (TextUtils.isEmpty(tvTimeEnd.getText().toString().trim())){
                Toast.makeText(mContext,"请选择结束日期",Toast.LENGTH_LONG).show();
            }else {
                selectTimeFinishListener.time(stringBeginDate,stringEndDate);
                this.dismiss();
            }
        });
        btCancel.setOnClickListener(v->{
            this.dismiss();
        });
    }

    public interface SelectTimeFinishListener{
        void time(String stringBeginDate, String stringEndDate);
    }
}
