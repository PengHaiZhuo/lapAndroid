package com.phz.dev.feature.practice.mlkit.scan

import android.os.Bundle
import com.phz.common.ext.startKtxActivity
import com.phz.common.page.activity.BaseVmDbActivity
import com.phz.common.state.BaseViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivityMlkitScanMenuBinding
import com.phz.dev.feature.practice.mlkit.scan.ct.MlKitScanWithConsoleActivity

/**
 * @author phz on 2021/8/24
 * @description
 */
class MlKitScanMenuActivity : BaseVmDbActivity<BaseViewModel, ActivityMlkitScanMenuBinding>() {
    override fun initData() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        mViewDataBinding.clickProxy=ClickProxy()
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = "MlKitScanMenu"
    }

    inner class ClickProxy {
        fun toContinueScan() {
            startKtxActivity<MlKitScanWithConsoleActivity>()
        }

        fun toNormalScan() {

        }
    }
}