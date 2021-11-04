package com.phz.dev.feature.practice.list.listview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.checkbox.MaterialCheckBox
import com.phz.dev.R
import com.phz.dev.feature.practice.list.listview.model.ClassRoomBean


/**
 * @author phz on 2021/9/26
 * @description
 */
class ClassRoomAdapter(
    private val mContext: Context,
    private val classRoomList: List<ClassRoomBean>,
    private val onChildItemClickListener: OnItemClickListener
) :
    BaseExpandableListAdapter() {

    override fun getGroupCount(): Int = classRoomList.size

    override fun getChildrenCount(groupPosition: Int): Int =
        classRoomList[groupPosition].student.size

    override fun getGroup(groupPosition: Int): Any = classRoomList[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int): Any =
        classRoomList[groupPosition].student[childPosition]

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = childPosition.toLong()

    override fun hasStableIds(): Boolean = true

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        lateinit var rootView: View
        var viewHolder = GroupViewHolder()
        if (convertView == null) {
            rootView =
                LayoutInflater.from(mContext).inflate(R.layout.item_student_class, parent, false)
            viewHolder.tvGroup = rootView.findViewById(R.id.tv_group_class)
            viewHolder.ivGroup = rootView.findViewById(R.id.iv_group_class)
            rootView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as GroupViewHolder
            rootView = convertView
        }

        val groupName = classRoomList[groupPosition].name
        viewHolder.tvGroup!!.text = groupName
        if (isExpanded) {
            viewHolder.ivGroup!!.setImageResource(R.drawable.arrow_up)
        } else {
            viewHolder.ivGroup!!.setImageResource(R.drawable.arrow_down)
        }
        return rootView
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        lateinit var rootView: View
        var viewHolder = ChildViewHolder()
        if (convertView == null) {
            rootView =
                LayoutInflater.from(mContext).inflate(R.layout.item_student, parent, false)
            viewHolder.cbChild = rootView.findViewById(R.id.cb_student)
            viewHolder.tvChild = rootView.findViewById(R.id.tv_student)
            rootView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ChildViewHolder
            rootView = convertView
        }
        val student = classRoomList[groupPosition].student[childPosition]
        viewHolder.tvChild!!.text = student.name
        viewHolder.cbChild!!.setOnCheckedChangeListener { _, isChecked ->
            //选中状态改变
            onChildItemClickListener.checkedChange(groupPosition, childPosition, isChecked)
        }
        return rootView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        //默认返回false,改成true表示组中的子条目可以被点击选中
        return true
    }

    internal class GroupViewHolder {
        var tvGroup: TextView? = null
        var ivGroup: ImageView? = null
    }

    internal class ChildViewHolder {
        var tvChild: TextView? = null
        var cbChild: MaterialCheckBox? = null
    }
}

interface OnItemClickListener {
    fun checkedChange(groupPosition: Int, childPosition: Int, isChecked: Boolean)
}