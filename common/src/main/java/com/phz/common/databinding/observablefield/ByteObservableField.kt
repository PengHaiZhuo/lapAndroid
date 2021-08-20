package com.phz.common.databinding.observablefield

import androidx.databinding.ObservableField

/**
 * @author phz
 * @description 自定义的Byte类型ObservableField 提供了默认值，避免取值的时候还要判空
 */
class ByteObservableField(value: Byte = 0) : ObservableField<Byte>(value) {

    override fun get(): Byte {
        return super.get()!!
    }
}