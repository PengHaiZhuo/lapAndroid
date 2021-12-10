package com.phz.dev.feature.login

import android.os.Bundle
import com.gyf.immersionbar.ktx.immersionBar
import com.phz.common.ext.getAppViewModel
import com.phz.common.ext.startKtxActivity
import com.phz.common.ext.view.clickNoRepeat
import com.phz.common.page.activity.BaseVmDbPureActivity
import com.phz.dev.R
import com.phz.dev.databinding.ActivityLoginBinding
import com.phz.dev.feature.main.MainActivity
import com.phz.dev.state.AppViewModel
import com.phz.dev.util.PersistenceUtil
import com.phz.dev.util.StringUtil

/**
 * @author phz on 2021/8/17
 * @description 登录页
 */
class LoginActivity : BaseVmDbPureActivity<LoginViewModel, ActivityLoginBinding>() {
    val appViewModel: AppViewModel by lazy { getAppViewModel<AppViewModel>() }

    override fun initData() {
        PersistenceUtil.getUserName().let {
            mViewDataBinding.etUsername.setText(it)
        }
        PersistenceUtil.getPWD().let {
            mViewDataBinding.etPassword.setText(it)
        }
        mViewDataBinding.btnLogin.clickNoRepeat {
            val name = mViewDataBinding.etUsername.text.toString()
            val pwd = mViewDataBinding.etPassword.text.toString()
            if (!StringUtil.isUserName(name)) {
                mViewDataBinding.etUsername.error = getString(R.string.tip_username)
                return@clickNoRepeat
            }
            if (!StringUtil.isPwd(pwd)) {
                mViewDataBinding.etPassword.error = getString(R.string.tip_pwd)
                return@clickNoRepeat
            }
            mViewModel.userName.set(name)
            mViewModel.pwd.set(pwd)
            mViewModel.login(name, pwd)
        }
        //初始化观察员
        initObserver()
    }

    /**
     * 初始化观察员
     */
    fun initObserver() {
        mViewModel.userBeanLiveData.observe(this) {
            //将用户信息缓存本地
            PersistenceUtil.setUserBean(it)
            PersistenceUtil.setUserName(mViewModel.userName.get())
            PersistenceUtil.setPWD(mViewModel.pwd.get())
            appViewModel.userBean.value = it
            startKtxActivity<MainActivity>()
            finish()
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        immersionBar {
            statusBarColor(R.color.colorTransparent)
        }
    }

    override fun layoutId(): Int = R.layout.activity_login
}