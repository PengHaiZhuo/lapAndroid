package com.phz.dev.feature.main.mine

import android.os.Bundle
import com.phz.common.page.fragment.BaseVmDbPureFragment
import com.phz.common.state.BaseViewModel
import com.phz.dev.R
import com.phz.dev.databinding.FragmentMineBinding

/**
 * @author phz on 2021/8/17
 * @description 我的页面
 */
class MineFragment : BaseVmDbPureFragment<BaseViewModel, FragmentMineBinding>() {
    override fun lazyInit() {
    }

    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun initData() {
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

}