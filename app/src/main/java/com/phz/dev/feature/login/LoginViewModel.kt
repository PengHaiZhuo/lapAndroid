package com.phz.dev.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phz.common.databinding.observablefield.StringObservableField
import com.phz.dev.api.DataState
import com.phz.dev.data.model.UserBean
import com.phz.dev.repo.WanAndroidRepo
import com.phz.dev.util.PersistenceUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author phz on 2021/8/17
 * @description
 */
@HiltViewModel
class LoginViewModel @Inject constructor(private val wanAndroidRepo: WanAndroidRepo) : ViewModel() {
    val userName = StringObservableField()
    val pwd = StringObservableField()
    init {
        PersistenceUtil.getUserName().let {
            userName.set(it)
        }
        PersistenceUtil.getPWD().let {
            pwd.set(it)
        }
    }

    var userBean = MutableStateFlow<DataState<UserBean>>(DataState.Empty)
    fun login(name: String, pwd: String) {
        viewModelScope.launch {
            userBean.value=DataState.Loading
            val result=wanAndroidRepo.login(name,pwd)
            if (result.isSuccess()){
                userBean.value=DataState.Success(result.read())
            }else{
                userBean.value=DataState.Error(result.errorMessage())
            }
        }
    }
}