package com.phz.dev.feature.practice.popupwindow.dropdownmenu.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.gyf.immersionbar.ImmersionBar;
import com.phz.dev.R;
import com.phz.dev.databinding.ActivityDropDwonMenuBinding;
import com.phz.dev.feature.practice.popupwindow.dropdownmenu.adapter.AccountFlowMenuListAdapter;
import com.phz.dev.feature.practice.popupwindow.dropdownmenu.state.DropDownMenuViewModel;
import com.phz.dev.feature.practice.popupwindow.dropdownmenu.ui.window.popup.PopupWindowAccountFlowFee;
import com.phz.dev.feature.practice.popupwindow.dropdownmenu.ui.window.popup.PopupWindowAccountFlowTime;
import com.phz.dev.feature.practice.popupwindow.dropdownmenu.ui.window.popup.PopupWindowAccountFlowType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author phz
 * @description 下拉菜单界面
 */
public class DropDownMenuActivity extends AppCompatActivity implements View.OnClickListener{
    private Context mContext;
    private ActivityDropDwonMenuBinding binding;
    private DropDownMenuViewModel viewModel;

    private List<PopupWindow> popupWindowList;
    private PopupWindowAccountFlowType popupWindowBusinessType;
    private PopupWindowAccountFlowFee popupWindowFeeProject;
    private PopupWindowAccountFlowTime popupWindowAccountFlowTime;

    private AccountFlowMenuListAdapter businessTypeAdapter;
    private AccountFlowMenuListAdapter feeProjectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        binding= DataBindingUtil.setContentView(this,R.layout.activity_drop_dwon_menu);
        ViewModelProvider vmp=new ViewModelProvider(this);
        viewModel =vmp.get(DropDownMenuViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setVm(viewModel);
        binding.setOnClickListener(this);

        setSupportActionBar(binding.materialToolbar);
        binding.materialToolbar.setTitleCentered(true);
        binding.materialToolbar.setNavigationOnClickListener(v->{
            onBackPressed();
        });
        getSupportActionBar().setTitle("下拉菜单");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImmersionBar.with(this).statusBarColor(R.color.colorPrimary)
                .titleBar(binding.materialToolbar)
                .autoStatusBarDarkModeEnable(true)
                .init();

        popupWindowList = new ArrayList<>();
        String[] strings = getResources().getStringArray(R.array.bill_list);
        businessTypeAdapter = new AccountFlowMenuListAdapter(mContext, strings);
        popupWindowBusinessType = new PopupWindowAccountFlowType(mContext, businessTypeAdapter);
        popupWindowList.add(popupWindowBusinessType);

        String[] stringsNew = getResources().getStringArray(R.array.service_list);
        feeProjectAdapter = new AccountFlowMenuListAdapter(mContext, stringsNew);
        popupWindowFeeProject = new PopupWindowAccountFlowFee(mContext, feeProjectAdapter);
        popupWindowList.add(popupWindowFeeProject);

        popupWindowAccountFlowTime = new PopupWindowAccountFlowTime(mContext, (stringBeginDate, stringEndDate) -> {

        });
        popupWindowList.add(popupWindowAccountFlowTime);

        popupWindowBusinessType.setOnDismissListener(() -> resetMenu());
        popupWindowFeeProject.setOnDismissListener(() -> resetMenu());
        popupWindowAccountFlowTime.setOnDismissListener(() -> resetMenu());
    }

    /**
     * 复位顶部菜单
     */
    private void resetMenu() {
        viewModel.getSelectMenu().set(0);
        binding.viewLightTransparent.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cl_type:
                viewModel.getSelectMenu().set(1);
                popupWindowBusinessType.showAsDropDown(v);
                SystemClock.sleep(50);
                binding.viewLightTransparent.setVisibility(View.VISIBLE);
                break;
            case R.id.cl_project:
                viewModel.getSelectMenu().set(2);
                popupWindowFeeProject.showAsDropDown(v);
                SystemClock.sleep(50);
                binding.viewLightTransparent.setVisibility(View.VISIBLE);
                break;
            case R.id.cl_time:
                viewModel.getSelectMenu().set(3);
                popupWindowAccountFlowTime.showAsDropDown(v);
                SystemClock.sleep(50);
                binding.viewLightTransparent.setVisibility(View.VISIBLE);
                break;
        }
    }

}
