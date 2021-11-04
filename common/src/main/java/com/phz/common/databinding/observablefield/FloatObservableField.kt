package com.phz.common.databinding.observablefield

import androidx.databinding.ObservableField

/**
 * @author phz
 * @description 自定义的Float类型ObservableField 提供了默认值，避免取值的时候还要判空
 */
class FloatObservableField(value: Float = 0f) : ObservableField<Float>(value) {

    override fun get(): Float {
        return super.get()!!
    }

}