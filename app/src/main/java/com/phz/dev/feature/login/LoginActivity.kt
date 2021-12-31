package com.phz.dev.feature.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.gyf.immersionbar.ktx.immersionBar
import com.phz.common.ext.*
import com.phz.common.ext.view.clickNoRepeat
import com.phz.common.page.activity.BaseVmDbPureActivity
import com.phz.common.state.NoViewModel
import com.phz.dev.R
import com.phz.dev.api.DataState
import com.phz.dev.databinding.ActivityLoginBinding
import com.phz.dev.feature.main.MainActivity
import com.phz.dev.feature.register.RegisterActivity
import com.phz.dev.state.AppViewModel
import com.phz.dev.util.PersistenceUtil
import com.phz.dev.util.StringUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author phz on 2021/8/17
 * @description 登录页
 */
@AndroidEntryPoint
class LoginActivity : BaseVmDbPureActivity<NoViewModel, ActivityLoginBinding>() {
    val appViewModel: AppViewModel by lazy { getAppViewModel() }
    val loginViewModel:LoginViewModel  by viewModels()
    override fun initData() {
        mViewDataBinding.vm=loginViewModel
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
            loginViewModel.userName.set(name)
            loginViewModel.pwd.set(pwd)
            loginViewModel.login(name, pwd)
        }
        mViewDataBinding.register.clickNoRepeat {
            startKtxActivity<RegisterActivity>()//跳转注册页
        }
        //初始化观察员
        initObserver()
    }

    /**
     * 初始化观察员
     */
    fun initObserver() {
        lifecycleScope.launch {
            loginViewModel.userBean.collect {
                when(it){
                    is DataState.Loading ->{
                        showLoadingExt("登录中，请稍候")
                    }
                    is DataState.Success ->{
                        dismissLoadingExt()
                        //将用户信息缓存本地
                        PersistenceUtil.setUserName(loginViewModel.userName.get())
                        PersistenceUtil.setPWD(loginViewModel.pwd.get())
                        appViewModel.userBean.value = it.data
                        startKtxActivity<MainActivity>()
                        finish()
                    }
                    is DataState.Error ->{
                        dismissLoadingExt()
                        showSnackShort(it.message)
                    }
                    is DataState.Empty ->{
                        dismissLoadingExt()
                    }
                }

            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        immersionBar {
            statusBarColor(R.color.colorTransparent)
        }
    }

    override fun layoutId(): Int = R.layout.activity_login
}