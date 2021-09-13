package com.phz.dev.feature.practice

import android.os.Bundle
import com.phz.common.ext.startKtxActivity
import com.phz.common.ext.view.vertical
import com.phz.common.page.activity.BaseVmDbActivity
import com.phz.common.page.adapter.OnItemClickListener
import com.phz.dev.R
import com.phz.dev.databinding.ActivityPracticeMainBinding
import com.phz.dev.feature.practice.animation.dynamic.ViewPagerSimpleSliderActivity
import com.phz.dev.feature.practice.animation.lottie.LottieLearnActivity
import com.phz.dev.feature.practice.mlkit.scan.MlKitScanMenuActivity
import com.phz.dev.feature.practice.popupwindow.dropdownmenu.ui.activity.DropDownMenuActivity
import com.phz.dev.feature.practice.toolbar.DrawerLayoutLearnActivity
import com.phz.dev.feature.practice.viewstub.ViewStubLearnActivity

/**
 * @author phz on 2021/8/23
 * @description
 */
class PracticeMainActivity :
    BaseVmDbActivity<PracticeMainViewModel, ActivityPracticeMainBinding>() {
    private var mAdapter = PracticeListAdapter()

    companion object {
        val practiceNames = arrayListOf(
            "ViewPager Transformer",
            "Lottie",
            "Scan",
            "DrawerLayout",
            "PopupWindow",
//            "CollapsingToolbarLayout",
            "ViewStub"
//        ,"CountDownLatch"
        )
    }

    override fun initData() {
        mAdapter.setOnItemClick(object : OnItemClickListener<String> {
            override fun onClick(bean: String, position: Int) {
                when (bean) {
                    "ViewPager Transformer" -> {
                        startKtxActivity<ViewPagerSimpleSliderActivity>()
                    }
                    "Lottie" -> {
                        startKtxActivity<LottieLearnActivity>()
                    }
                    "Scan" -> {
                        startKtxActivity<MlKitScanMenuActivity>()
                    }
                    "DrawerLayout" -> {
                        startKtxActivity<DrawerLayoutLearnActivity>()
                    }
                    "PopupWindow" -> {
                        startKtxActivity<DropDownMenuActivity>()
                    }
                    "ViewStub" -> {
                        startKtxActivity<ViewStubLearnActivity>()
                    }
                }
            }
        })
        mViewDataBinding.rvPractice.apply {
            vertical()
            adapter = mAdapter
            mAdapter.addAll(practiceNames)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = "实践与练习"
    }
}


