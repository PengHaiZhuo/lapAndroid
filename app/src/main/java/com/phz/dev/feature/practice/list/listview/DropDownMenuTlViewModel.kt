package com.phz.dev.feature.practice.list.listview

import androidx.lifecycle.MutableLiveData
import com.phz.common.state.BaseViewModel
import com.phz.dev.feature.practice.list.listview.model.ProjectBean

/**
 * @author phz on 2021/9/28
 * @description
 */
class DropDownMenuTlViewModel:BaseViewModel() {
    var indexOne = MutableLiveData<Int>()
    var indexTwo = MutableLiveData<Int>()
}