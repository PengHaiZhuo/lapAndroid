package com.phz.dev.feature.main.ground

import android.os.Bundle
import com.phz.common.page.fragment.BaseVmDbPureFragment
import com.phz.common.state.NoViewModel
import com.phz.dev.R
import com.phz.dev.databinding.FragmentGroundBinding

/**
 * @author phz on 2021/8/17
 * @description 广场页
 */
class GroundFragment : BaseVmDbPureFragment<NoViewModel, FragmentGroundBinding>() {
    override fun lazyInit() {
    }

    override fun getLayoutId(): Int = R.layout.fragment_ground

    override fun initData() {
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

}