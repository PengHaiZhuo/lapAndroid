package com.phz.dev.feature.practice.list.recycleview

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.phz.common.ext.dismissLoadingExt
import com.phz.common.ext.showLoadingExt
import com.phz.common.page.activity.BaseVmDbActivity
import com.phz.dev.R
import com.phz.dev.databinding.ActivityGenshinRoleBinding
import com.phz.dev.feature.practice.list.recycleview.state.GenShinRoleListViewModel
import kotlinx.coroutines.delay

/**
 * @author phz on 2021/9/29
 * @description
 */
class GenShinRoleActivity :
    BaseVmDbActivity<GenShinRoleListViewModel, ActivityGenshinRoleBinding>() {
    override fun initData() {
        mViewDataBinding.swipeGenshinRole.apply {
            setSize(SwipeRefreshLayout.DEFAULT) //设置尺寸
            setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
            )//设置刷新时颜色变换
            setBackgroundColor(Color.WHITE)//设置背景色
            setDistanceToTriggerSync(96)//设置下拉距离
            setOnRefreshListener {
                lifecycleScope.launchWhenResumed {
                    //模拟网络加载
                    showLoadingExt()
                    delay(1000)
                    //更改列表数据

                    dismissLoadingExt()
                }
            }
        }

    }

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = "局部刷新"
    }
}