package com.phz.dev.feature.practice.list.listview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.phz.dev.feature.practice.list.listview.model.ProjectBean

/**
 * @author phz on 2021/9/28
 * @description
 */
class ProjectAdapter(
    private var mContext: Context, private var lists: List<ProjectBean>,
    private val itemRes: Int = android.R.layout.simple_dropdown_item_1line
) :
    ArrayAdapter<ProjectBean>(mContext, itemRes) {

    override fun getItem(position: Int): ProjectBean? = lists[position]
    override fun getCount(): Int = lists.size
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        lateinit var rootView: View
        var viewHolder = MyViewHolder()
        if (convertView == null) {
            rootView =
                LayoutInflater.from(mContext).inflate(itemRes, parent, false)
            viewHolder.tv = rootView.findViewById(android.R.id.text1)
            rootView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as MyViewHolder
            rootView = convertView
        }
        viewHolder.tv!!.text = lists[position].name
        return rootView
    }


    inner class MyViewHolder {
        var tv: TextView? = null
    }
}