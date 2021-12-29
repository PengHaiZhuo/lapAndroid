package com.phz.common.state

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @author phz
 * @description 简单封装了一下，加了个showLoading加载框显示和隐藏,
 * 子类可以继承后添加showError、showEmpty、showNormal等变量，配合LoadSir库或者自己试用占位符ViewStub去实现ui局部状态的改变
 * 也可以封装网络请求库，在结果处处理状态
 */
open class ExampleViewModel : ViewModel() {

    val showLoading by lazy { MutableStateFlow(false) }

}