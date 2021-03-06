package com.phz.common.page.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/**
 * @author phz
 * @description:支持懒加载的FragmentLazyPagerAdapter,只有主Fragment才会调用resume方法,需要配合[com.phz.common.page.fragment.BaseVmDbFragment]
 * 这种做了懒加载处理的fragment使用
 * @notice：如果您使用ViewPager，建议您使用此适配器，如果您使用ViewPager2,那么可以不用
 */
open class FragmentLazyPagerAdapter(
    fragmentManager: FragmentManager,
    private val fragments: MutableList<Fragment>,
    private val titles: MutableList<String>
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size

    override fun getPageTitle(position: Int) = titles[position]

}