package com.phz.common.page.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.phz.common.ext.view.clickNoRepeat

/**
 * @author phz on 2021/8/26
 * @description 普通RecycleViewAdapter{ T:item的泛型  D:item布局的ViewDataBinding}
 * @notice 为了图方便，没有使用DiffUtil，没有做局部刷新判断，全量刷新notifyDataSetChanged耗费很大，数据量多不建议使用
 */
abstract class BaseRecycleViewAdapter<T, D : ViewDataBinding> :
    RecyclerView.Adapter<BaseRecycleViewHolder<T, D>>() {

    private var mList: MutableList<T> = mutableListOf()

    /**
     * item点击监听
     */
    private var onItemClickListener: OnItemClickListener<T>? = null

    override fun onBindViewHolder(holder: BaseRecycleViewHolder<T, D>, position: Int) {
        val bean = mList[position]
        holder.bind(bean, position)
        onItemClickListener?.let { listener ->
            holder.itemView.clickNoRepeat {
                //设置监听并调用回调方法
                listener.onClick(bean, position)
            }
        }
    }

    override fun getItemCount(): Int = mList.size

    fun addAll(list: MutableList<T>) {
        mList = list
        notifyDataSetChanged()
    }

    fun addItems(list: MutableList<T>) {
        mList.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        mList.clear()
        notifyDataSetChanged()
    }

    fun removeItems(list: MutableList<T>) {
        this.mList.retainAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(obj: T) {
        this.mList.remove(obj)
        notifyDataSetChanged()
    }

    /**
     * 设置item点击监听
     */
    fun setOnItemClick(onClick:OnItemClickListener<T>){
        this.onItemClickListener=onClick
    }

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