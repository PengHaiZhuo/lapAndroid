package com.phz.dev.feature.main.home

import android.os.Bundle
import com.phz.common.page.fragment.BaseVmDbPureFragment
import com.phz.common.state.BaseViewModel
import com.phz.dev.R
import com.phz.dev.databinding.FragmentHomeBinding

/**
 * @author phz on 2021/8/17
 * @description 首页
 */
class HomeFragment : BaseVmDbPureFragment<BaseViewModel, FragmentHomeBinding>() {
    companion object{
    }

    override fun lazyInit() {
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initData() {
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

}