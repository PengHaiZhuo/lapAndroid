package com.phz.dev.feature.login

import android.os.Bundle
import com.gyf.immersionbar.ktx.immersionBar
import com.phz.common.ext.startKtxActivity
import com.phz.common.ext.view.clickNoRepeat
import com.phz.common.page.activity.BaseVmDbPureActivity
import com.phz.dev.R
import com.phz.dev.databinding.ActivityLoginBinding
import com.phz.dev.feature.main.MainActivity

/**
 * @author phz on 2021/8/17
 * @description 登录页
 */
class LoginActivity : BaseVmDbPureActivity<LoginViewModel, ActivityLoginBinding>() {

    override fun initData() {
        mViewDataBinding.btnLogin.clickNoRepeat {
            //todo 登陆
            startKtxActivity<MainActivity>()
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        immersionBar {
            statusBarColor(R.color.colorTransparent)
        }
    }

    override fun layoutId(): Int = R.layout.activity_login
}