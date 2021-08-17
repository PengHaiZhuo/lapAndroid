package com.phz.dev.feature.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.gyf.immersionbar.ktx.immersionBar
import com.phz.common.appContext
import com.phz.common.ext.getVersionName
import com.phz.common.ext.startKtxActivity
import com.phz.common.page.activity.BaseVmDbPureActivity
import com.phz.common.state.BaseViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivitySplashBinding
import com.phz.dev.feature.main.MainActivity

class SplashActivity : BaseVmDbPureActivity<BaseViewModel, ActivitySplashBinding>() {
    val mHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun layoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initView(savedInstanceState: Bundle?) {
        immersionBar {
            statusBarColor(R.color.colorSecondaryVariant)
        }
    }

    override fun initData() {
        mViewDataBinding.tvVersion.text =
            String.format(appContext.getString(R.string.v_version_name), getVersionName(appContext))
        mViewDataBinding.animLog.apply {
            addOffsetAnimListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)

                    mHandler.postDelayed({
                        startKtxActivity<MainActivity>()
                        this@SplashActivity.finish()
                    }, 2000L)
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)
    }
}