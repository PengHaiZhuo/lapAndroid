package com.phz.common.page.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * @author phz on 2021/8/26
 * @description 普通BaseRecycleViewHolder
 */
abstract class BaseRecycleViewHolder<T, D : ViewDataBinding>
    (parent: ViewGroup, layoutId: Int) :
    RecyclerView.ViewHolder(
        DataBindingUtil.inflate<D>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        ).root
    ) {
    //主构造方法调用后，可以直接获取，不会返回null
    protected var mViewBinding: ViewDataBinding = DataBindingUtil.getBinding(this.itemView)!!

    /**
     * 写个方法让RecycleView.Adapter在onBindViewHolder里调用，方便onBind处理
     */
    fun bind(bean: T, position: Int) {
        onBind(bean,position)
        //默认情况，数据改变时，绑定不会马上刷新视图
        //调用executePendingBindings方法：评估挂起的绑定，更新任何将表达式绑定到修改变量的视图
        mViewBinding.executePendingBindings()
    }

    /**
     * 写个方法方便adapter里处理数据与UI
     */
    abstract fun onBind(bean:T,position: Int)
}