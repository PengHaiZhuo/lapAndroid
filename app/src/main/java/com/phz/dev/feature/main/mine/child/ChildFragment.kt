package com.phz.dev.feature.main.mine.child

import android.os.Bundle
import com.phz.common.ext.logE
import com.phz.common.page.fragment.BaseVmDbPureFragment
import com.phz.common.state.NoViewModel
import com.phz.dev.R
import com.phz.dev.databinding.FragmentHomeBinding

/**
 * @author phz on 2021/8/19
 * @description
 */
class ChildFragment:BaseVmDbPureFragment<NoViewModel,FragmentHomeBinding> (){
    override fun lazyInit() {
    }

    override fun getLayoutId(): Int= R.layout.fragment_home

    override fun initData() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        "onViewCreated ChildFragment".logE()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        "onDestroyView ChildFragment".logE()
    }
}