package com.phz.dev.feature.practice.animation.transition

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.phz.common.ext.view.toggle
import com.phz.common.page.activity.BaseToolbarActivity
import com.phz.common.state.NoViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivityTransitionBinding


/**
 * @author phz
 * @description transition库使用
 */
class TransitionActivity : BaseToolbarActivity<NoViewModel, ActivityTransitionBinding>() {

    override fun initData() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = "Transition"
        mViewDataBinding.click = ClickProxy()
    }

    inner class ClickProxy {
        fun startLL1Animation(){
            val transitionLL1: Transition = Slide(Gravity.START)
            transitionLL1.apply {
                duration=2000L
                addTarget(mViewDataBinding.ll1)
                TransitionManager.beginDelayedTransition(mViewDataBinding.ll1.parent as ViewGroup,transitionLL1)
            }
            mViewDataBinding.ll1.toggle()
        }

        fun startLL2Animation(){
            val transitionLL2: Transition = Slide(Gravity.END)
            transitionLL2.apply {
                duration=2000L
                addTarget(mViewDataBinding.ll2)
                TransitionManager.beginDelayedTransition(mViewDataBinding.ll2.parent as ViewGroup,transitionLL2)
            }
            mViewDataBinding.ll2.toggle()
        }

        fun startIvAnimation(){
            val transitionIv: Transition = Fade()
            transitionIv.apply {
                duration=2000L
                addTarget(mViewDataBinding.imageView)
                TransitionManager.beginDelayedTransition(mViewDataBinding.imageView.parent as ViewGroup,transitionIv)
            }
            mViewDataBinding.imageView.toggle()
        }

    }

}