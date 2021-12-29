package com.phz.dev.feature.main.project

import android.os.Bundle
import com.phz.common.page.fragment.BaseVmDbPureFragment
import com.phz.common.state.NoViewModel
import com.phz.dev.R
import com.phz.dev.databinding.FragmentProjectBinding

/**
 * @author phz on 2021/8/17
 * @description 项目页面
 */
class ProjectFragment : BaseVmDbPureFragment<NoViewModel, FragmentProjectBinding>() {
    override fun lazyInit() {
    }

    override fun getLayoutId(): Int = R.layout.fragment_project

    override fun initData() {
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

}