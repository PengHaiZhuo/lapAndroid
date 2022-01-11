package com.phz.dev.feature.practice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.phz.common.ext.view.clickNoRepeat
import com.phz.dev.R

/**
 * @author phz on 2021/8/25
 * @description
 */
class PracticeListAdapter(private val onItemClickListener: (String) -> Unit) :
    ListAdapter<String, PracticeListAdapter.MyViewHolder>(PracticeDiffUtil()) {

    inner class MyViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {
        val tv: TextView = view.findViewById(R.id.tv_list_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_simple_text_water, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.tv.text = item
        holder.tv.clickNoRepeat {
            onItemClickListener(item)
        }
    }
}

class PracticeDiffUtil : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}