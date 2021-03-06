package com.phz.dev.feature.practice.popupwindow.dropdownmenu.ui.window.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.phz.dev.R;
import com.phz.dev.app.MyApplication;
import com.phz.dev.feature.practice.popupwindow.dropdownmenu.adapter.AccountFlowMenuListAdapter;
import com.phz.dev.feature.practice.popupwindow.dropdownmenu.util.Util;

/**
 * @author phz
 * @introduction 账户流水 菜单弹出框
 */
public class PopupWindowAccountFlowType extends PopupWindow {
    private View rootView;
    private ListView listView;

    public PopupWindowAccountFlowType(Context context, AccountFlowMenuListAdapter myAdapter){
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView=inflater.inflate(R.layout.popup_account_flow, null);
        listView=rootView.findViewById(R.id.lv);
        this.setWidth(Util.getScreenWidth(MyApplication.instance));
        this.setHeight(Util.getScreenHeight(MyApplication.instance)/3);
        this.setContentView(rootView);
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setOutsideTouchable(true);
        this.setFocusable(true);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            myAdapter.setSelectPosition(position);
//            PopupWindowAccountFlowType.this.dismiss();
        });
    }

}
