package com.phz.dev.feature.practice.popupwindow.dropdownmenu.state;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;

/**
 * @author phz
 * @description
 */
public class DropDownMenuViewModel extends ViewModel {
    private ObservableInt selectMenu=new ObservableInt();
    {
        //默认没有显示任何菜单项
        selectMenu.set(0);
    }

    public ObservableInt getSelectMenu() {
        return selectMenu;
    }
}
