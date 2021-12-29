package com.phz.dev.state

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phz.dev.data.model.UserBean

/**
 * @author phz
 * @description 应用级别的ViewModel,用于存放一些全局信息
 * @use 在任意页面获取appViewModel并添加订阅
 * { ****val appViewModel: AppViewModel by lazy { getAppViewModel<AppViewModel>() }*** }
 */
class AppViewModel : ViewModel() {
    val userBean = MutableLiveData<UserBean>()
}