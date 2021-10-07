package com.phz.dev.feature.practice.list.recycleview.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.phz.common.databinding.MineBindingAdapter.circleImageUrlRes
import com.phz.common.ext.logD
import com.phz.dev.R
import com.phz.dev.feature.practice.list.recycleview.model.GenShinRole

/**
 * @author phz on 2021/9/29
 * @description
 */
class GenShinRoleAdapter(private val isLikeChangeListener:(Int)->Unit) :
    ListAdapter<GenShinRole, GenShinRoleAdapter.MyViewHolder>(GenShinRoleDiffCallback()) {

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
            ivLike.isSelected=item.isLike
            ivLike.setOnClickListener {
                isLikeChangeListener(item.id)
            }
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
            diffBundle.getBoolean(GenShinRoleDiffCallback.KEY_IS_LIKE,false).let {
                holder.ivLike.isSelected=it
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
        val iv: ImageView = view.findViewById(R.id.iv_avatar)
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvDes: TextView = view.findViewById(R.id.tv_des)
        val ivLike:ImageView=view.findViewById(R.id.iv_like)
    }

}

class GenShinRoleDiffCallback : DiffUtil.ItemCallback<GenShinRole>() {
    companion object {
        const val KEY_NAME = "NAME"
        const val KEY_IMG = "IMG"
        const val KEY_DES = "DES"
        const val KEY_IS_LIKE="LIKE"
    }

    override fun areItemsTheSame(oldItem: GenShinRole, newItem: GenShinRole): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GenShinRole, newItem: GenShinRole): Boolean {
        //当areItemsTheSame返回true，代表2个对象是同一个类型Item，在此方法判断是否是一个Item，不是则跳getChangePayload方法比较细节
        return oldItem.imgResId == newItem.imgResId && oldItem.des == newItem.des && oldItem.name == newItem.name && oldItem.isLike==newItem.isLike
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
        if (oldItem.isLike!=newItem.isLike){
            diffBundle.putBoolean(KEY_IS_LIKE,newItem.isLike)
        }
        if (diffBundle.size() == 0) return null
        return diffBundle
    }
}