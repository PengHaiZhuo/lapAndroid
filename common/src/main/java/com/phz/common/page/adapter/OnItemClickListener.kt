package com.phz.common.page.adapter

/**
 * @author phz on 2021/8/26
 * @description
 */
interface OnItemClickListener<T> {
    fun onClick(bean:T,position:Int)
}
