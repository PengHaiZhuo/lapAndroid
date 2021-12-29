package com.phz.dev.feature.practice.list.listview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author phz on 2021/9/28
 * @description
 */
class DropDownMenuTlViewModel: ViewModel() {
    var indexOne = MutableLiveData<Int>()
    var indexTwo = MutableLiveData<Int>()
}