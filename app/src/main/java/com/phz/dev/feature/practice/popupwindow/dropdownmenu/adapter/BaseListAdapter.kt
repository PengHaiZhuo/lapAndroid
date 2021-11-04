package com.phz.dev.feature.practice.popupwindow.dropdownmenu.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

/**
 * @author phz on 2021/8/31
 * @description
 */
abstract class BaseListAdapter<T,H>(private var mContext: Context) :BaseAdapter() {
    private var mData=ArrayList<T>()
    private var convertView: View? = null

    /**
     * 当前点击的条目
     */
    private var mSelectPosition = -1


    constructor(context: Context,list:List<T>):this(context){
        setData(list)
    }

    constructor(context: Context,bytes:Array<T>):this(context){
        setData(bytes)
    }

    override fun getCount(): Int {
        return mData.size
    }

    override fun getItem(position: Int): T? {
        return if (checkPosition(position)) mData[position] else null
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var mConvertView = convertView
        val holder: H
        if (convertView == null) {
            mConvertView = View.inflate(mContext, getLayoutId(), null)
            holder = newViewHolder(mConvertView)
            mConvertView.tag = holder
        } else {
            holder = convertView.tag as H
        }
        val item = getItem(position)
        item?.let { convert(holder, it, position) }
        this.convertView = mConvertView
        return mConvertView
    }

    open fun setData(data: List<T>?) {
        if (data != null) {
            mData.clear()
            mData.addAll(data)
            mSelectPosition = -1
            notifyDataSetChanged()
        }
    }

    open fun setData(data: Array<T>?) {
        if (data != null && data.isNotEmpty()) {
            setData(listOf(*data))
        }
    }

    private fun checkPosition(position: Int): Boolean {
        return position >= 0 && position <= mData.size - 1
    }

    open fun removeElement(element: T) {
        if (mData.contains(element)) {
            mData.remove(element)
            notifyDataSetChanged()
        }
    }

    open fun removeElement(position: Int) {
        if (mData.size > position) {
            mData.removeAt(position)
            notifyDataSetChanged()
        }
    }

    open fun removeElements(elements: List<T>?) {
        if (elements != null && elements.isNotEmpty() && mData.size >= elements.size) {
            for (element in elements) {
                if (mData.contains(element)) {
                    mData.remove(element)
                }
            }
            notifyDataSetChanged()
        }
    }

    open fun removeElements(elements: Array<T>?) {
        if (elements != null && elements.isNotEmpty()) {
            removeElements(listOf(*elements))
        }
    }

    open fun updateElement(element: T, position: Int) {
        if (checkPosition(position)) {
            mData[position] = element
            notifyDataSetChanged()
        }
    }

    open fun addElement(element: T?) {
        if (element != null) {
            mData.add(element)
            notifyDataSetChanged()
        }
    }

    open fun clearData() {
        mData.clear()
        mSelectPosition = -1
        notifyDataSetChanged()
    }

    /**
     * 创建ViewHolder
     * @param convertView
     * @return
     */
    protected abstract fun newViewHolder(convertView: View?): H

    /**
     * 获取适配的布局ID
     * @return
     */
    protected abstract fun getLayoutId(): Int

    /**
     * 转换布局
     * @param holder
     * @param item
     * @param position
     */
    protected abstract fun convert(holder: H, item: T, position: Int)

    /**
     * @return 当前列表的选中项
     */
    open fun getSelectPosition(): Int {
        return mSelectPosition
    }

    /**
     * 设置当前列表的选中项
     *
     * @param selectPosition
     * @return
     */
    open fun setSelectPosition(selectPosition: Int) {
        mSelectPosition = selectPosition
        notifyDataSetChanged()
    }

    /**
     * 获取当前列表选中项
     *
     * @return 当前列表选中项
     */
    open fun getSelectItem(): T? {
        return getItem(mSelectPosition)
    }
}