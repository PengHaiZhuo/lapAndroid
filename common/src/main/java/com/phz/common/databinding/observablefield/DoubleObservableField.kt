package com.phz.common.databinding.observablefield

import androidx.databinding.ObservableField

/**
 * @author phz
 * @description 自定义的Double类型ObservableField 提供了默认值，避免取值的时候还要判空
 */
class DoubleObservableField(value: Double = 0.0) : ObservableField<Double>(value) {

    override fun get(): Double {
        return super.get()!!
    }

}