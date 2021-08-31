package com.phz.dev.feature.practice

import android.os.Bundle
import com.phz.common.ext.startKtxActivity
import com.phz.common.ext.view.vertical
import com.phz.common.page.activity.BaseVmDbActivity
import com.phz.common.page.adapter.OnItemClickListener
import com.phz.dev.R
import com.phz.dev.databinding.ActivityPracticeMainBinding
import com.phz.dev.feature.practice.animation.ViewPagerSimpleSliderActivity
import com.phz.dev.feature.practice.mlkit.scan.MlKitScanMenuActivity
import com.phz.dev.feature.practice.popupwindow.dropdownmenu.ui.activity.DropDownMenuActivity
import com.phz.dev.feature.practice.toolbar.ToolbarLearnActivity

/**
 * @author phz on 2021/8/23
 * @description
 */
class PracticeMainActivity :
    BaseVmDbActivity<PracticeMainViewModel, ActivityPracticeMainBinding>() {
    private var mAdapter = PracticeListAdapter()

    companion object {
        val practiceNames = arrayListOf("ViewPager Transformer", "Scan","ToolBar","DropDownMenu")
    }

    override fun initData() {
        mAdapter.setOnItemClick(object : OnItemClickListener<String> {
            override fun onClick(bean: String, position: Int) {
                when (position) {
                    0 -> {
                        startKtxActivity<ViewPagerSimpleSliderActivity>()
                    }
                    1 -> {
                        startKtxActivity<MlKitScanMenuActivity>()
                    }
                    2->{
                        startKtxActivity<ToolbarLearnActivity>()
                    }
                    3->{
                        startKtxActivity<DropDownMenuActivity>()
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


