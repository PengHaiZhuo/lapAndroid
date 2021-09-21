package com.phz.dev.feature.practice.screenrecord

import android.os.Bundle
import com.phz.common.page.activity.BaseVmDbActivity
import com.phz.common.state.BaseViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivityScreenRecordBinding

/**
 * @author phz on 2021/9/17
 * @description 开发录屏列表
 */
class ScreenRecordActivity : BaseVmDbActivity<BaseViewModel, ActivityScreenRecordBinding>() {


    override fun initData() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        mViewDataBinding.click = ClickProxy()
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = "开发录屏"
    }

    inner class ClickProxy {

        fun clickMpAndroidChart() {
            
        }
    }

}