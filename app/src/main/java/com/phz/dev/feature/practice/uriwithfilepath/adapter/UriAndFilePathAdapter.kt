package com.phz.dev.feature.practice.uriwithfilepath.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.phz.dev.R
import com.phz.dev.feature.practice.uriwithfilepath.bean.PathBean

/**
 * @author phz on 2021/10/15
 * @description 打印路径列表适配器
 */
class UriAndFilePathAdapter : ListAdapter<PathBean, UriAndFilePathAdapter.MyViewHolder>(PathBeanDiffUtil()) {


    inner class MyViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvPath: TextView = view.findViewById(R.id.tv_path)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_url_with_filepath, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.tvName.text = item.apiName
        holder.tvPath.text = item.pathOrUri
    }

}

class PathBeanDiffUtil : DiffUtil.ItemCallback<PathBean>() {
    override fun areItemsTheSame(oldItem: PathBean, newItem: PathBean): Boolean {
        return oldItem.apiName == newItem.apiName
    }

    override fun areContentsTheSame(oldItem: PathBean, newItem: PathBean): Boolean {
        return oldItem == newItem
    }

}