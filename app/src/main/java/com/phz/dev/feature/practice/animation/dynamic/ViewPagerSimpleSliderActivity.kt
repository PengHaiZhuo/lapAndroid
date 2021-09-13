package com.phz.dev.feature.practice.animation.dynamic

import android.os.Bundle
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.viewpager2.widget.ViewPager2
import com.phz.common.ext.toPx
import com.phz.common.ext.view.spring
import com.phz.common.page.activity.BaseVmDbActivity
import com.phz.common.state.BaseViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivityViewpager2SimpleSliderBinding
import com.phz.dev.feature.practice.animation.dynamic.adapter.AvatarAdapter
import com.phz.dev.feature.practice.animation.dynamic.data.model.GenShinRole

class ViewPagerSimpleSliderActivity :
    BaseVmDbActivity<BaseViewModel, ActivityViewpager2SimpleSliderBinding>() {
    private val recyclePagerAdapter: AvatarAdapter by lazy { AvatarAdapter() }
    private var animationStartNeeded = true
    private var targetPosition = 0f
    private var targetAlpha = 1f

    companion object {
        val roleList = mutableListOf<GenShinRole>(
            GenShinRole("空", R.drawable.ic_genshin_k),
            GenShinRole("刻晴", R.drawable.ic_genshin_kq),
            GenShinRole("可莉", R.drawable.ic_genshin_kl),
            GenShinRole("莫纳", R.drawable.ic_genshin_mn),
            GenShinRole("温蒂", R.drawable.ic_genshin_wd),
            GenShinRole("优菈", R.drawable.ic_genshin_yl),
            GenShinRole("钟离", R.drawable.ic_genshin_zl)
        )
        private val singlePageSize = roleList.size
        var nextLoadMore = singlePageSize - 3//避免滑到底才进行加载，预先就加载更多，比较平滑
    }

    override fun initData() {
        mViewDataBinding.vp2SimpleSlider.apply {
            offscreenPageLimit = 3
            adapter = recyclePagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    when (state) {
                        //空闲稳定状态，可以开始动画
                        ViewPager2.SCROLL_STATE_IDLE -> {
                            animationStartNeeded = true
                            targetPosition = 0f
                            targetAlpha = 1f
                        }
                        else -> {
                            if (animationStartNeeded) {
                                animationStartNeeded = false
                                targetPosition = 20.toPx().toFloat()
                                targetAlpha = 0f
                            }
                        }
                    }
                    mViewDataBinding.tvIndex.spring(DynamicAnimation.TRANSLATION_Y, targetPosition)
                    mViewDataBinding.tvIndex.spring(DynamicAnimation.ALPHA, targetAlpha)
                    super.onPageScrollStateChanged(state)
                }

                override fun onPageSelected(position: Int) {
                    mViewDataBinding.tvIndex.text = "Role ${position + 1}"
                    if (nextLoadMore <= position + 1) {
                        nextLoadMore += com.phz.dev.feature.practice.animation.dynamic.ViewPagerSimpleSliderActivity.Companion.singlePageSize
                        recyclePagerAdapter.addItems(roleList)
                    }
                    super.onPageSelected(position)
                }
            })
        }
        mViewDataBinding.vpc.setSimpleSlider(0f, 0.2f, 0.5f)
        recyclePagerAdapter.addAll(roleList)
    }

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = "ViewPager2 Container"
    }
}