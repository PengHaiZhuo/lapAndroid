package com.phz.dev.feature.practice

import android.os.Bundle
import com.phz.common.ext.startKtxActivity
import com.phz.common.page.activity.BaseVmDbActivity
import com.phz.dev.R
import com.phz.dev.databinding.ActivityPracticeMainBinding
import com.phz.dev.feature.practice.animation.ViewPagerSimpleSliderActivity

/**
 * @author phz on 2021/8/23
 * @description
 */
class PracticeMainActivity :
    BaseVmDbActivity<PracticeMainViewModel, ActivityPracticeMainBinding>() {
    override fun initData() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text="实践与练习"
        mViewDataBinding.vm = mViewModel
        mViewDataBinding.clickProxy = ProxyClick()
    }

    inner class ProxyClick {
        fun vp2() {
            startKtxActivity<ViewPagerSimpleSliderActivity>()
        }
    }

}