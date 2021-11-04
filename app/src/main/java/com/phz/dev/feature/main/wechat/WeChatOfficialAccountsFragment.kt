package com.phz.dev.feature.main.wechat

import android.os.Bundle
import com.phz.common.page.fragment.BaseVmDbPureFragment
import com.phz.common.state.BaseViewModel
import com.phz.dev.R
import com.phz.dev.databinding.FragmentWechatOfficialAccountBinding

/**
 * @author phz on 2021/8/17
 * @description 微信公众号页面
 */
class WeChatOfficialAccountsFragment : BaseVmDbPureFragment<BaseViewModel, FragmentWechatOfficialAccountBinding>() {
    override fun lazyInit() {
    }

    override fun getLayoutId(): Int = R.layout.fragment_wechat_official_account

    override fun initData() {
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

}