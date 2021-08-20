package com.phz.common.databinding.observablefield

import androidx.databinding.ObservableField

/**
 * @author phz
 * @description 自定义的Int类型ObservableField 提供了默认值，避免取值的时候还要判空
 */
class IntObservableField(value: Int = 0) : ObservableField<Int>(value) {

    override fun get(): Int {
        return super.get()!!
    }

}