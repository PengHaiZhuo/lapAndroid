package com.phz.dev.feature.practice.screenrecord

import android.os.Bundle
import com.phz.common.page.activity.BaseToolbarActivity
import com.phz.common.state.NoViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivityScreenRecordBinding

/**
 * @author phz on 2021/9/17
 * @description 开发录屏列表
 */
class ScreenRecordActivity : BaseToolbarActivity<NoViewModel, ActivityScreenRecordBinding>() {


    override fun initData() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        mViewDataBinding.click = ClickProxy()
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = "开发录屏"
    }

    inner class ClickProxy {

        fun clickCarStatistics() {
            ExoPlayerActivity.start(
                this@ScreenRecordActivity,
                ExoPlayerActivity.Companion.TYPE.CAR_STATISTICS
            )
        }

        fun clickTurboStatistics() {
            ExoPlayerActivity.start(
                this@ScreenRecordActivity,
                ExoPlayerActivity.Companion.TYPE.TURBO_STATISTICS
            )
        }

        fun clickWindStatistics() {
            ExoPlayerActivity.start(
                this@ScreenRecordActivity,
                ExoPlayerActivity.Companion.TYPE.WIND_STATISTICS
            )
        }

        fun clickBluetoothHp() {
            ExoPlayerActivity.start(
                this@ScreenRecordActivity,
                ExoPlayerActivity.Companion.TYPE.BLUETOOTH_HELPER
            )
        }

        fun clickXiaoPu() {
            ExoPlayerActivity.start(
                this@ScreenRecordActivity,
                ExoPlayerActivity.Companion.TYPE.XIAO_PU
            )
        }

        fun clickNeighborVoice() {
            ExoPlayerActivity.start(
                this@ScreenRecordActivity,
                ExoPlayerActivity.Companion.TYPE.NEIGHBOR_VOICE
            )
        }

        fun clickEngineeringPulse() {
            ExoPlayerActivity.start(
                this@ScreenRecordActivity,
                ExoPlayerActivity.Companion.TYPE.ENGINEERING_PLUS
            )
        }

    }

}