package com.phz.dev.feature.practice.animation

import android.os.Bundle
import com.phz.common.page.activity.BaseVmDbActivity
import com.phz.common.state.BaseViewModel
import com.phz.dev.databinding.ActivityViewpager2SimpleSliderBinding

class ViewPagerSimpleSliderActivity :
    BaseVmDbActivity<BaseViewModel, ActivityViewpager2SimpleSliderBinding>() {
    override fun initData() {
        //todo https://androidrepo.com/repo/lgvalle-Material-Animations-android-animations
        //https://github.com/lgvalle/Material-Animations/
        //这篇文章是讲安卓transition的，主要是动画，包括window和view各种情况
    }

    override fun initView(savedInstanceState: Bundle?) {
    }
}