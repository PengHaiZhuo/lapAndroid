package com.phz.dev.feature.practice.map.baidu

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import com.baidu.mapapi.map.BaiduMap
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.phz.common.ext.view.clickNoRepeat
import com.phz.common.page.activity.BaseToolbarActivity
import com.phz.common.state.NoViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivityBaiduMapviewBinding

/**
 * @author phz on 2021/10/11
 * @description 百度地图
 */
class BaiduMapViewActivity : BaseToolbarActivity<NoViewModel, ActivityBaiduMapviewBinding>() {
    companion object {
        //确切定位权限
        const val fineLocation=Manifest.permission.ACCESS_FINE_LOCATION
        //大致定位权限
        const val crossLocation=Manifest.permission.ACCESS_COARSE_LOCATION
        //相机 实景导航要用
        const val camera=Manifest.permission.CAMERA
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<RelativeLayout>
    private lateinit var baiduMap: BaiduMap
    private val onMapLoadedCallback by lazy {
        BaiduMap.OnMapLoadedCallback {
            //地图加载成功回调
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        XXPermissions.with(this).permission(fineLocation,crossLocation, camera).request(object :
            OnPermissionCallback{
            override fun onGranted(permissions: MutableList<String>?, all: Boolean) {

            }

            override fun onDenied(permissions: MutableList<String>?, never: Boolean) {
                super.onDenied(permissions, never)
            }
        })
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

    override fun onResume() {
        super.onResume()
        mViewDataBinding.mapview.onResume()
    }

    override fun onPause() {
        super.onPause()
        mViewDataBinding.mapview.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewDataBinding.mapview.onDestroy()
    }
}