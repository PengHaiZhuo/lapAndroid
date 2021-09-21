package com.phz.dev.feature.main.mine

import android.graphics.Color
import android.os.Bundle
import androidx.core.view.size
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.setFitsSystemWindows
import com.phz.common.databinding.MineBindingAdapter.circleImageUrlGifRes
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

    override fun onResume() {
        super.onResume()
        immersionBar {
            statusBarColor(R.color.colorPrimary)
            autoDarkModeEnable(true)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mViewDataBinding.vm = mViewModel
        mViewDataBinding.clickProxy = ProxyClick()
        mViewDataBinding.swipeRl.apply {
//            setSize(SwipeRefreshLayout.LARGE) //设置尺寸
//            setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE);//设置刷新时颜色变换
            setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
            )//设置刷新时颜色变换
            setBackgroundColor(Color.WHITE)//设置背景色
            setDistanceToTriggerSync(96)//设置下拉距离
            setOnRefreshListener {
                //todo 从网络获取积分 返回结果后手动停止动画
                postDelayed({
                    circleImageUrlGifRes(mViewDataBinding.ivAvatar, mViewModel.gifUrl)
                    mViewDataBinding.swipeRl.isRefreshing = false
                },2000)

            }
        }
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