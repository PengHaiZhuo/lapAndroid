package com.phz.dev.feature.main

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.gyf.immersionbar.ktx.immersionBar
import com.phz.common.page.activity.BaseVmDbPureActivity
import com.phz.common.state.BaseViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivityMainBinding

/**
 * @author phz
 * @description
 */
class MainActivity : BaseVmDbPureActivity<BaseViewModel, ActivityMainBinding>() {

    override fun initData() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        //布局文件中使用的FragmentContainerView而不是fragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
        mViewDataBinding.bottomNav.setupWithNavController(navHostFragment.navController)
        immersionBar {
            statusBarColor(R.color.colorPrimary)
            autoDarkModeEnable(true)
        }
    }

    override fun layoutId(): Int = R.layout.activity_main

}