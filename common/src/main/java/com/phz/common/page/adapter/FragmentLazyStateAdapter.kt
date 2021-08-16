package com.phz.common.page.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


/**
 * @author phz
 * @description 普通FragmentStateAdapter
 * @notice：如果您使用ViewPager2，建议您使用此适配器
 */
open class FragmentLazyStateAdapter(
    fragmentActivity: FragmentActivity,
    private val fragments: MutableList<Fragment>
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]

}