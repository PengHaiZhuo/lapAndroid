package com.phz.dev.feature.practice.list.recycleview.adapter.diff

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.phz.dev.feature.practice.list.recycleview.model.GenShinRole

/**
 * @author phz on 2021/9/29
 * @description
 */
class GenShinRoleDiffCallback : DiffUtil.ItemCallback<GenShinRole>() {
    companion object {
        const val KEY_NAME = "NAME"
        const val KEY_IMG = "IMG"
        const val KEY_DES = "DES"
    }

    override fun areItemsTheSame(oldItem: GenShinRole, newItem: GenShinRole): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GenShinRole, newItem: GenShinRole): Boolean {
        //当areItemsTheSame返回true，代表2个对象是同一个Item，在此方法进行细节比较
        return oldItem.imgResId == newItem.imgResId || oldItem.des == newItem.des || oldItem.name == newItem.name
    }

    override fun getChangePayload(oldItem: GenShinRole, newItem: GenShinRole): Any? {
        //获取有关更改的有效负载,具体处理移步Adapter的onBindViewHolder方法
        //这样使用可以实现子item的局部刷新
        var diffBundle = Bundle()
        if (oldItem.name != newItem.name) {
            diffBundle.putString(KEY_NAME, newItem.name)
        }
        if (oldItem.imgResId != newItem.imgResId) {
            diffBundle.putInt(KEY_IMG, newItem.imgResId)
        }
        if (oldItem.des != newItem.des) {
            diffBundle.putString(KEY_DES, newItem.des)
        }
        if (diffBundle.size() == 0) return null
        return diffBundle
    }
}