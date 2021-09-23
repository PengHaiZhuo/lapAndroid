package com.phz.dev.feature.practice.screenrecord

import android.os.Bundle
import com.phz.common.page.activity.BaseVmDbActivity
import com.phz.common.state.BaseViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivityExoPlayerBinding

/**
 * @author phz on 2021/9/22
 * @description
 */
class ExoPlayerActivity : BaseVmDbActivity<BaseViewModel, ActivityExoPlayerBinding>() {
    private var type:TYPE=TYPE.CAR_STATISTICS
    override fun initData() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = "开发录屏"
    }

    companion object {
        enum class TYPE {
            CAR_STATISTICS, WIND_STATISTICS, TURBO_STATISTICS, BLUETOOTH_HELPER
        }
    }
}