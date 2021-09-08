package com.phz.dev.feature.main.home

import android.os.Bundle
import com.gyf.immersionbar.ktx.immersionBar
import com.phz.common.page.fragment.BaseVmDbPureFragment
import com.phz.common.state.BaseViewModel
import com.phz.dev.R
import com.phz.dev.databinding.FragmentHomeBinding

/**
 * @author phz on 2021/8/17
 * @description 首页
 */
class HomeFragment : BaseVmDbPureFragment<BaseViewModel, FragmentHomeBinding>() {
    override fun lazyInit() {
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initData() {
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun onResume() {
        super.onResume()
        immersionBar {
            statusBarColor(R.color.white)
            autoDarkModeEnable(true)
        }

    }
}