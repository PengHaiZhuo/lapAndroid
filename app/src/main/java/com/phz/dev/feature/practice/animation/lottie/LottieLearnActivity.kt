package com.phz.dev.feature.practice.animation.lottie

import android.animation.Animator
import android.os.Bundle
import com.phz.common.page.activity.BaseToolbarActivity
import com.phz.common.state.NoViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivityLottieLearnBinding
import com.phz.dev.feature.practice.animation.lottie.dialog.DionaDialog

/**
 * @author phz
 * @description  使用请参考【http://airbnb.io/lottie/#/android】
 */
class LottieLearnActivity : BaseToolbarActivity<NoViewModel, ActivityLottieLearnBinding>() {
    private lateinit var dionaDialog: DionaDialog

    override fun initData() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = "Lottie Android"
        mViewDataBinding.click = ClickProxy()
        dionaDialog = DionaDialog(this)
        initLottieAniView()
    }

    inner class ClickProxy {
        fun clickDiona() {
            dionaDialog.show()
        }

        fun play() {
            mViewDataBinding.lottieAniView.playAnimation()
            mViewDataBinding.btPause.isEnabled = true
        }

        fun pause() {
            if (mViewDataBinding.lottieAniView.isAnimating) {
                mViewDataBinding.lottieAniView.pauseAnimation()
                mViewDataBinding.btPause.text = getString(R.string.resume)
            } else {
                mViewDataBinding.lottieAniView.resumeAnimation()
                mViewDataBinding.btPause.text = getString(R.string.pause)
            }
        }
    }

    /**
     * 初始化LottieAnimationView属性设置
     */
    fun initLottieAniView() {

        mViewDataBinding.lottieAniView.apply {
            addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    mViewDataBinding.btPause.text = getString(R.string.pause)
                    mViewDataBinding.btPause.isEnabled = false

                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

            })

            addAnimatorUpdateListener {

            }
        }
    }

}