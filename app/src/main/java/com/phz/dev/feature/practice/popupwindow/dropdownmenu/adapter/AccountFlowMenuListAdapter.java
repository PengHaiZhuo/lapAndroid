package com.phz.dev.feature.practice.popupwindow.dropdownmenu.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.phz.dev.R;

/**
 * @author phz
 * @introduction 账户流水  普通菜单菜单列表填充器
 */
public class AccountFlowMenuListAdapter extends BaseListAdapter<String, AccountFlowMenuListAdapter.MyViewHolder> {

    public AccountFlowMenuListAdapter(Context context, String[] data) {
        super(context, data);
    }

    @Override
    protected MyViewHolder newViewHolder(View convertView) {
        return new MyViewHolder(convertView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_simple_text;
    }

    @Override
    protected void convert(MyViewHolder holder, String item, int position) {
        holder.tv.setText(item);
        int mSelectPosition=getSelectPosition();
        if (mSelectPosition != -1) {
            if (position == mSelectPosition) {
                holder.tv.setSelected(true);
                holder.tv.setCompoundDrawablesWithIntrinsicBounds(0, 0,  R.drawable.ic_checked_right, 0);
            } else {
                holder.tv.setSelected(false);
                holder.tv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        }
    }

    class MyViewHolder {
        private TextView tv;

        public MyViewHolder(View convertView) {
            tv=convertView.findViewById(R.id.tv_item_simple);
        }
    }
}

