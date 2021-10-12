package com.phz.common.state

import androidx.lifecycle.ViewModel
import com.kunminx.architecture.ui.callback.UnPeekLiveData

/**
 * @author phz
 * @description 简单封装了一下，加了个showLoading加载框显示和隐藏,
 * 子类可以继承后添加showError、showEmpty、showNormal等变量，配合LoadSir库或者自己试用占位符ViewStub去实现ui局部状态的改变
 */
open class BaseViewModel : ViewModel() {

    val showLoading by lazy { UnPeekLiveData<Boolean>() }

    override fun onCleared() {
        super.onCleared()
        //ViewModel销毁时会调用，常用于释放一些资源，避免泄漏
    }

}