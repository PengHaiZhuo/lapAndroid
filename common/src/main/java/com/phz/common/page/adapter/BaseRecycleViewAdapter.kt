package com.phz.common.page.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.phz.common.page.adapter.viewholder.BaseRecycleViewHolder

/**
 * @author phz on 2021/8/26
 * @description 普通RecycleViewAdapter{ T:item的泛型  D:item布局的ViewDataBinding}
 * @notice 除非图方便，不然请使用普通的ListAdapter然后处理DiffUtil
 */
abstract class BaseRecycleViewAdapter<T, D : ViewDataBinding> :
    RecyclerView.Adapter<BaseRecycleViewHolder<T, D>>() {

    private var mList: MutableList<T> = mutableListOf()

    override fun onBindViewHolder(holder: BaseRecycleViewHolder<T, D>, position: Int) {
        val bean = mList[position]
        holder.bind(bean, position)
    }

    override fun getItemCount(): Int = mList.size

    /**
     * 全局刷新
     */
    fun addAll(list: MutableList<T>) {
        mList = list
        notifyDataSetChanged()
    }

    /**
     * 添加到末尾
     */
    fun addItems(list: MutableList<T>) {
        val size=mList.size
        mList.addAll(list)
        notifyItemRangeChanged(size,list.size)
    }

    /**
     * 清楚列表并刷新
     */
    fun clear() {
        mList.clear()
        notifyDataSetChanged()
    }

    /**
     * 部分删除
     */
    fun removeItems(list: MutableList<T>) {
        this.mList.retainAll(list)
        notifyDataSetChanged()
    }

    /**
     * 删除某个特定项
     */
    fun removeItem(obj: T) {
        this.mList.remove(obj)
        notifyDataSetChanged()
    }

    fun getList(): MutableList<T> {return mList}

    override fun onBindViewHolder(
        holder: BaseRecycleViewHolder<T, D>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
}
