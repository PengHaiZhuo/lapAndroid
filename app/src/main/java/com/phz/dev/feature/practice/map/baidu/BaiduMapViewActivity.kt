package com.phz.dev.feature.practice.map.baidu

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import com.baidu.mapapi.map.BaiduMap
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.phz.common.ext.logE
import com.phz.common.ext.view.clickNoRepeat
import com.phz.common.page.activity.BaseVmDbActivity
import com.phz.common.state.BaseViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivityBaiduMapviewBinding

/**
 * @author phz on 2021/10/11
 * @description 百度地图
 */
class BaiduMapViewActivity : BaseVmDbActivity<BaseViewModel, ActivityBaiduMapviewBinding>() {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<RelativeLayout>
    private lateinit var baiduMap: BaiduMap
    private val onMapLoadedCallback by lazy {
        BaiduMap.OnMapLoadedCallback {
            //地图加载成功回调
        }
    }

    override fun initData() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = "地图"
        baiduMap = mViewDataBinding.mapview.map
        bottomSheetBehavior = BottomSheetBehavior.from(mViewDataBinding.bottomSheet)
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {//已折叠
                        mViewDataBinding.imageView.setImageResource(R.drawable.ic_show_out)
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {//已展开
                        mViewDataBinding.imageView.setImageResource(R.drawable.ic_show_in)
                    }
                    else -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED//手动折叠
        mViewDataBinding.imageView.post {
            val height = mViewDataBinding.imageView.drawable.bounds.height()
            bottomSheetBehavior.peekHeight = height//设置折叠时底部工作表高度
            baiduMap.setOnMapLoadedCallback(onMapLoadedCallback)//设置地图加载成功回调监听
        }
        mViewDataBinding.imageView.clickNoRepeat {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }
}