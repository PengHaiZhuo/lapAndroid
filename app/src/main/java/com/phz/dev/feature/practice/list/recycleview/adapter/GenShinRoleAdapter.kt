package com.phz.dev.feature.practice.list.recycleview.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.phz.common.databinding.MineBindingAdapter.circleImageUrlRes
import com.phz.common.ext.logD
import com.phz.dev.R
import com.phz.dev.feature.practice.list.recycleview.adapter.diff.GenShinRoleDiffCallback
import com.phz.dev.feature.practice.list.recycleview.model.GenShinRole

/**
 * @author phz on 2021/9/29
 * @description
 */
class GenShinRoleAdapter(private val callBack: GenShinRoleDiffCallback) :
    ListAdapter<GenShinRole, GenShinRoleAdapter.MyViewHolder>(callBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_genshin_role, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        "onBindViewHolder".logD("GenShin")
        holder.apply {
            circleImageUrlRes(iv, item.imgResId)
            tvName.text = item.name
            tvDes.text = item.des
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, payloads: MutableList<Any>) {
        //item的局部刷新在这里处理
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val diffBundle: Bundle = payloads[0] as Bundle
            diffBundle.getString(GenShinRoleDiffCallback.KEY_NAME)?.let {
                holder.tvName.text = it
            }
            diffBundle.getString(GenShinRoleDiffCallback.KEY_DES)?.let {
                holder.tvDes.text = it
            }
            diffBundle.getInt(GenShinRoleDiffCallback.KEY_IMG, -1).let {
                if (it != -1) {
                    circleImageUrlRes(holder.iv, it)
                }
            }
        }
    }

    override fun getItem(position: Int): GenShinRole {
        return super.getItem(position)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    inner class MyViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        var iv: ImageView = view.findViewById(R.id.iv_avatar)
        var tvName: TextView = view.findViewById(R.id.tv_name)
        var tvDes: TextView = view.findViewById(R.id.tv_des)
    }

}