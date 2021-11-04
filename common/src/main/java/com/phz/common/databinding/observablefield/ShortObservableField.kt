package com.phz.common.databinding.observablefield

import androidx.databinding.ObservableField

/**
 * @author phz
 * @description 自定义的Short类型ObservableField 提供了默认值，避免取值的时候还要判空
 */
class ShortObservableField(value: Short = 0) : ObservableField<Short>(value) {

    override fun get(): Short {
        return super.get()!!
    }

}