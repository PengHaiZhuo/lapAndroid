package com.phz.dev.feature.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.phz.common.databinding.observablefield.StringObservableField
import com.phz.common.ext.logE
import com.phz.common.state.BaseViewModel
import com.phz.dev.data.model.UserBean
import com.phz.dev.ext.request
import com.phz.dev.net.apiService
import com.phz.dev.util.PersistenceUtil
import kotlinx.coroutines.launch

/**
 * @author phz on 2021/8/17
 * @description
 */
class LoginViewModel : BaseViewModel() {
    val userName = StringObservableField()
    val pwd = StringObservableField()
    val userBeanLiveData = MutableLiveData<UserBean>()//登陆返回信息

    init {
        PersistenceUtil.getUserName().let {
            userName.set(it)
        }
        PersistenceUtil.getPWD().let {
            pwd.set(it)
        }
    }

    fun login(name: String, pwd: String) {
        viewModelScope.launch {
            request({ apiService.login(name, pwd) }, {
                userBeanLiveData.value = it
            }, {
                ToastUtils.showShort("登录失败，错误码${it.errorCode}")
                "message:${it.errorMsg}    code:${it.errorCode}".logE()
            })
        }
    }
}