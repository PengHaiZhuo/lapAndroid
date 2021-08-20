package com.phz.dev.feature.main.mine.integral

import android.os.Bundle
import com.phz.common.page.fragment.BaseVmDbPureFragment
import com.phz.common.state.BaseViewModel
import com.phz.dev.R
import com.phz.dev.databinding.FragmentHomeBinding

/**
 * @author phz on 2021/8/19
 * @description 我的积分
 */
class IntegralFragment:BaseVmDbPureFragment<BaseViewModel,FragmentHomeBinding> (){
    override fun lazyInit() {
    }

    override fun getLayoutId(): Int= R.layout.fragment_mine_integral

    override fun initData() {
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

}