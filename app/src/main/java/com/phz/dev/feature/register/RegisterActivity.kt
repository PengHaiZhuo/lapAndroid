package com.phz.dev.feature.register

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.phz.common.ext.getAppViewModel
import com.phz.common.ext.logE
import com.phz.common.ext.request
import com.phz.common.ext.startKtxActivity
import com.phz.common.ext.view.clickNoRepeat
import com.phz.common.page.activity.BaseVmDbActivity
import com.phz.common.state.NoViewModel
import com.phz.common.util.ActivityManagerKtx
import com.phz.dev.R
import com.phz.dev.databinding.ActivityRegisterBinding
import com.phz.dev.feature.login.LoginActivity
import com.phz.dev.feature.main.MainActivity
import com.phz.dev.net.apiService
import com.phz.dev.state.AppViewModel
import com.phz.dev.util.PersistenceUtil
import com.phz.dev.util.StringUtil
import kotlinx.coroutines.launch

/**
 * @author phz on 2021/12/13
 * @description
 */
class RegisterActivity : BaseVmDbActivity<NoViewModel, ActivityRegisterBinding>() {
    val appViewModel: AppViewModel by lazy { getAppViewModel() }
    override fun initData() {
        mViewDataBinding.btnRegister.clickNoRepeat {
            val username = mViewDataBinding.etUsername.text.toString()
            val pwd = mViewDataBinding.etPassword.text.toString()
            val confirmPwd = mViewDataBinding.etConfirmPassword.text.toString()
            if (!StringUtil.isUserName(username)) {
                mViewDataBinding.etUsername.error = getString(R.string.tip_username)
                return@clickNoRepeat
            }
            if (!StringUtil.isPwd(pwd)) {
                mViewDataBinding.etPassword.error = getString(R.string.tip_pwd)
                return@clickNoRepeat
            }
            if (!StringUtil.isPwd(confirmPwd)) {
                mViewDataBinding.etConfirmPassword.error = getString(R.string.tip_pwd)
                return@clickNoRepeat
            }
            if (pwd != confirmPwd) {
                mViewDataBinding.etConfirmPassword.error = getString(R.string.tip_pwd_not_same)
                return@clickNoRepeat
            }
            mViewModel.viewModelScope.launch {
                mViewModel.request({ apiService.register(username, pwd, confirmPwd) }, {
                    //将用户信息缓存本地
                    PersistenceUtil.setUserName(username)
                    PersistenceUtil.setPWD(pwd)
                    appViewModel.userBean.value = it
                    startKtxActivity<MainActivity>()
                    ActivityManagerKtx.finishActivity(LoginActivity::class.java)
                    finish()
                }, {
                    ToastUtils.showShort("注册失败，错误码${it.errorCode}")
                    "message:${it.errorMsg}    code:${it.errorCode}".logE()
                })
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        centerTextView.text = getString(R.string.register)
    }
}