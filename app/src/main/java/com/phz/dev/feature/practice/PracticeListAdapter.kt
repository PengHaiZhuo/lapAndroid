package com.phz.dev.feature.practice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.phz.dev.R
import com.phz.dev.databinding.ItemSimpleTextBinding

/**
 * @author phz on 2021/8/25
 * @description
 */
class PracticeListAdapter(private val click: MyClick) :
    RecyclerView.Adapter<PracticeListAdapter.MyViewHolder>() {

    private var list: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemSimpleTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.setText(item)
    }

    override fun getItemViewType(position: Int): Int = R.layout.item_simple_text

    override fun getItemCount(): Int = list.size

    fun addAll(list: MutableList<String>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun addItems(list: MutableList<String>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(private val mViewDataBinding: ItemSimpleTextBinding) :
        RecyclerView.ViewHolder(mViewDataBinding.root) {

        fun setText(string: String) {
            mViewDataBinding.apply {
                mViewDataBinding.tvSimple.text = string
                mViewDataBinding.tvSimple.setOnClickListener {
                    click.onClick(string)
                }
            }
        }
    }
}