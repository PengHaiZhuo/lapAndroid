package com.phz.dev.feature.main.mine

import android.os.Bundle
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.setFitsSystemWindows
import com.phz.common.ext.startKtxActivity
import com.phz.common.page.fragment.BaseVmDbPureFragment
import com.phz.dev.R
import com.phz.dev.databinding.FragmentMineBinding
import com.phz.dev.feature.practice.PracticeMainActivity

/**
 * @author phz on 2021/8/17
 * @description 我的页面
 */
class MineFragment : BaseVmDbPureFragment<MineViewModel, FragmentMineBinding>() {
    override fun lazyInit() {
    }

    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun initData() {
//        mViewDataBinding.btnToChild.clickNoRepeat {
//            nav().navigate(R.id.action_mine_to_child)
//        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mViewDataBinding.vm = mViewModel
        mViewDataBinding.clickProxy = ProxyClick()
    }


    inner class ProxyClick {
        //积分
        fun integral() {}

        //文章
        fun article() {}

        //收藏
        fun star() {}

        //待办
        fun todo() {}

        //我的实践
        fun practice() {
            context!!.startKtxActivity<PracticeMainActivity>()
        }

        //清理缓存
        fun clear() {}

        //检查更新
        fun update() {}

        //退出
        fun exit() {}
    }
}