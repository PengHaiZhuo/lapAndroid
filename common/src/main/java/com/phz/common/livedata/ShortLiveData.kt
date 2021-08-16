package com.phz.common.livedata

import androidx.lifecycle.MutableLiveData


/**
 * @author phz
 * @description 自定义的Short类型 MutableLiveData 提供了默认值，避免取值的时候还要判空
 */
class ShortLiveData : MutableLiveData<Short>() {

    override fun getValue(): Short {
        return super.getValue() ?: 0
    }
}