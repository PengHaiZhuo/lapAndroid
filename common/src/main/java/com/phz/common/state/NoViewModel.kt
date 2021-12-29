package com.phz.common.state

import androidx.lifecycle.ViewModel

/**
 * @author phz
 * @description 空的ViewModel
 */
open class NoViewModel : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        //ViewModel销毁时会调用，常用于释放一些资源，避免泄漏
    }
}