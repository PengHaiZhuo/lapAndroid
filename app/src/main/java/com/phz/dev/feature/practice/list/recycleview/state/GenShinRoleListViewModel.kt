package com.phz.dev.feature.practice.list.recycleview.state

import androidx.lifecycle.MutableLiveData
import com.phz.common.state.BaseViewModel
import com.phz.dev.feature.practice.list.recycleview.model.GenShinRole

/**
 * @author phz on 2021/9/29
 * @description
 */
class GenShinRoleListViewModel : BaseViewModel() {
    var roles = MutableLiveData<ArrayList<GenShinRole>>()
}