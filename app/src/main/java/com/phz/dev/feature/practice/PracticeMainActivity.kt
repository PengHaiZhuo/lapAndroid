package com.phz.dev.feature.practice

import android.os.Bundle
import com.blankj.utilcode.util.ColorUtils
import com.phz.common.ext.startKtxActivity
import com.phz.common.ext.view.divider
import com.phz.common.ext.view.vertical
import com.phz.common.page.activity.BaseVmDbActivity
import com.phz.dev.R
import com.phz.dev.databinding.ActivityPracticeMainBinding
import com.phz.dev.feature.practice.animation.ViewPagerSimpleSliderActivity
import com.phz.dev.feature.practice.mlkit.scan.MlKitScanMenuActivity

/**
 * @author phz on 2021/8/23
 * @description
 */
class PracticeMainActivity :
    BaseVmDbActivity<PracticeMainViewModel, ActivityPracticeMainBinding>() {
    private var mAdapter = PracticeListAdapter(object : MyClick {
        override fun onClick(name: String) {
            when (name) {
                practiceNames[0] -> {
                    startKtxActivity<ViewPagerSimpleSliderActivity>()
                }
                practiceNames[1] -> {
                    startKtxActivity<MlKitScanMenuActivity>()
                }
            }
        }

    })

    companion object {
        val practiceNames = arrayListOf("viewpager2", "mlkit扫码")
    }

    override fun initData() {
        mViewDataBinding.rvPractice.apply {
            vertical()
            divider {
                setColor(ColorUtils.getColor(R.color.colorDivider))
                setDivider(2)
            }
            mAdapter.addAll(practiceNames)
            adapter = mAdapter
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = "实践与练习"
    }
}

interface MyClick {
    fun onClick(name: String)
}


