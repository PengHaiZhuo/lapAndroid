package com.phz.dev.feature.practice.animation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.phz.common.databinding.MineBindingAdapter.circleImageUrlRes
import com.phz.common.util.ActivityManagerKtx
import com.phz.dev.R
import com.phz.dev.databinding.ItemAvatarBinding
import com.phz.dev.feature.practice.animation.data.model.GenShinRole
import org.jetbrains.annotations.NotNull

/**
 * @author phz on 2021/8/23
 * @description
 */
class AvatarAdapter : RecyclerView.Adapter<AvatarAdapter.MyViewHolder>() {

    private var list: MutableList<GenShinRole> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(ActivityManagerKtx.currentActivity!!.baseContext)
            .inflate(viewType, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int = R.layout.item_avatar

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        circleImageUrlRes(holder.mViewDataBinding.ivAvatar, item.src)
        holder.mViewDataBinding.tvName.text = item.name
    }

    fun addAll(list: MutableList<GenShinRole>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun addItems(list: MutableList<GenShinRole>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(@NotNull view: View) : RecyclerView.ViewHolder(view) {
        var mViewDataBinding: ItemAvatarBinding = DataBindingUtil.bind(view)!!
    }

    override fun getItemCount(): Int = list.size

}