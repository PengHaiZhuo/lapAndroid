package com.phz.dev.feature.practice

import android.view.ViewGroup
import com.phz.common.page.adapter.BaseRecycleViewAdapter
import com.phz.common.page.adapter.BaseRecycleViewHolder
import com.phz.dev.R
import com.phz.dev.databinding.ItemSimpleTextBinding

/**
 * @author phz on 2021/8/25
 * @description
 */
class PracticeListAdapter : BaseRecycleViewAdapter<String, ItemSimpleTextBinding>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int = R.layout.item_simple_text

    inner class MyViewHolder(parent: ViewGroup, layoutId: Int) :
        BaseRecycleViewHolder<String, ItemSimpleTextBinding>(parent, layoutId) {
        override fun onBind(bean: String, position: Int) {
            val mBinding = mViewBinding as ItemSimpleTextBinding
            mBinding.btPractice.text = bean
        }
    }
}